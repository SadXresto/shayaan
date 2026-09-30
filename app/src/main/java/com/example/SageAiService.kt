package com.example

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

// ═══════════════════════════════════════════════════════════════════
// SUPABASE EDGE FUNCTION MODELS (functions/v1/gemini-chat)
// ═══════════════════════════════════════════════════════════════════

@JsonClass(generateAdapter = true)
data class EdgeChatRequest(
    val conversation_id: String,
    val message: String
)

@JsonClass(generateAdapter = true)
data class EdgeChatResponse(
    val reply: String? = null
)

interface SahyaanEdgeFunctionService {
    @POST("functions/v1/gemini-chat")
    suspend fun invokeGeminiChat(
        @Header("apikey") publishableKey: String,
        @Header("Authorization") bearerToken: String,
        @Body request: EdgeChatRequest
    ): EdgeChatResponse
}

object SahyaanEdgeClient {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun createService(baseUrl: String): SahyaanEdgeFunctionService {
        val normalizedUrl = baseUrl
            .removeSuffix("/rest/v1/")
            .removeSuffix("/rest/v1")
            .trimEnd('/') + "/"
        return Retrofit.Builder()
            .baseUrl(normalizedUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SahyaanEdgeFunctionService::class.java)
    }
}

// ═══════════════════════════════════════════════════════════════════
// SAGE AI COMPANION ENGINE
// ═══════════════════════════════════════════════════════════════════

data class SageReplyResult(
    val replyText: String,
    val isCrisisAlert: Boolean = false,
    val suggestCounsellorConnection: Boolean = false
)

object SageAiCompanion {

    private val crisisKeywords = listOf(
        "suicide", "suicidal", "kill myself", "end my life", "want to die",
        "better off dead", "self-harm", "self harm", "hurt myself", "cutting myself",
        "harm myself", "harm others", "hurt someone", "kill someone", "overdose",
        "don't want to live", "dont want to live", "no reason to live", "ending it all"
    )

    private val humanCounsellorKeywords = listOf(
        "human counsellor", "real counsellor", "real person", "talk to a counsellor",
        "speak to a counsellor", "connect with a counsellor", "connect with counsellor",
        "book a session", "appointment", "meet counsellor", "talk to ms", "talk to a human",
        "speak to someone real", "licensed therapist"
    )

    fun isCrisisMessage(message: String): Boolean {
        val lower = message.lowercase()
        return crisisKeywords.any { lower.contains(it) }
    }

    fun isAskingForHumanCounsellor(message: String): Boolean {
        val lower = message.lowercase()
        return humanCounsellorKeywords.any { lower.contains(it) }
    }

    fun buildCrisisResponse(
        campusCounsellingContact: String,
        counsellorName: String,
        nationalCrisisHotline: String = "14416 (Tele-MANAS) / 1098 (Childline)",
        localEmergencyNumber: String = "112"
    ): String {
        return """I'm so sorry you're going through such deep pain right now, and I'm really glad you shared this with me. Your safety matters more than anything else, and you don't have to carry this alone—please reach out to a human counsellor or crisis support right now:

• Campus Counselling ($counsellorName): $campusCounsellingContact
• National Crisis Hotline: $nationalCrisisHotline
• Emergency: $localEmergencyNumber

Please tap "Connect with a Counsellor" below or call one of these numbers immediately so someone who cares can support you safely."""
    }

