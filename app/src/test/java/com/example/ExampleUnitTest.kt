package com.example

import java.io.File
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun sqlMigration_containsCompleteSchemaRlsAndRpcs() {
    val sqlFile = sequenceOf(
      File("../supabase/migrations/20260926000000_sahyaan_schema.sql"),
      File("supabase/migrations/20260926000000_sahyaan_schema.sql")
    ).firstOrNull { it.exists() }

    assertNotNull("SQL migration file must exist", sqlFile)
    val sql = sqlFile!!.readText()

    // Verify all 4 tables (idempotent create table if not exists) and RLS enabled
    assertTrue(sql.contains("create table if not exists public.schools"))
    assertTrue(sql.contains("alter table public.schools enable row level security;"))
    assertTrue(sql.contains("create table if not exists public.profiles"))
    assertTrue(sql.contains("alter table public.profiles enable row level security;"))
    assertTrue(sql.contains("create table if not exists public.conversations"))
    assertTrue(sql.contains("alter table public.conversations enable row level security;"))
    assertTrue(sql.contains("create table if not exists public.messages"))
    assertTrue(sql.contains("alter table public.messages enable row level security;"))

    // Verify helper functions, trigger, and RPCs
    assertTrue(sql.contains("create or replace function public.current_school_id()"))
    assertTrue(sql.contains("create or replace function public.can_access_conversation(conv_id uuid)"))
    assertTrue(sql.contains("create or replace function public.handle_new_user()"))
    assertTrue(sql.contains("create trigger on_auth_user_created"))
    assertTrue(sql.contains("create or replace function public.resolve_school_code(code text)"))
    assertTrue(sql.contains("create or replace function public.request_counsellor()"))

    // Verify service_role AI message policy, grants, realtime, and seed school
    assertTrue(sql.contains("to service_role"))
    assertTrue(sql.contains("grant execute on function public.resolve_school_code(text)"))
    assertTrue(sql.contains("alter publication supabase_realtime add table public.messages;"))
    assertTrue(sql.contains("SAHYAAN-TEST"))
  }

  @Test
  fun envExample_hasNoForbiddenSecretAssignments() {
    val envExample = sequenceOf(
      File("../.env.example"),
      File(".env.example")
    ).firstOrNull { it.exists() }

    assertNotNull(".env.example must exist", envExample)
    val activeLines = envExample!!.readLines()
      .map { it.trim() }
      .filter { it.isNotEmpty() && !it.startsWith("#") }

    assertTrue("Active lines must not be empty", activeLines.isNotEmpty())
    val forbiddenRegex = Regex("""^(export\s+)?(GEMINI_API_KEY|SUPABASE_SERVICE_ROLE_KEY|SERVICE_ROLE_KEY|SUPABASE_SECRET_KEY)\s*=|(sb_secret_|AIzaSy|service_role)""")
    for (line in activeLines) {
      assertFalse("Forbidden secret found in active .env.example line: $line", forbiddenRegex.containsMatchIn(line))
    }
  }

  @Test
  fun sageAiCompanion_crisisAndCounsellorDetectionWorks() {
    assertTrue(SageAiCompanion.isCrisisMessage("I want to end my life"))
    assertFalse(SageAiCompanion.isCrisisMessage("I am stressed about math exams"))
    assertTrue(SageAiCompanion.isAskingForHumanCounsellor("Can I talk to a counsellor please?"))
  }
}
