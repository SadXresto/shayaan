# 🔐 Sahyaan APK — Security Checklist

This checklist verifies that the **Sahyaan** Android APK and Supabase backend (`gemini-chat` Edge Function + Postgres RLS) never leak server-side secrets into the client build or repository.

---

### 1. No secret keys in `.env` or source
```bash
grep -rn "sb_secret_" app/
grep -rn "service_role" app/
```
✅ Both must return **0 matches** inside `app/`.

⚠️ **Do not** run these on `.` (repo root) — the `supabase/functions/` folder may reference server-side secrets via `Deno.env.get(...)`. Scope to `app/` only.

---

### 2. No Gemini key in app
```bash
grep -rn "AIzaSy" app/
grep -rn "GEMINI_API_KEY" app/
```
✅ Must return **0 matches** in `app/`, `strings.xml`, and `BuildConfig.java`.

The Gemini key lives **only** in:
```bash
supabase secrets list
# → shows GEMINI_API_KEY (server-side only)
```

---

### 3. `.env` is git-ignored
Verify `.gitignore` contains:
```gitignore
.env
.env.*
!.env.example
local.properties
```
Then confirm:
```bash
git status --ignored          # .env should appear under "Ignored files"
git ls-files | grep -E "\.env|local\.properties"   # must only show .env.example (or be empty)
```

---

### 4. `BuildConfig.java` contains only safe fields
Open:
```
app/build/generated/source/buildConfig/debug/com/example/BuildConfig.java
```
✅ The **only** Supabase/Gemini-related fields must be:
```java
public static final String SUPABASE_URL = "https://bqllmeadgzjxbwiimfol.supabase.co";
public static final String SUPABASE_PUBLISHABLE_KEY = "sb_publishable_c_1foKmUR7HXoWqeOlldDA_aPgIaONs";
```
❌ No `SUPABASE_SERVICE_ROLE_KEY`, no `GEMINI_API_KEY`, no `sb_secret_...`.

Also verify the **release** variant:
```
app/build/generated/source/buildConfig/release/com/example/BuildConfig.java
```

---

### 5. RLS enabled on every table
Supabase → **Database → Tables** — confirm RLS is **enabled** on:
- `schools`
- `profiles`
- `conversations`
- `messages`

Then run in the SQL editor to confirm policies exist too (RLS on + no policies = silent failures):
```sql
select schemaname, tablename, rowsecurity
from pg_tables
where schemaname = 'public'
  and tablename in ('schools', 'profiles', 'conversations', 'messages');

select tablename, policyname, cmd, roles
from pg_policies
where schemaname = 'public'
order by tablename, policyname;
```

---

### 6. Leaked Secret Key Rotation
If any `sb_secret_...` or `service_role` key was ever pasted publicly:
1. Open **Supabase Dashboard → Project Settings → API Keys**.
2. Revoke / roll the compromised `sb_secret_...` key immediately.
3. Update any server-side CI/CD or Edge Function secrets if applicable (`supabase secrets set ...`).
4. Never place `sb_secret_...` or `GEMINI_API_KEY` inside `.env`, `local.properties`, or Android resources.

---

### 7. Edge Function Auth Verification (`gemini-chat`)
Verify that unauthenticated requests to `/functions/v1/gemini-chat` are rejected with `401 Unauthorized`:
```bash
curl -i -X POST "https://bqllmeadgzjxbwiimfol.supabase.co/functions/v1/gemini-chat" \
  -H "Content-Type: application/json" \
  -d '{"conversation_id":"00000000-0000-0000-0000-000000000000","message":"test"}'
```
✅ Must return `HTTP/2 401` (`{"error":"Missing Authorization header"}` or `{"error":"Unauthorized"}`).
