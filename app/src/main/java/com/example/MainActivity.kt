package com.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ui.theme.SahaayaTheme
import com.example.ui.theme.ThemeMode

// ═══════════════════════════════════════
// DATA MODELS
// ═══════════════════════════════════════

data class SchoolInfo(
    val name: String = "PM Shri Kendriya Vidyalaya No.1, Vasco Da Gama, Goa",
    val phone: String = "+91 832 251 2345",
    val email: String = "kv1vasco@gmail.com",
    val address: String = "Vasco Da Gama, Goa - 403802"
)

data class ChatMessage(
    val id: String,
    val senderName: String,
    val message: String,
    val isFromUser: Boolean,
    val timestamp: String,
    val isCrisisAlert: Boolean = false,
    val suggestCounsellorConnection: Boolean = false
)

data class DirectChatMessage(
    val id: String,
    val studentId: String,
    val studentName: String,
    val studentClass: String,
    val senderRole: String, // "STUDENT" or "COUNSELLOR"
    val senderName: String,
    val message: String,
    val isFromStudent: Boolean,
    val timestamp: String
)

data class Student(
    val id: String,
    val name: String,
    val phone: String,
    val schoolName: String,
    val classSection: String,
    var streak: Int = 0,
    var seeds: Int = 0,
    var lastCheckIn: String = "Never",
    var checkedInToday: Boolean = false,
    var mood: String = ""
)

data class CheckIn(
    val id: String,
    val studentId: String,
    val studentName: String,
    val studentClass: String,
    val mood: String,
    val factors: List<String>,
    val wantsToTalk: String,
    val customNotes: String = "",
    val energyLevel: String = "",
    val sleepQuality: String = "",
    val academicStress: String = "Moderate / Manageable",
    val peerRelations: String = "Normal / Connected",
    val physicalSymptoms: String = "None",
    val supportAction: String = "",
    val situationReport: String = "",
    val timestamp: String
)

data class FriendReport(
    val id: String,
    val reporterId: String,
    val reporterName: String,
    val concernedName: String,
    val concernedPhone: String,
    val classSection: String,
    val concern: String,
    val notes: String = "",
    val urgency: String = "Medium",
    var status: String = "New",
    val timestamp: String
)

data class SupportRequest(
    val id: String,
    val studentId: String,
    val studentName: String,
    val studentClass: String,
    val type: String,
    val notes: String = "",
    var status: String = "New",
    val timestamp: String
)

data class CounsellorCase(
    val id: String,
    val studentId: String,
    val studentName: String,
    val studentClass: String,
    val category: String, // "Follow-up requested", "Conversation requested", "Concern reported", "Needs review"
    var status: String,   // "New", "Under Review", "Scheduled", "Resolved"
    var notes: String,
    val urgency: String = "Medium",
    val situationReport: String = "",
    val timestamp: String
)

data class SchoolAccount(
    var schoolName: String,
    var schoolPassword: String,
    var counsellorName: String,
    var counsellorEmail: String,
    var counsellorPhone: String,
    var isVerified: Boolean = false
)

// ═══════════════════════════════════════
// REPOSITORY / STATE HOLDER
// ═══════════════════════════════════════

class SahaayaRepository(private val context: Context? = null) {
    private val prefs = context?.getSharedPreferences("sahaaya_app_prefs", Context.MODE_PRIVATE)

    var loggedInRole by mutableStateOf("")

    // Registered School Accounts with School Passwords
    val schools = mutableStateListOf<SchoolAccount>(
        SchoolAccount(
            schoolName = "PM Shri Kendriya Vidyalaya No.1, Vasco Da Gama, Goa",
            schoolPassword = "kv1vasco123",
            counsellorName = "Ms. Sharma",
            counsellorEmail = "counsellor@kv1vasco.edu.in",
            counsellorPhone = "+91 832 251 2345",
            isVerified = true
        )
    )

    fun getSchoolAccount(name: String): SchoolAccount? {
        return schools.firstOrNull { it.schoolName.equals(name.trim(), ignoreCase = true) }
    }

    fun addOrUpdateSchoolAccount(account: SchoolAccount) {
        val idx = schools.indexOfFirst { it.schoolName.equals(account.schoolName.trim(), ignoreCase = true) }
        if (idx != -1) {
            schools[idx] = account
        } else {
            schools.add(account)
        }
        persistSchools()
    }

    private fun persistSchools() {
        val raw = schools.joinToString(";;;") { "${it.schoolName}|||${it.schoolPassword}|||${it.counsellorName}|||${it.counsellorEmail}" }
        prefs?.edit()?.putString("custom_schools", raw)?.apply()
    }

    fun updateSchoolPassword(name: String, newPassword: String): Boolean {
        val idx = schools.indexOfFirst { it.schoolName.equals(name.trim(), ignoreCase = true) }
        if (idx != -1) {
            val curr = schools[idx]
            schools[idx] = curr.copy(schoolPassword = newPassword.trim())
            persistSchools()
            return true
        }
        return false
    }

    val currentSchoolPassword: String
        get() = getSchoolAccount(schoolInfo.name)?.schoolPassword ?: "kv1vasco123"

    // School Global Information
    var schoolInfo by mutableStateOf(
        SchoolInfo(
            name = "PM Shri Kendriya Vidyalaya No.1, Vasco Da Gama, Goa",
            phone = "+91 832 251 2345",
            email = "kv1vasco@gmail.com",
            address = "Vasco Da Gama, Goa - 403802"
        )
    )