    suspend fun generateSageReply(
        userMessage: String,
        conversationHistory: List<ChatMessage>,
        schoolName: String,
        schoolPhone: String,
        counsellorEmail: String,
        counsellorName: String,
        studentName: String,
        conversationId: String = "",
        userJwt: String = ""
    ): SageReplyResult = withContext(Dispatchers.IO) {
        val trimmed = userMessage.trim()
        val campusContact = "$schoolPhone / $counsellorEmail"

        // 1. Immediate Crisis Protocol check
        if (isCrisisMessage(trimmed)) {
            return@withContext SageReplyResult(
                replyText = buildCrisisResponse(
                    campusCounsellingContact = campusContact,
                    counsellorName = counsellorName
                ),
                isCrisisAlert = true,
                suggestCounsellorConnection = true
            )
        }

        val wantsCounsellor = isAskingForHumanCounsellor(trimmed)

        // 2. Call Supabase Edge Function (gemini-chat) when an authenticated session & conversation exist
        val supabaseUrl = try {
            BuildConfig.SUPABASE_URL.trim()
        } catch (_: Exception) {
            ""
        }
        val publishableKey = try {
            BuildConfig.SUPABASE_PUBLISHABLE_KEY.trim()
        } catch (_: Exception) {
            ""
        }

        if (supabaseUrl.startsWith("https://") &&
            publishableKey.isNotBlank() &&
            conversationId.isNotBlank() &&
            userJwt.isNotBlank()
        ) {
            try {
                val service = SahyaanEdgeClient.createService(supabaseUrl)
                val response = service.invokeGeminiChat(
                    publishableKey = publishableKey,
                    bearerToken = "Bearer $userJwt",
                    request = EdgeChatRequest(
                        conversation_id = conversationId,
                        message = trimmed
                    )
                )
                val edgeReply = response.reply?.trim()
                if (!edgeReply.isNullOrBlank()) {
                    return@withContext SageReplyResult(
                        replyText = edgeReply,
                        isCrisisAlert = false,
                        suggestCounsellorConnection = wantsCounsellor ||
                                edgeReply.contains("Connect with a Counsellor", ignoreCase = true)
                    )
                }
            } catch (_: Exception) {
                // Fall back gracefully to local Sage companion engine
            }
        }

        // 3. Empathetic, context-aware fallback following every rule of the Sage persona
        val fallbackReply = generateLocalSageResponse(
            userMessage = trimmed,
            history = conversationHistory,
            schoolName = schoolName,
            counsellorName = counsellorName
        )

        SageReplyResult(
            replyText = fallbackReply,
            isCrisisAlert = false,
            suggestCounsellorConnection = wantsCounsellor ||
                    fallbackReply.contains("Connect with a Counsellor", ignoreCase = true)
        )
    }

