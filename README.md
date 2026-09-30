# Sahyaan — School-Based Counselling Platform

**Sahyaan** is a school-based counselling platform powered by **Supabase** (Postgres + Row Level Security + Auth + Realtime + Edge Functions) and **Sage** (Gemini-powered AI companion via the `gemini-chat` Edge Function).

---

## 📂 Repository Structure

- **`app/`** — Android client application (Kotlin + Jetpack Compose). Communicates with Supabase using only the client-safe `SUPABASE_URL` and `SUPABASE_PUBLISHABLE_KEY`.
- **`apk/sahaaya-latest.apk`** — Latest compiled debug APK (automatically synced on every `assembleDebug` build).
- **`supabase/migrations/20260926000000_sahyaan_schema.sql`** — Complete Postgres schema, RLS policies, helper functions, Realtime publication, and seed school (`SAHYAAN-TEST`).
- **`supabase/functions/gemini-chat/index.ts`** — Supabase Edge Function that authenticates the caller via JWT, verifies conversation access under RLS, calls `gemini-2.5-flash` using the server-side `GEMINI_API_KEY` secret, and stores both messages in `public.messages`.
- **`supabase/README.md`** — Step-by-step Supabase deployment & `curl` verification guide.
- **`SECURITY.md`** — Pre-release APK & Supabase security verification checklist.

---

## 🔐 Android `.env` Configuration (Client-Safe Only)

Your root `.env` (git-ignored) and `.env.example` contain **only** the two public client values injected into `BuildConfig`:

```ini
SUPABASE_URL=https://bqllmeadgzjxbwiimfol.supabase.co
SUPABASE_PUBLISHABLE_KEY=sb_publishable_c_1foKmUR7HXoWqeOlldDA_aPgIaONs
```

- **Never** add `GEMINI_API_KEY`, `SUPABASE_SERVICE_ROLE_KEY`, or `sb_secret_...` to `.env` or any file inside `app/`.
- `GEMINI_API_KEY` is stored exclusively in **Supabase Edge Function Secrets**:
  ```bash
  supabase secrets set GEMINI_API_KEY=your_google_ai_studio_api_key
  ```

---

## 🛡️ Security Checklist

See [`SECURITY.md`](./SECURITY.md) for the complete 7-point verification checklist before distributing any APK build.