    // Current Active Student (Initialized with ready-to-use profile; no login barrier required)
    var currentStudent by mutableStateOf(
        Student(
            id = "std_default",
            name = "Student",
            phone = "",
            schoolName = "PM Shri Kendriya Vidyalaya No.1, Vasco Da Gama, Goa",
            classSection = "Class 10 - Section A",
            streak = 0,
            seeds = 0,
            lastCheckIn = "Never",
            checkedInToday = false,
            mood = ""
        )
    )

    var studentSeeds by mutableIntStateOf(0)
    var counsellorName by mutableStateOf("Ms. Sharma")
    val schoolName: String
        get() = schoolInfo.name
    val schoolPhone: String
        get() = schoolInfo.phone
    var counsellorEmail by mutableStateOf("kv1vasco@gmail.com")
    var checkInFrequency by mutableStateOf("Daily")
    var themeMode by mutableStateOf(ThemeMode.LIGHT)

    init {
        loadSavedSession()
    }

    private fun loadSavedSession() {
        val savedTheme = prefs?.getString("theme_mode", "") ?: ""
        if (savedTheme == "DARK") {
            themeMode = ThemeMode.DARK
        }

        // Restore custom registered schools if any
        val customSchoolsRaw = prefs?.getString("custom_schools", "") ?: ""
        if (customSchoolsRaw.isNotBlank()) {
            customSchoolsRaw.split(";;;").forEach { entry ->
                val parts = entry.split("|||")
                if (parts.size >= 2) {
                    val sName = parts[0]
                    val sPass = parts[1]
                    val cName = parts.getOrNull(2) ?: "School Counsellor"
                    val cEmail = parts.getOrNull(3) ?: "counsellor@school.edu.in"
                    if (schools.none { it.schoolName.equals(sName, ignoreCase = true) }) {
                        schools.add(
                            SchoolAccount(
                                schoolName = sName,
                                schoolPassword = sPass,
                                counsellorName = cName,
                                counsellorEmail = cEmail,
                                counsellorPhone = "+91 98765 43210",
                                isVerified = true
                            )
                        )
                    }
                }
            }
        }

        val role = prefs?.getString("logged_in_role", "student") ?: "student"
        val sName = (prefs?.getString("student_name", "") ?: "").ifBlank { "Student" }
        val sPhone = prefs?.getString("student_phone", "") ?: ""
        val sSchool = (prefs?.getString("student_school", "") ?: "").ifBlank { schoolInfo.name }
        val sClass = (prefs?.getString("student_class", "") ?: "").ifBlank { "Class 10 - Section A" }
        val sStreak = prefs?.getInt("student_streak", 0) ?: 0
        val sSeeds = prefs?.getInt("student_seeds", 0) ?: 0
        val sMood = prefs?.getString("student_mood", "") ?: ""
        val sCheckedToday = prefs?.getBoolean("student_checked_today", false) ?: false
        val sLastCheckIn = prefs?.getString("student_last_checkin", "Never") ?: "Never"

        val loadedStudent = Student(
            id = "std_${sPhone.ifBlank { "default" }}",
            name = sName,
            phone = sPhone,
            schoolName = sSchool,
            classSection = sClass,
            streak = sStreak,
            seeds = sSeeds,
            lastCheckIn = sLastCheckIn,
            checkedInToday = sCheckedToday,
            mood = sMood
        )
        currentStudent = loadedStudent
        studentSeeds = sSeeds
        if (students.none { it.id == loadedStudent.id }) {
            students.add(loadedStudent)
        }

        // Restore latest check-in reflection card if present
        val cMood = prefs?.getString("last_checkin_mood", "") ?: ""
        if (cMood.isNotBlank()) {
            val cEnergy = prefs?.getString("last_checkin_energy", "") ?: ""
            val cSleep = prefs?.getString("last_checkin_sleep", "") ?: ""
            val cAction = prefs?.getString("last_checkin_action", "") ?: ""
            val cNotes = prefs?.getString("last_checkin_notes", "") ?: ""
            val cTalk = prefs?.getString("last_checkin_talk", "") ?: ""
            val cTimestamp = prefs?.getString("last_checkin_time", "Today, Just now") ?: "Today, Just now"
            val factorsStr = prefs?.getString("last_checkin_factors", "") ?: ""
            val factorsList = if (factorsStr.isNotBlank()) factorsStr.split(", ") else emptyList()

            checkIns.add(
                0,
                CheckIn(
                    id = "c_saved",
                    studentId = loadedStudent.id,
                    studentName = loadedStudent.name,
                    studentClass = loadedStudent.classSection,
                    mood = cMood,
                    factors = factorsList,
                    wantsToTalk = cTalk,
                    customNotes = cNotes,
                    energyLevel = cEnergy,
                    sleepQuality = cSleep,
                    supportAction = cAction,
                    timestamp = cTimestamp
                )
            )
        }

        if (role == "counsellor") {
            val cEmail = prefs?.getString("counsellor_email", "") ?: ""
            val cSchool = prefs?.getString("counsellor_school", "") ?: ""
            if (cEmail.isNotBlank()) {
                counsellorEmail = cEmail
            }
            if (cSchool.isNotBlank()) {
                schoolInfo = schoolInfo.copy(name = cSchool)
            }
            counsellorName = prefs?.getString("counsellor_name", "Ms. Sharma") ?: "Ms. Sharma"
            loggedInRole = "counsellor"
        } else {
            loggedInRole = "student"
        }
        loadSavedDirectMessages()
        loadSavedSageChatHistory()
    }

    fun toggleTheme() {
        themeMode = if (themeMode == ThemeMode.DARK) ThemeMode.LIGHT else ThemeMode.DARK
        prefs?.edit()?.putString("theme_mode", themeMode.name)?.apply()
    }

