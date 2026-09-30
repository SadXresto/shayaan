package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Sahaaya", appName)
  }

  @Test
  fun `repository initializes without NullPointerException`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = SahaayaRepository(context)
    assertEquals("", repo.loggedInRole)
    assertEquals("Sage", repo.chatMessages.first().senderName)
    val student = repo.loginOrRegisterStudent("Aarav", "9876543210", repo.schoolInfo.name, "Class 10 - Section A")
    assertEquals("student", repo.loggedInRole)
    assertEquals("Aarav", student.name)
    assertEquals(1, repo.students.size)
  }
}
