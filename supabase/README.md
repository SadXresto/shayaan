# Sahyaan — Supabase Backend Deployment Guide

This guide walks through deploying the complete **Sahyaan** backend on Supabase (Postgres schema, Row Level Security, Auth, Realtime, and the `gemini-chat` Edge Function).

---

## 1. Create a Supabase Project

1. Go to [https://supabase.com/dashboard](https://supabase.com/dashboard) and sign in.
2. Click **New project** and choose your organization.
3. Fill in:
   - **Name**: `Sahyaan`
   - **Database Password**: Generate a strong password and save it securely.
   - **Region**: Select the region closest to your schools/students (e.g., `South Asia (Mumbai)`).
4. Click **Create new project** and wait ~1–2 minutes for the database and API to finish provisioning.

---

## 2. Run the SQL in SQL Editor

1. In your Supabase project dashboard, click **SQL Editor** in the left sidebar.
2. Click **New query**.
3. Paste the entire SQL migration (`supabase/migrations/20260926000000_sahyaan_schema.sql`) into the query editor.
4. Click **Run** (or press `Ctrl+Enter` / `Cmd+Enter`).
5. Confirm `Success. No rows returned` appears. You can verify the 4 tables (`schools`, `profiles`, `conversations`, `messages`) and the seeded `Test School` (`SAHYAAN-TEST`) under **Table Editor**.

---

## 3. Enable Email Provider (Confirmation ON) + Anonymous Sign-Ins

1. Go to **Authentication → Providers → Email**:
   - Ensure **Enable Email provider** is toggled **ON**.
   - Ensure **Confirm email** is toggled **ON** (do not disable it).
   - Click **Save**.
2. Go to **Authentication → Settings** (under *User Signups*):
   - Toggle **Allow anonymous sign-ins** **ON** so students can chat with Sage without signing up first.
   - Click **Save**.
3. Go to **Authentication → Email Templates**:
   - Update the default email template branding to **Sahyaan** (e.g., `Confirm your Sahyaan signup`).

---

## 4. Install Supabase CLI, Login, and Link Project

1. **Install the Supabase CLI**:
   ```bash
   # macOS (Homebrew)
   brew install supabase/tap/supabase

   # Windows (Scoop)
   scoop bucket add supabase https://github.com/supabase/scoop-bucket.git
   scoop install supabase

   # Linux / npm
   npm install -g supabase
   ```

2. **Log in to your Supabase account**:
   ```bash
   supabase login
   ```

3. **Link your project** (find your `<project-ref>` under **Project Settings → General → Reference ID**):
   ```bash
   supabase link --project-ref <ref>
   ```

---

## 5. Set the `GEMINI_API_KEY` Secret

Generate an API key in [Google AI Studio](https://aistudio.google.com/apikey) and store it in Supabase Secrets:

```bash
supabase secrets set GEMINI_API_KEY=your_google_ai_studio_api_key
```

*(Note: `SUPABASE_URL` and `SUPABASE_ANON_KEY` are automatically injected into the Edge Function environment by Supabase.)*

---

## 6. Deploy the `gemini-chat` Edge Function

From the repository root (containing `supabase/functions/gemini-chat/index.ts`), run:

```bash
supabase functions deploy gemini-chat
```

---

## 7. End-to-End `curl` Test

Set your project URL and publishable (`anon`) key:

```bash
export SUPABASE_URL="https://<ref>.supabase.co"
export SUPABASE_ANON_KEY="<your-anon-or-publishable-key>"
```

### Step 7.1 — Resolve the seeded school's `id` (`SAHYAAN-TEST`):
```bash
curl -s -X POST "${SUPABASE_URL}/rest/v1/rpc/resolve_school_code" \
  -H "apikey: ${SUPABASE_ANON_KEY}" \
  -H "Content-Type: application/json" \
  -d '{"code": "SAHYAAN-TEST"}'
```
Copy the returned `id` UUID as `<SCHOOL_ID>`.

### Step 7.2 — Sign up / obtain an access token via Auth (`/auth/v1/signup` or `/auth/v1/token`):
```bash
# Anonymous / instant test signup linked to <SCHOOL_ID>:
curl -s -X POST "${SUPABASE_URL}/auth/v1/signup" \
  -H "apikey: ${SUPABASE_ANON_KEY}" \
  -H "Content-Type: application/json" \
  -d '{
    "data": {
      "full_name": "Test Student",
      "role": "counselee",
      "school_id": "<SCHOOL_ID>"
    }
  }'

# Or sign in a confirmed email user via /auth/v1/token?grant_type=password:
curl -s -X POST "${SUPABASE_URL}/auth/v1/token?grant_type=password" \
  -H "apikey: ${SUPABASE_ANON_KEY}" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@example.com",
    "password": "your-password"
  }'
```
Copy the returned `access_token` as `<USER_JWT>`.

### Step 7.3 — Call `/rest/v1/rpc/request_counsellor` to create a conversation:
```bash
curl -s -X POST "${SUPABASE_URL}/rest/v1/rpc/request_counsellor" \
  -H "apikey: ${SUPABASE_ANON_KEY}" \
  -H "Authorization: Bearer <USER_JWT>" \
  -H "Content-Type: application/json" \
  -d '{}'
```
Copy the returned UUID string as `<CONVERSATION_ID>`.

### Step 7.4 — Call `/functions/v1/gemini-chat`:
```bash
curl -s -X POST "${SUPABASE_URL}/functions/v1/gemini-chat" \
  -H "apikey: ${SUPABASE_ANON_KEY}" \
  -H "Authorization: Bearer <USER_JWT>" \
  -H "Content-Type: application/json" \
  -d '{
    "conversation_id": "<CONVERSATION_ID>",
    "message": "I am feeling overwhelmed about my exams."
  }'
```

Expected JSON output:
```json
{
  "reply": "That sounds really overwhelming, and it makes complete sense that you're feeling the weight of your exams right now. Taking things one small step at a time can help when everything feels piled up. What part of your exams is making you feel the most stressed today?"
}
```

---

## 8. Where to Find Project URL and Publishable Key

1. Open your project in the [Supabase Dashboard](https://supabase.com/dashboard).
2. Go to **Project Settings (⚙️) → API** (or **API Keys**).
3. Copy:
   - **Project URL**: `https://<ref>.supabase.co`
   - **Publishable / `anon` `public` key**: Safe to use in your client app because Row Level Security (RLS) is enabled on all tables.
4. **Never** expose the `service_role` secret key in any client application or Edge Function client where RLS should apply.