    val isDarkTheme: Boolean
        get() = themeMode == ThemeMode.DARK

    // Persistent Direct Messages between Students and School Counsellor
    val directMessages = mutableStateListOf<DirectChatMessage>()

    fun loadSavedDirectMessages() {
        try {
            val raw = prefs?.getString("direct_messages_data", "") ?: ""
            if (raw.isNotBlank()) {
                val blocks = raw.split("===S_MSG===")
                directMessages.clear()
                for (b in blocks) {
                    val parts = b.split("|||")
                    if (parts.size >= 8) {
                        directMessages.add(
                            DirectChatMessage(
                                id = parts[0],
                                studentId = parts[1],
                                studentName = parts[2],
                                studentClass = parts[3],
                                senderRole = parts[4],
                                senderName = parts[5],
                                message = parts[6],
                                isFromStudent = parts[7].toBooleanStrictOrNull() ?: (parts[4] == "STUDENT"),
                                timestamp = if (parts.size > 8) parts[8] else "Today"
                            )
                        )
                    }
                }
            }
        } catch (_: Exception) {}
    }

    fun saveDirectMessages() {
        try {
            val encoded = directMessages.joinToString("===S_MSG===") {
                "${it.id}|||${it.studentId}|||${it.studentName}|||${it.studentClass}|||${it.senderRole}|||${it.senderName}|||${it.message.replace("|||", " ").replace("===S_MSG===", " ")}|||${it.isFromStudent}|||${it.timestamp}"
            }
            prefs?.edit()?.putString("direct_messages_data", encoded)?.apply()
        } catch (_: Exception) {}
    }

    fun sendStudentDirectMessage(messageText: String, student: Student = currentStudent) {
        val clean = messageText.trim()
        if (clean.isBlank()) return
        val msg = DirectChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            studentId = student.id.ifBlank { currentStudent.id.ifBlank { "std_current" } },
            studentName = student.name.ifBlank { currentStudent.name.ifBlank { "Student" } },
            studentClass = student.classSection.ifBlank { currentStudent.classSection.ifBlank { "General" } },
            senderRole = "STUDENT",
            senderName = student.name.ifBlank { currentStudent.name.ifBlank { "Student" } },
            message = clean,
            isFromStudent = true,
            timestamp = "Today, Just now"
        )
        directMessages.add(msg)
        saveDirectMessages()