    private fun generateLocalSageResponse(
        userMessage: String,
        history: List<ChatMessage>,
        schoolName: String,
        counsellorName: String
    ): String {
        val lower = userMessage.lowercase()
        val userTurnsCount = history.count { it.isFromUser }

        if (isAskingForHumanCounsellor(userMessage)) {
            return "Thank you for sharing that with me—reaching out takes real courage. While I'm an AI companion here to listen, your conversations with human counsellors like $counsellorName are completely private and secure. You can use the \"Connect with a Counsellor\" tab right here in the app, or would you like to share what you're hoping to talk about with them first?"
        }

        if (lower.contains("confidential") || lower.contains("private") || lower.contains("secret") ||
            lower.contains("who are you") || lower.contains("are you a therapist") || lower.contains("are you real")
        ) {
            return "I'm Sage, your warm AI companion here at $schoolName, and I'm here to give you a safe, non-judgmental space to express your feelings. While I'm an AI and not a licensed therapist, your conversations with human counsellors through \"Connect with a Counsellor\" are strictly private and secure. How are you feeling in this moment?"
        }

        if (lower.contains("exam") || lower.contains("test") || lower.contains("grade") ||
            lower.contains("study") || lower.contains("studies") || lower.contains("fail") ||
            lower.contains("academic") || lower.contains("homework") || lower.contains("assignment") ||
            lower.contains("syllabus") || lower.contains("marks")
        ) {
            return when (userTurnsCount % 3) {
                1 -> "That sounds incredibly overwhelming. Exam and academic pressure at $schoolName can feel like a huge weight on your shoulders. Would you like to talk about what's making you feel most anxious right now?"
                2 -> "I can understand why you'd feel that way—trying to keep up with expectations is really exhausting. Sometimes taking three slow, deep breaths or jotting down just one small task can gently ease that pressure if you'd like to try. Which subject or topic feels the heaviest on your mind today?"
                else -> "Thank you for sharing that with me; it makes complete sense that you're feeling stretched thin. Remember that a single test or assignment never defines your worth. What is one small way you could be gentle with yourself tonight?"
            }
        }

        if (lower.contains("anxious") || lower.contains("anxiety") || lower.contains("overwhelm") ||
            lower.contains("stressed") || lower.contains("stress") || lower.contains("panic") ||
            lower.contains("nervous") || lower.contains("scared") || lower.contains("worried")
        ) {
            return when (userTurnsCount % 3) {
                1 -> "That sounds really tough, and I'm glad you're sharing it here instead of holding it all inside. When everything feels loud at once, a gentle grounding exercise—like noticing three things you can see around you—might help bring a little calm if you're open to it. What feels like the biggest source of that worry right now?"
                2 -> "I can understand why you'd feel that way; carrying that much tension is exhausting. If you'd like, we can slow things down together with a soft deep breath in for four seconds and out for four seconds. How is your body feeling as you sit with this right now?"
                else -> "Thank you for sharing that with me—feeling anxious can be so draining. You don't have to figure everything out all at once today. What is one thought that keeps circling back the most?"
            }
        }

        if (lower.contains("friend") || lower.contains("lonely") || lower.contains("alone") ||
            lower.contains("left out") || lower.contains("ignored") || lower.contains("bully") ||
            lower.contains("classmate") || lower.contains("parents") || lower.contains("family") ||
            lower.contains("fight") || lower.contains("argued")
        ) {
            return when (userTurnsCount % 3) {
                1 -> "Thank you for sharing that with me—feeling disconnected or hurt by people around you is really painful. Navigating friendships and expectations can feel so heavy sometimes. Would you like to tell me a little more about what happened?"
                2 -> "That sounds really tough, and I can understand why you'd feel hurt by that situation. You deserve to feel heard and respected in your relationships. What part of this has been weighing on your heart the most?"
                else -> "I hear you, and it's completely valid that this is affecting your peace of mind. Sometimes writing your thoughts down in a journal or speaking privately via \"Connect with a Counsellor\" can help untangle heavy relationship feelings. How have you been coping with this over the last few days?"
            }
        }

        if (lower.contains("sad") || lower.contains("cry") || lower.contains("crying") ||
            lower.contains("tired") || lower.contains("exhausted") || lower.contains("sleep") ||
            lower.contains("insomnia") || lower.contains("empty") || lower.contains("down") ||
            lower.contains("unmotivated") || lower.contains("drained")
        ) {
            return when (userTurnsCount % 3) {
                1 -> "That sounds really heavy, and I want you to know it's okay to not feel okay today. When your mind and body are drained, even small things take so much effort. Have you been able to get any rest lately, or has your mind been too busy?"
                2 -> "Thank you for trusting me with how you're feeling. Carrying sadness or exhaustion through the school day takes a quiet kind of strength. What do you think your mind or body needs most right now—a moment to vent, or a gentle way to rest?"
                else -> "I can understand why you'd feel so worn out. If it feels helpful, resting your eyes for a minute and letting your shoulders drop can be a tiny step toward comfort. Would you like to share when you first started feeling this way?"
            }
        }

        if (lower.contains("breath") || lower.contains("grounding") || lower.contains("cope") ||
            lower.contains("coping") || lower.contains("calm") || lower.contains("journal") ||
            lower.contains("exercise") || lower.contains("help me relax")
        ) {
            return "I'm really glad you're taking a moment to care for yourself. One gentle option is the 5-4-3-2-1 grounding idea—noticing 5 things you see, 4 you can touch, 3 you hear, 2 you smell, and 1 slow breath—or simply inhaling softly for 4 counts and exhaling for 6. Would you like to try one of those together right now, or talk through what's on your mind?"
        }

        if (lower in listOf("hi", "hello", "hey", "hi sage", "hello sage", "good morning", "good evening")) {
            return "Hello there! Thank you for stopping by—I'm Sage, your supportive AI companion here at $schoolName. This is a safe, judgment-free space for whatever is on your mind today. How are you truly feeling right now?"
        }

        return when (userTurnsCount % 3) {
            1 -> "Thank you for sharing that with me. It sounds like you've been carrying a lot on your mind lately, and I'm right here listening without any judgment. What part of this feels most important for us to talk through first?"
            2 -> "I can understand why you'd feel that way, and it makes complete sense that this is on your mind. Remember that you can take things one small breath at a time, and if you ever want human support, \"Connect with a Counsellor\" is always private and secure. How has this been affecting your day so far?"
            else -> "That sounds really meaningful to reflect on, and I appreciate your openness with me. Sometimes simply putting our thoughts into words helps make them feel a little lighter. What would feel most supportive for you in this moment?"
        }
    }
}
