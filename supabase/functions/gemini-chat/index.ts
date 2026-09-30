import { createClient } from "https://esm.sh/@supabase/supabase-js@2.45.4";

const corsHeaders = {
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Headers":
    "authorization, x-client-info, apikey, content-type",
  "Access-Control-Allow-Methods": "POST, OPTIONS",
};

const SAGE_SYSTEM_PROMPT = `You are "Sage," a warm, empathetic, non-judgmental AI companion inside Sahyaan, 
a school-based counselling support app. You are the first point of contact for 
students before or alongside a human counsellor. You are NOT a licensed therapist 
and do not replace human counsellors.

Core principles:
- Empathy before anything else. Always validate the emotion first.
- Soft, warm, conversational tone. No jargon. No coldness.
- Keep replies to 2–4 sentences. Ask at most ONE gentle question per reply.
- Never diagnose, never suggest medication, never moralize.
- You may offer simple coping ideas (breathing, grounding, journaling) as 
  optional suggestions, never as instructions.
- Respond in the same language the student writes in (including Hinglish).

Crisis protocol (highest priority):
If the student mentions self-harm, suicide, harm to others, abuse, or 
immediate danger:
1. Respond with warm empathy. Do not panic or lecture.
2. Urge them to reach a human right now — open the "Connect with a Counsellor" 
   feature in the app, or call:
   - School Counselling Team: [ADMIN: FILL IN PHONE/EMAIL]
   - National Crisis Helpline (India): 14416 (Tele-MANAS)
   - Emergency: 112
3. Do not continue normal conversation. Prioritize safety.

Never:
- Claim to be human or a licensed counsellor
- Reveal other students' or schools' information
- Give legal, medical, or financial advice`;

Deno.serve(async (req: Request): Promise<Response> => {
  if (req.method === "OPTIONS") {
    return new Response("ok", { headers: corsHeaders });
  }

  if (req.method !== "POST") {
    return new Response(JSON.stringify({ error: "Method not allowed" }), {
      status: 405,
      headers: { ...corsHeaders, "Content-Type": "application/json" },
    });
  }

  try {
    const geminiApiKey = Deno.env.get("GEMINI_API_KEY");
    const supabaseUrl = Deno.env.get("SUPABASE_URL");
    const supabaseAnonKey = Deno.env.get("SUPABASE_ANON_KEY");

    if (!geminiApiKey || !supabaseUrl || !supabaseAnonKey) {
      return new Response(
        JSON.stringify({
          error: "Missing required environment variables (GEMINI_API_KEY, SUPABASE_URL, SUPABASE_ANON_KEY)",
        }),
        {
          status: 500,
          headers: { ...corsHeaders, "Content-Type": "application/json" },
        },
      );
    }

    const authHeader = req.headers.get("Authorization");
    if (!authHeader) {
      return new Response(
        JSON.stringify({ error: "Missing Authorization header" }),
        {
          status: 401,
          headers: { ...corsHeaders, "Content-Type": "application/json" },
        },
      );
    }

    // Use ANON key + caller's Authorization header so RLS applies (never service_role)
    const supabase = createClient(supabaseUrl, supabaseAnonKey, {
      global: {
        headers: { Authorization: authHeader },
      },
      auth: {
        persistSession: false,
      },
    });

    const {
      data: { user },
      error: userError,
    } = await supabase.auth.getUser();

    if (userError || !user) {
      return new Response(JSON.stringify({ error: "Unauthorized" }), {
        status: 401,
        headers: { ...corsHeaders, "Content-Type": "application/json" },
      });
    }

    const { conversation_id, message } = await req.json();
    if (!conversation_id || !message || typeof message !== "string") {
      return new Response(
        JSON.stringify({ error: "conversation_id and message are required" }),
        {
          status: 400,
          headers: { ...corsHeaders, "Content-Type": "application/json" },
        },
      );
    }

    // Verify caller has access to conversation_id
    const { data: canAccess, error: accessError } = await supabase.rpc(
      "can_access_conversation",
      { conv_id: conversation_id },
    );

    if (accessError || !canAccess) {
      return new Response(
        JSON.stringify({ error: "Forbidden: cannot access conversation" }),
        {
          status: 403,
          headers: { ...corsHeaders, "Content-Type": "application/json" },
        },
      );
    }

    // Load the last 20 messages of the conversation ordered by created_at asc
    const { data: recentRows, error: historyError } = await supabase
      .from("messages")
      .select("content, is_ai, created_at")
      .eq("conversation_id", conversation_id)
      .order("created_at", { ascending: false })
      .limit(20);

    if (historyError) {
      return new Response(
        JSON.stringify({ error: historyError.message }),
        {
          status: 500,
          headers: { ...corsHeaders, "Content-Type": "application/json" },
        },
      );
    }

    const historyAsc = (recentRows ?? []).reverse();

    // Insert the user's new message into public.messages (is_ai = false, sender_id = user.id)
    const { error: insertUserError } = await supabase.from("messages").insert({
      conversation_id,
      sender_id: user.id,
      content: message.trim(),
      is_ai: false,
    });

    if (insertUserError) {
      return new Response(
        JSON.stringify({ error: insertUserError.message }),
        {
          status: 500,
          headers: { ...corsHeaders, "Content-Type": "application/json" },
        },
      );
    }

    // Build Gemini contents with the Sage system prompt as the first user turn,
    // followed by the conversation history, followed by the new message
    const contents = [
      {
        role: "user",
        parts: [{ text: SAGE_SYSTEM_PROMPT }],
      },
      {
        role: "model",
        parts: [
          {
            text: "Understood. I am Sage, a warm, empathetic, non-judgmental AI companion inside Sahyaan. I will follow all core principles and the crisis protocol strictly.",
          },
        ],
      },
      ...historyAsc.map((msg) => ({
        role: msg.is_ai ? "model" : "user",
        parts: [{ text: msg.content }],
      })),
      {
        role: "user",
        parts: [{ text: message.trim() }],
      },
    ];

    // Call Gemini 2.5 Flash
    const geminiRes = await fetch(
      `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=${encodeURIComponent(geminiApiKey)}`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ contents }),
      },
    );

    if (!geminiRes.ok) {
      const errorBody = await geminiRes.text();
      return new Response(
        JSON.stringify({
          error: "Gemini API request failed",
          details: errorBody,
        }),
        {
          status: 502,
          headers: { ...corsHeaders, "Content-Type": "application/json" },
        },
      );
    }

    const geminiJson = await geminiRes.json();
    const reply: string =
      geminiJson?.candidates?.[0]?.content?.parts?.[0]?.text?.trim() ??
      "I hear you, and thank you for sharing that with me. Would you like to tell me a little more about what's on your mind right now?";

    // Insert the reply into public.messages (is_ai = true, sender_id = null)
    const { error: insertReplyError } = await supabase.from("messages").insert({
      conversation_id,
      sender_id: null,
      content: reply,
      is_ai: true,
    });

    if (insertReplyError) {
      return new Response(
        JSON.stringify({ error: insertReplyError.message }),
        {
          status: 500,
          headers: { ...corsHeaders, "Content-Type": "application/json" },
        },
      );
    }

    return new Response(JSON.stringify({ reply }), {
      status: 200,
      headers: { ...corsHeaders, "Content-Type": "application/json" },
    });
  } catch (err) {
    return new Response(
      JSON.stringify({
        error: err instanceof Error ? err.message : "Internal server error",
      }),
      {
        status: 500,
        headers: { ...corsHeaders, "Content-Type": "application/json" },
      },
    );
  }
});