        // If message indicates crisis or high distress, alert counsellor safeguarding queue
        val isDistress = clean.contains("emergency", ignoreCase = true) ||
                clean.contains("help", ignoreCase = true) ||
                clean.contains("danger", ignoreCase = true) ||
                clean.contains("harm", ignoreCase = true) ||
                clean.contains("kill", ignoreCase = true)
        if (isDistress) {
            counsellorCases.add(
                0,
                CounsellorCase(
                    id = "case_${System.currentTimeMillis() % 10000}",
                    studentId = msg.studentId,
                    studentName = msg.studentName,
                    studentClass = msg.studentClass,
                    category = "Direct message alert",
                    status = "New",
                    notes = "Student sent urgent direct message: \"$clean\"",
                    urgency = "High",
                    situationReport = "Automated safeguarding priority flag generated by direct student message.",
                    timestamp = "Today, Just now"
                )
            )
        }
    }

    fun sendStudentDirectMessage(student: Student, messageText: String) = sendStudentDirectMessage(messageText, student)

    fun sendCounsellorDirectMessage(studentId: String, studentName: String, studentClass: String, messageText: String) {
        val clean = messageText.trim()
        if (clean.isBlank()) return
        val msg = DirectChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            studentId = studentId,
            studentName = studentName,
            studentClass = studentClass,
            senderRole = "COUNSELLOR",
            senderName = counsellorName.ifBlank { "School Counsellor" },
            message = clean,
            isFromStudent = false,
            timestamp = "Today, Just now"
        )
        directMessages.add(msg)
        saveDirectMessages()
    }

    fun getDirectMessagesForStudent(studentId: String): List<DirectChatMessage> {
        val targetId = studentId.ifBlank { currentStudent.id }
        return directMessages.filter { it.studentId == targetId || (targetId.isNotBlank() && it.studentId == targetId) }
    }

    fun resetStudentPassword(schoolName: String, phone: String, newPass: String): Boolean {
        val cleanPhone = phone.trim()
        val cleanPass = newPass.trim()
        if (cleanPhone.isBlank() || cleanPass.length < 4) return false

        val sAccount = getSchoolAccount(schoolName)
        if (sAccount != null) {
            sAccount.schoolPassword = cleanPass
            addOrUpdateSchoolAccount(sAccount)
        }
        prefs?.edit()?.putString("student_pwd_${cleanPhone}", cleanPass)?.apply()
        return true
    }

    fun resetCounsellorPassword(schoolName: String, email: String, newPass: String): Boolean {
        val cleanEmail = email.trim()
        val cleanPass = newPass.trim()
        if (cleanEmail.isBlank() || cleanPass.length < 4) return false

        val sAccount = getSchoolAccount(schoolName)
        if (sAccount != null) {
            sAccount.schoolPassword = cleanPass
            addOrUpdateSchoolAccount(sAccount)
        } else {
            addOrUpdateSchoolAccount(
                SchoolAccount(
                    schoolName = schoolName,
                    schoolPassword = cleanPass,
                    counsellorEmail = cleanEmail,
                    counsellorName = counsellorName.ifBlank { "School Counsellor" },
                    counsellorPhone = schoolInfo.phone,
                    isVerified = true
                )
            )
        }
        prefs?.edit()?.putString("counsellor_pwd_${cleanEmail}", cleanPass)?.apply()
        return true
    }

    // Persistent Chat Messages with Sage AI Companion
    var isSageTyping by mutableStateOf(false)

    val chatMessages = mutableStateListOf(
        ChatMessage(
            id = "msg_1",
            senderName = "Sage",
            message = "Hi there, I'm Sage—your warm, non-judgmental AI companion here at Sahaaya. This is a safe space to express your feelings, explore your thoughts, and receive gentle guidance. While I'm an AI and not a licensed therapist, your conversations with human counsellors via \"Connect with a Counsellor\" are always private and secure. How are you feeling today?",
            isFromUser = false,
            timestamp = "Today, Just now"
        )
    )

    fun loadSavedSageChatHistory() {
        try {
            val raw = prefs?.getString("sage_chat_history", "") ?: ""
            if (raw.isNotBlank()) {
                val blocks = raw.split("===SAGE_MSG===")
                val loaded = mutableListOf<ChatMessage>()
                for (b in blocks) {
                    val parts = b.split("|||")
                    if (parts.size >= 5) {
                        loaded.add(
                            ChatMessage(
                                id = parts[0],
                                senderName = parts[1],
                                message = parts[2],
                                isFromUser = parts[3].toBooleanStrictOrNull() ?: false,
                                timestamp = parts[4],
                                isCrisisAlert = parts.getOrNull(5)?.toBooleanStrictOrNull() ?: false,
                                suggestCounsellorConnection = parts.getOrNull(6)?.toBooleanStrictOrNull() ?: false
                            )
                        )
                    }
                }
                if (loaded.isNotEmpty()) {
                    chatMessages.clear()
                    chatMessages.addAll(loaded)
                }
            }
        } catch (_: Exception) {}
    }

    fun saveSageChatHistory() {
        try {
            val encoded = chatMessages.joinToString("===SAGE_MSG===") {
                val safeMsg = it.message.replace("|||", " ").replace("===SAGE_MSG===", " ")
                "${it.id}|||${it.senderName}|||${safeMsg}|||${it.isFromUser}|||${it.timestamp}|||${it.isCrisisAlert}|||${it.suggestCounsellorConnection}"
            }
            prefs?.edit()?.putString("sage_chat_history", encoded)?.apply()
        } catch (_: Exception) {}
    }

    fun clearSageChatHistory() {
        chatMessages.clear()
        chatMessages.add(
            ChatMessage(
                id = "msg_welcome_${System.currentTimeMillis()}",
                senderName = "Sage",
                message = "Hi there, I'm Sage—your warm, non-judgmental AI companion at ${schoolInfo.name}. Whenever you're ready, feel free to share whatever is on your mind. What's been weighing on your thoughts today?",
                isFromUser = false,
                timestamp = "Today, Just now"
            )
        )
        saveSageChatHistory()
    }

    // Managed Students (Starts clean; dynamic accounts created/uploaded on student login)
    val students = mutableStateListOf<Student>()

    // Real Check-in history submitted by students
    val checkIns = mutableStateListOf<CheckIn>()

    // Real Friend Reports submitted by students
    val friendReports = mutableStateListOf<FriendReport>()

    // Real Support Requests submitted by students
    val supportRequests = mutableStateListOf<SupportRequest>()

    // Real Counsellor Safeguarding Cases
    val counsellorCases = mutableStateListOf<CounsellorCase>()

    // Student Authentication & Profile Management
    fun loginOrRegisterStudent(
        name: String,
        phone: String,
        schoolName: String,
        classSection: String
    ): Student {
        val cleanPhone = phone.trim()
        val cleanName = name.trim().ifBlank { "Student" }
        val cleanSchool = schoolName.trim().ifBlank { schoolInfo.name }
        val cleanClass = classSection.trim().ifBlank { "Class 10 - Section A" }

        // Match existing student by phone or full name in the same school
        val existingIndex = students.indexOfFirst {
            (cleanPhone.isNotBlank() && it.phone == cleanPhone) ||
            (it.name.equals(cleanName, ignoreCase = true) && it.schoolName.equals(cleanSchool, ignoreCase = true))
        }

        val student = if (existingIndex >= 0) {
            val existing = students[existingIndex]
            val updated = existing.copy(
                name = cleanName,
                phone = cleanPhone.ifBlank { existing.phone },
                schoolName = cleanSchool,
                classSection = cleanClass
            )
            students[existingIndex] = updated
            updated
        } else {
            val newStudent = Student(
                id = "std_${System.currentTimeMillis() % 100000}",
                name = cleanName,
                phone = cleanPhone,
                schoolName = cleanSchool,
                classSection = cleanClass,
                streak = 0,
                seeds = 0,
                lastCheckIn = "Never",
                checkedInToday = false,
                mood = ""
            )
            students.add(newStudent)
            newStudent
        }

        currentStudent = student
        studentSeeds = student.seeds
        loggedInRole = "student"

        // Persist student session to SharedPreferences
        prefs?.edit()
            ?.putString("logged_in_role", "student")
            ?.putString("student_name", cleanName)
            ?.putString("student_phone", cleanPhone)
            ?.putString("student_school", cleanSchool)
            ?.putString("student_class", cleanClass)
            ?.putInt("student_seeds", student.seeds)
            ?.putInt("student_streak", student.streak)
            ?.putString("student_mood", student.mood)
            ?.putBoolean("student_checked_today", student.checkedInToday)
            ?.putString("student_last_checkin", student.lastCheckIn)
            ?.apply()

        return student
    }

    fun updateStudentProfile(name: String, classSection: String, schoolName: String) {
        val cleanName = name.trim().ifBlank { "Student" }
        val cleanClass = classSection.trim().ifBlank { "Class 10 - Section A" }
        val cleanSchool = schoolName.trim().ifBlank { schoolInfo.name }
        val updated = currentStudent.copy(
            id = currentStudent.id.ifBlank { "std_default" },
            name = cleanName,
            classSection = cleanClass,
            schoolName = cleanSchool
        )
        currentStudent = updated
        val idx = students.indexOfFirst { it.id == updated.id }
        if (idx >= 0) {
            students[idx] = updated
        } else {
            students.add(updated)
        }
        prefs?.edit()
            ?.putString("student_name", cleanName)
            ?.putString("student_class", cleanClass)
            ?.putString("student_school", cleanSchool)
            ?.apply()
    }

    fun logoutStudent() {
        loggedInRole = ""
        prefs?.edit()
            ?.putString("logged_in_role", "")
            ?.apply()
    }

    fun loginCounsellor(email: String, school: String, name: String = "School Counsellor", phone: String = "") {
        counsellorEmail = email
        if (school.isNotBlank()) {
            schoolInfo = schoolInfo.copy(name = school)
        }
        counsellorName = name
        loggedInRole = "counsellor"
        prefs?.edit()
            ?.putString("logged_in_role", "counsellor")
            ?.putString("counsellor_email", email)
            ?.putString("counsellor_school", school)
            ?.putString("counsellor_name", name)
            ?.putString("counsellor_phone", phone)
            ?.apply()
    }

    fun logoutCounsellor() {
        loggedInRole = ""
        prefs?.edit()
            ?.remove("logged_in_role")
            ?.remove("counsellor_email")
            ?.remove("counsellor_school")
            ?.remove("counsellor_name")
            ?.remove("counsellor_phone")
            ?.apply()
    }

    fun addSeeds(amount: Int) {
        studentSeeds += amount
        val updated = currentStudent.copy(seeds = studentSeeds)
        currentStudent = updated
        val idx = students.indexOfFirst { it.id == currentStudent.id }
        if (idx >= 0) {
            students[idx] = updated
        }
        prefs?.edit()?.putInt("student_seeds", studentSeeds)?.apply()
    }

    // Helper functions mutating state
    fun submitStudentCheckIn(
        mood: String,
        factors: List<String>,
        wantsToTalk: String,
        customNotes: String = "",
        energyLevel: String = "",
        sleepQuality: String = "",
        supportAction: String = "",
        academicStress: String = "Moderate / Manageable",
        peerRelations: String = "Normal / Connected",
        physicalSymptoms: String = "None"
    ) {
        val newSeeds = studentSeeds + 20
        studentSeeds = newSeeds
        val updated = currentStudent.copy(
            checkedInToday = true,
            streak = currentStudent.streak + 1,
            seeds = newSeeds,
            lastCheckIn = "Today",
            mood = mood
        )
        currentStudent = updated

        // Generate DeepSeek / Clinical pedagogical situation report
        val clinicalReport = SituationAnalysisEngine.generateDetailedSituationReport(
            student = currentStudent,
            mood = mood,
            academicStress = academicStress,
            peerRelations = peerRelations,
            sleepQuality = sleepQuality,
            energyLevel = energyLevel,
            physicalSymptoms = physicalSymptoms,
            factors = factors,
            wantsToTalk = wantsToTalk,
            customNotes = customNotes
        )

        // Update in student list as well
        val idx = students.indexOfFirst { it.id == currentStudent.id }
        if (idx >= 0) {
            students[idx] = updated
        } else if (currentStudent.id.isNotBlank()) {
            students.add(updated)
        }

        // Record check-in
        val checkInRecord = CheckIn(
            id = "c_${System.currentTimeMillis()}",
            studentId = currentStudent.id.ifBlank { "std_${System.currentTimeMillis() % 10000}" },
            studentName = currentStudent.name.ifBlank { "Student" },
            studentClass = currentStudent.classSection.ifBlank { "General" },
            mood = mood,
            factors = factors,
            wantsToTalk = wantsToTalk,
            customNotes = customNotes,
            energyLevel = energyLevel,
            sleepQuality = sleepQuality,
            academicStress = academicStress,
            peerRelations = peerRelations,
            physicalSymptoms = physicalSymptoms,
            supportAction = supportAction,
            situationReport = clinicalReport,
            timestamp = "Today, Just now"
        )
        checkIns.add(0, checkInRecord)

        // Persist check-in details and stats
        prefs?.edit()
            ?.putInt("student_seeds", newSeeds)
            ?.putInt("student_streak", updated.streak)
            ?.putString("student_mood", mood)
            ?.putBoolean("student_checked_today", true)
            ?.putString("student_last_checkin", "Today")
            ?.putString("last_checkin_mood", mood)
            ?.putString("last_checkin_energy", energyLevel)
            ?.putString("last_checkin_sleep", sleepQuality)
            ?.putString("last_checkin_action", supportAction)
            ?.putString("last_checkin_notes", customNotes)
            ?.putString("last_checkin_talk", wantsToTalk)
            ?.putString("last_checkin_factors", factors.joinToString(", "))
            ?.putString("last_checkin_time", "Today, Just now")
            ?.apply()

        // If mood is distressed, wants to talk, severe stress or bullying, alert counsellor
        val needsAttention = mood.contains("Not doing well") || mood.contains("need support") ||
                wantsToTalk.contains("Yes") || academicStress.contains("Severe") ||
                peerRelations.contains("Bullying")
        if (needsAttention) {
            val alertCategory = when {
                wantsToTalk.contains("Yes") -> "Conversation requested"
                peerRelations.contains("Bullying") -> "Safeguarding concern"
                academicStress.contains("Severe") -> "Academic distress"
                else -> "Needs review"
            }
            val urgency = if (mood.contains("need support") || peerRelations.contains("Bullying") || academicStress.contains("Severe")) "High" else "Medium"
            val caseNotes = buildString {
                append("Checked in with mood: $mood. ")
                if (academicStress.isNotBlank()) append("Academics: $academicStress. ")
                if (peerRelations.isNotBlank()) append("Social: $peerRelations. ")
                if (energyLevel.isNotBlank()) append("Energy: $energyLevel. ")
                if (sleepQuality.isNotBlank()) append("Sleep: $sleepQuality. ")
                if (factors.isNotEmpty()) append("Factors: ${factors.joinToString(", ")}. ")
                if (supportAction.isNotBlank()) append("Preferred support: $supportAction. ")
                if (customNotes.isNotBlank()) append("Custom Note: \"$customNotes\"")
            }
            val newCase = CounsellorCase(
                id = "case_${System.currentTimeMillis() % 10000}",
                studentId = currentStudent.id.ifBlank { checkInRecord.studentId },
                studentName = currentStudent.name.ifBlank { "Student" },
                studentClass = currentStudent.classSection.ifBlank { "General" },
                category = alertCategory,
                status = "New",
                notes = caseNotes,
                urgency = urgency,
                situationReport = clinicalReport,
                timestamp = "Today, Just now"
            )
            counsellorCases.add(0, newCase)
        }
    }

    fun submitFriendReport(
        concernedName: String,
        concernedPhone: String,
        classSection: String,
        concern: String,
        notes: String,
        urgency: String
    ) {
        val report = FriendReport(
            id = "fr_${System.currentTimeMillis() % 10000}",
            reporterId = currentStudent.id.ifBlank { "std_self" },
            reporterName = currentStudent.name.ifBlank { "Anonymous Student" },
            concernedName = concernedName,
            concernedPhone = concernedPhone,
            classSection = classSection,
            concern = concern,
            notes = notes,
            urgency = urgency,
            status = "New",
            timestamp = "Today, Just now"
        )
        friendReports.add(0, report)

        val newCase = CounsellorCase(
            id = "case_${System.currentTimeMillis() % 10000}",
            studentId = "peer_${System.currentTimeMillis() % 1000}",
            studentName = concernedName,
            studentClass = classSection,
            category = "Concern reported",
            status = "New",
            notes = "Reported by peer: $concern. Notes: $notes",
            urgency = urgency,
            timestamp = "Today, Just now"
        )
        counsellorCases.add(0, newCase)
    }

    fun submitSupportRequest(type: String, notes: String) {
        val request = SupportRequest(
            id = "sr_${System.currentTimeMillis() % 10000}",
            studentId = currentStudent.id.ifBlank { "std_self" },
            studentName = currentStudent.name.ifBlank { "Student" },
            studentClass = currentStudent.classSection.ifBlank { "General" },
            type = type,
            notes = notes,
            status = "New",
            timestamp = "Today, Just now"
        )
        supportRequests.add(0, request)

        val newCase = CounsellorCase(
            id = "case_${System.currentTimeMillis() % 10000}",
            studentId = currentStudent.id.ifBlank { request.studentId },
            studentName = currentStudent.name.ifBlank { "Student" },
            studentClass = currentStudent.classSection.ifBlank { "General" },
            category = "Follow-up requested",
            status = "New",
            notes = "Requested: $type. Note: $notes",
            urgency = "Medium",
            timestamp = "Today, Just now"
        )
        counsellorCases.add(0, newCase)
    }

    fun updateCaseStatus(caseId: String, newStatus: String, addedNotes: String? = null) {
        val idx = counsellorCases.indexOfFirst { it.id == caseId }
        if (idx >= 0) {
            val old = counsellorCases[idx]
            val updatedNotes = if (!addedNotes.isNullOrBlank()) "${old.notes} | $addedNotes" else old.notes
            counsellorCases[idx] = old.copy(status = newStatus, notes = updatedNotes)
        }
    }

    fun updateFriendReportStatus(reportId: String, newStatus: String) {
        val idx = friendReports.indexOfFirst { it.id == reportId }
        if (idx >= 0) {
            val old = friendReports[idx]
            friendReports[idx] = old.copy(status = newStatus)
        }
    }

    fun updateSupportRequestStatus(requestId: String, newStatus: String) {
        val idx = supportRequests.indexOfFirst { it.id == requestId }
        if (idx >= 0) {
            val old = supportRequests[idx]
            supportRequests[idx] = old.copy(status = newStatus)
        }
    }

    suspend fun sendSageMessageAsync(userText: String) {
        val trimmed = userText.trim()
        if (trimmed.isEmpty()) return

        val userMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            senderName = currentStudent.name.ifBlank { "You" },
            message = trimmed,
            isFromUser = true,
            timestamp = "Today, Just now"
        )
        chatMessages.add(userMsg)
        saveSageChatHistory()

        isSageTyping = true
        val sageResult = try {
            SageAiCompanion.generateSageReply(
                userMessage = trimmed,
                conversationHistory = chatMessages.toList(),
                schoolName = currentStudent.schoolName.ifBlank { schoolInfo.name },
                schoolPhone = schoolInfo.phone,
                counsellorEmail = counsellorEmail,
                counsellorName = counsellorName,
                studentName = currentStudent.name
            )
        } finally {
            isSageTyping = false
        }

        val botReply = ChatMessage(
            id = "msg_reply_${System.currentTimeMillis()}",
            senderName = "Sage",
            message = sageResult.replyText,
            isFromUser = false,
            timestamp = "Today, Just now",
            isCrisisAlert = sageResult.isCrisisAlert,
            suggestCounsellorConnection = sageResult.suggestCounsellorConnection
        )
        chatMessages.add(botReply)
        saveSageChatHistory()

        if (sageResult.isCrisisAlert) {
            counsellorCases.add(
                0,
                CounsellorCase(
                    id = "case_${System.currentTimeMillis() % 10000}",
                    studentId = currentStudent.id.ifBlank { "std_default" },
                    studentName = currentStudent.name.ifBlank { "Student" },
                    studentClass = currentStudent.classSection.ifBlank { "General" },
                    category = "Crisis Protocol Triggered",
                    status = "New",
                    notes = "Sage Crisis Protocol activated for student message: \"$trimmed\"",
                    urgency = "High",
                    situationReport = "Immediate safety protocol triggered in Sage AI Companion.",
                    timestamp = "Today, Just now"
                )
            )
        }
    }

    fun sendChatMessage(userText: String) {
        val trimmed = userText.trim()
        if (trimmed.isEmpty()) return

        val userMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            senderName = currentStudent.name.ifBlank { "You" },
            message = trimmed,
            isFromUser = true,
            timestamp = "Today, Just now"
        )
        chatMessages.add(userMsg)

        val isCrisis = SageAiCompanion.isCrisisMessage(trimmed)
        val wantsCounsellor = SageAiCompanion.isAskingForHumanCounsellor(trimmed)
        val campusContact = "${schoolInfo.phone} / $counsellorEmail"

        val replyText = if (isCrisis) {
            SageAiCompanion.buildCrisisResponse(
                campusCounsellingContact = campusContact,
                counsellorName = counsellorName
            )
        } else if (wantsCounsellor) {
            "Thank you for sharing that with me—reaching out takes real courage. While I'm an AI companion here to listen, your conversations with human counsellors like $counsellorName are completely private and secure. You can use the \"Connect with a Counsellor\" feature right here in the app, or would you like to share what you're hoping to talk about with them first?"
        } else if (trimmed.contains("exam", ignoreCase = true) || trimmed.contains("stress", ignoreCase = true) || trimmed.contains("anxious", ignoreCase = true)) {
            "That sounds incredibly overwhelming. Exam pressure can feel like a huge weight. Would you like to talk about what's making you most anxious right now?"
        } else {
            "Thank you for sharing that with me. I can understand why that would feel heavy on your mind, and I'm right here to listen without any judgment. Would you like to tell me a little more about how this is affecting your day?"
        }

        val botReply = ChatMessage(
            id = "msg_reply_${System.currentTimeMillis()}",
            senderName = "Sage",
            message = replyText,
            isFromUser = false,
            timestamp = "Today, Just now",
            isCrisisAlert = isCrisis,
            suggestCounsellorConnection = isCrisis || wantsCounsellor
        )
        chatMessages.add(botReply)
        saveSageChatHistory()
    }
}

