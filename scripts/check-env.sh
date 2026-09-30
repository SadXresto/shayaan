#!/usr/bin/env bash
set -euo pipefail

TARGET_FILE="${1:-.env.example}"

if [ ! -f "$TARGET_FILE" ]; then
  echo "❌ ERROR: $TARGET_FILE not found."
  exit 1
fi

# 1. Strip full-line comments (optional leading whitespace + '#') and blank lines,
#    while preserving original line numbers via grep -n.
ACTIVE_LINES=$(grep -n -v -E '^[[:space:]]*(#|$)' "$TARGET_FILE" || true)

# 2. Match forbidden variable names before '=' OR forbidden value fragments after '='
#    - Variable names: GEMINI_API_KEY, SUPABASE_SERVICE_ROLE_KEY, SERVICE_ROLE_KEY, SECRET_KEY
#    - Value fragments / patterns: sb_secret_*, AIzaSy*, service_role, or GEMINI_API_KEY
FORBIDDEN_REGEX='^[0-9]+:[[:space:]]*(export[[:space:]]+)?(GEMINI_API_KEY|SUPABASE_SERVICE_ROLE_KEY|SERVICE_ROLE_KEY|SUPABASE_SECRET_KEY)[[:space:]]*=|=[[:space:]"'\'']*(.*(sb_secret_|AIzaSy|service_role|GEMINI_API_KEY))'

FORBIDDEN=$(printf '%s\n' "$ACTIVE_LINES" | grep -E "$FORBIDDEN_REGEX" || true)

if [ -n "$FORBIDDEN" ]; then
  echo "❌ ERROR: Forbidden secret key or variable assignment found in $TARGET_FILE:"
  echo "$FORBIDDEN"
  exit 1
fi

echo "✅ $TARGET_FILE is clean — no forbidden secret assignments or key values found."
exit 0