// ═══════════════════════════════════════
// ROUTING CONSTANTS
// ═══════════════════════════════════════

object Screens {
    const val WELCOME = "welcome"
    const val ROLE_SELECT = "role_select"
    const val STUDENT_LOGIN = "student_login"
    const val COUNSELLOR_LOGIN = "counsellor_login"
    const val COUNSELLOR_SIGNUP = "counsellor_signup"

    // Main App Containers
    const val STUDENT_CONTAINER = "student_container"
    const val COUNSELLOR_CONTAINER = "counsellor_container"

    // Global Subscreens
    const val EMERGENCY = "emergency"
    const val FRIEND_CONCERN_STANDALONE = "friend_concern_standalone"
    const val I_NEED_TO_TALK_STANDALONE = "i_need_to_talk_standalone"
    const val LEARNING_HUB_STANDALONE = "learning_hub_standalone"
    const val CHAT_STANDALONE = "chat_standalone"
    const val SCHOOL_SETUP_STANDALONE = "school_setup_standalone"
    const val STRESS_MANAGEMENT = "stress_management"
    const val SUPPORTING_FRIENDS_GUIDE = "supporting_friends_guide"
    const val COUNSELLOR_CHAT_HUB = "counsellor_chat_hub"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SahaayaApp()
        }
    }
}

fun launchEmergencyCall(context: Context, number: String = "112") {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        context.startActivity(intent)
    } catch (_: Exception) {
        // Fallback gracefully in test/emulator environment
    }
}

@Composable
fun SahaayaApp() {
    val context = LocalContext.current
    val repository = remember { SahaayaRepository(context) }
    val initialScreen = when (repository.loggedInRole) {
        "counsellor" -> Screens.COUNSELLOR_CONTAINER
        else -> Screens.STUDENT_CONTAINER
    }
    val backStack = remember { mutableStateListOf(initialScreen) }

    SahaayaTheme(themeMode = repository.themeMode) {
        Surface(modifier = Modifier.fillMaxSize()) {
            fun navigate(to: String) {
                backStack.add(to)
            }

            fun back() {
                if (backStack.size > 1) {
                    backStack.removeAt(backStack.lastIndex)
                }
            }

            fun enterStudentDirectly() {
                if (repository.currentStudent.name.isBlank()) {
                    repository.loginOrRegisterStudent(
                        name = "Student",
                        phone = "",
                        schoolName = repository.schoolInfo.name,
                        classSection = "Class 10 - Section A"
                    )
                } else {
                    repository.loggedInRole = "student"
                }
                backStack.clear()
                backStack.add(Screens.STUDENT_CONTAINER)
            }

            fun enterCounsellorDirectly() {
                repository.loginCounsellor(
                    email = repository.counsellorEmail.ifBlank { "counsellor@kv1vasco.edu.in" },
                    school = repository.schoolInfo.name,
                    name = repository.counsellorName.ifBlank { "Ms. Sharma" }
                )
                backStack.clear()
                backStack.add(Screens.COUNSELLOR_CONTAINER)
            }

            BackHandler(enabled = backStack.size > 1) {
                back()
            }

            when (backStack.lastOrNull() ?: Screens.STUDENT_CONTAINER) {
                Screens.WELCOME -> {
                    WelcomeScreen(
                        repository = repository,
                        onGetStarted = { navigate(Screens.ROLE_SELECT) },
                        onDirectStudentLogin = { enterStudentDirectly() },
                        onDirectCounsellorLogin = { enterCounsellorDirectly() }
                    )
                }

                Screens.ROLE_SELECT -> {
                    ChooseRoleScreen(
                        repository = repository,
                        onSelectStudent = { enterStudentDirectly() },
                        onSelectCounsellor = { enterCounsellorDirectly() },
                        onBack = {
                            if (backStack.size > 1) back() else enterStudentDirectly()
                        }
                    )
                }

                Screens.STUDENT_LOGIN -> {
                    StudentLoginScreen(
                        repository = repository,
                        onBack = { back() },
                        onLoginSuccess = { studentName, phone, school, classSec ->
                            repository.loginOrRegisterStudent(
                                name = studentName,
                                phone = phone,
                                schoolName = school,
                                classSection = classSec
                            )
                            backStack.clear()
                            backStack.add(Screens.STUDENT_CONTAINER)
                        }
                    )
                }

                Screens.COUNSELLOR_LOGIN -> {
                    CounsellorLoginScreen(
                        repository = repository,
                        onBack = { back() },
                        onNavigateSignUp = { navigate(Screens.COUNSELLOR_SIGNUP) },
                        onLoginSuccess = { email, school ->
                            repository.loginCounsellor(email, school)
                            backStack.clear()
                            backStack.add(Screens.COUNSELLOR_CONTAINER)
                        }
                    )
                }

                Screens.COUNSELLOR_SIGNUP -> {
                    CounsellorSignupScreen(
                        repository = repository,
                        onBack = { back() },
                        onSignUpComplete = {
                            repository.loginCounsellor(
                                repository.counsellorEmail,
                                repository.schoolInfo.name,
                                repository.counsellorName
                            )
                            backStack.clear()
                            backStack.add(Screens.COUNSELLOR_CONTAINER)
                        }
                    )
                }

                Screens.STUDENT_CONTAINER -> {
                    StudentMainContainer(
                        repository = repository,
                        onLogout = {
                            navigate(Screens.ROLE_SELECT)
                        },
                        onNavigateEmergency = {},
                        onNavigateFriendConcern = { navigate(Screens.FRIEND_CONCERN_STANDALONE) },
                        onNavigateNeedToTalk = { navigate(Screens.I_NEED_TO_TALK_STANDALONE) },
                        onNavigateLearningHub = { navigate(Screens.LEARNING_HUB_STANDALONE) },
                        onNavigateChat = { navigate(Screens.CHAT_STANDALONE) },
                        onNavigateStressManagement = { navigate(Screens.STRESS_MANAGEMENT) },
                        onNavigateSupportingFriends = { navigate(Screens.SUPPORTING_FRIENDS_GUIDE) }
                    )
                }

                Screens.COUNSELLOR_CONTAINER -> {
                    CounsellorMainContainer(
                        repository = repository,
                        onLogout = {
                            navigate(Screens.ROLE_SELECT)
                        },
                        onNavigateEmergency = {},
                        onNavigateChatHub = { navigate(Screens.COUNSELLOR_CHAT_HUB) }
                    )
                }

                Screens.EMERGENCY -> {
                    EmergencyScreen(
                        repository = repository,
                        onBack = { back() }
                    )
                }

                Screens.FRIEND_CONCERN_STANDALONE -> {
                    FriendConcernScreen(
                        repository = repository,
                        onBack = { back() }
                    )
                }

                Screens.I_NEED_TO_TALK_STANDALONE -> {
                    INeedToTalkScreen(
                        repository = repository,
                        onBack = { back() },
                        onOpenChat = { navigate(Screens.CHAT_STANDALONE) }
                    )
                }

                Screens.LEARNING_HUB_STANDALONE -> {
                    LearningHubScreen(
                        onBack = { back() }
                    )
                }

                Screens.CHAT_STANDALONE -> {
                    ChatScreen(
                        repository = repository,
                        onBack = { back() },
                        onConnectWithCounsellor = { navigate(Screens.I_NEED_TO_TALK_STANDALONE) }
                    )
                }

                Screens.STRESS_MANAGEMENT -> {
                    StressManagementScreen(
                        onBack = { back() },
                        onOpenChat = { navigate(Screens.CHAT_STANDALONE) }
                    )
                }

                Screens.SUPPORTING_FRIENDS_GUIDE -> {
                    SupportingFriendsScreen(
                        onBack = { back() },
                        onOpenFriendReport = { navigate(Screens.FRIEND_CONCERN_STANDALONE) },
                        onOpenDirectChat = { navigate(Screens.CHAT_STANDALONE) }
                    )
                }

                Screens.COUNSELLOR_CHAT_HUB -> {
                    CounsellorChatHubScreen(
                        repository = repository,
                        onBack = { back() }
                    )
                }

                Screens.SCHOOL_SETUP_STANDALONE -> {
                    SchoolSetupScreen(
                        repository = repository,
                        onLogout = {
                            repository.logoutCounsellor()
                            backStack.clear()
                            backStack.add(Screens.WELCOME)
                        }
                    )
                }
            }
        }
    }
}
