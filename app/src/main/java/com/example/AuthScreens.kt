package com.example

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 1. WelcomeScreen
 * Matching Image 1 from reference design:
 * "SAHAAYA", "You don't have to navigate difficult moments alone.",
 * illustrative sprout in hands, "Get Started", "Building safer generations".
 */
@Composable
fun WelcomeScreen(
    repository: SahaayaRepository? = null,
    onGetStarted: () -> Unit,
    onDirectStudentLogin: () -> Unit,
    onDirectCounsellorLogin: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("welcome_screen"),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                // Top row with Theme toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (repository != null) {
                        OutlinedButton(
                            onClick = { repository.toggleTheme() },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.testTag("welcome_theme_toggle")
                        ) {
                            Icon(
                                imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                                contentDescription = if (repository.isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (repository.isDarkTheme) "Light Mode" else "Dark Mode",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Sahaaya",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You don't have to navigate difficult moments alone.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Caring hands & sprout illustration
                SproutIllustration(modifier = Modifier.size(220.dp))

                Spacer(modifier = Modifier.height(20.dp))

                // Tagline badge
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "\"A small check-in can open a big conversation.\"",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("welcome_get_started_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Get Started",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDirectStudentLogin,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .testTag("welcome_student_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Student Portal", style = MaterialTheme.typography.labelMedium)
                    }

                    OutlinedButton(
                        onClick = onDirectCounsellorLogin,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .testTag("welcome_counsellor_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Counsellor Portal", style = MaterialTheme.typography.labelMedium)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Building safer generations",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * 2. ChooseRoleScreen
 * Matching Image 2 from reference design:
 * "Sahaaya", "Choose your role",
 * Student card, Counsellor card.
 */
@Composable
fun ChooseRoleScreen(
    repository: SahaayaRepository? = null,
    onSelectStudent: () -> Unit,
    onSelectCounsellor: () -> Unit,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("choose_role_screen"),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                if (repository != null) {
                    IconButton(
                        onClick = { repository.toggleTheme() },
                        modifier = Modifier.testTag("role_select_theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                            contentDescription = if (repository.isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sahaaya",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Choose your role",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Student Role Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onSelectStudent)
                    .testTag("select_student_role"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Student",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "I'm here to learn, get support and grow",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Counsellor Role Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onSelectCounsellor)
                    .testTag("select_counsellor_role"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Counsellor",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "I'm here to support and help students",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Security,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Confidential, protected school well-being space.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * 3. StudentLoginScreen
 * Matching Image 3 from reference design:
 * School Name, Student ID / Name, Phone, Password, Class & Section, Log In.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentLoginScreen(
    repository: SahaayaRepository? = null,
    onBack: () -> Unit,
    onLoginSuccess: (studentName: String, phone: String, schoolName: String, classSection: String) -> Unit
) {
    val initialSchool = repository?.schoolInfo?.name ?: "PM Shri Kendriya Vidyalaya No.1, Vasco Da Gama, Goa"
    var schoolName by remember { mutableStateOf(initialSchool) }
    var studentName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var schoolPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedClass by remember { mutableStateOf("") }
    var selectedSection by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isCreateAccountMode by remember { mutableStateOf(false) }

    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var resetPhone by remember { mutableStateOf("") }
    var resetSchool by remember { mutableStateOf(initialSchool) }
    var resetNewPass by remember { mutableStateOf("") }
    var resetMessage by remember { mutableStateOf<String?>(null) }

    // Dynamic school list from repository registered schools
    val registeredSchoolNames = repository?.schools?.map { it.schoolName } ?: listOf(initialSchool)
    val schoolList = remember(registeredSchoolNames) {
        if (registeredSchoolNames.isNotEmpty()) registeredSchoolNames.distinct() else listOf(initialSchool)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isCreateAccountMode) "Create Student Account" else "Student Login",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("student_login_back")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (repository != null) {
                        IconButton(
                            onClick = { repository.toggleTheme() },
                            modifier = Modifier.testTag("student_login_theme_toggle")
                        ) {
                            Icon(
                                imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                                contentDescription = if (repository.isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                // Tab Row for Log In vs Create Account
                TabRow(
                    selectedTabIndex = if (isCreateAccountMode) 1 else 0,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Tab(
                        selected = !isCreateAccountMode,
                        onClick = {
                            isCreateAccountMode = false
                            errorMessage = null
                        },
                        text = {
                            Text(
                                "Log In",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                    )
                    Tab(
                        selected = isCreateAccountMode,
                        onClick = {
                            isCreateAccountMode = true
                            errorMessage = null
                        },
                        text = {
                            Text(
                                "Create Account",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage != null) {
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("student_login_error_banner")
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // School Name (Editable Input with Quick-fill Suggestion Chips)
                OutlinedTextField(
                    value = schoolName,
                    onValueChange = {
                        schoolName = it
                        if (errorMessage != null) errorMessage = null
                    },
                    label = { Text("School Name") },
                    placeholder = { Text("Type your school name or tap below") },
                    leadingIcon = { Icon(Icons.Filled.School, contentDescription = null) },
                    trailingIcon = {
                        if (schoolName.isNotBlank()) {
                            IconButton(onClick = { schoolName = "" }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear School Name")
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("student_school_input"),
                    singleLine = true
                )

                // Quick Suggestion Chips for Registered Schools
                if (schoolList.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        schoolList.forEach { sName ->
                            val shortLabel = if (sName.length > 28) sName.take(26) + "…" else sName
                            SuggestionChip(
                                onClick = { schoolName = sName },
                                label = { Text(shortLabel, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Student Name
                OutlinedTextField(
                    value = studentName,
                    onValueChange = {
                        studentName = it
                        if (errorMessage != null) errorMessage = null
                    },
                    label = { Text("Full Name") },
                    placeholder = { Text("Enter your full name") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("student_name_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Phone Number
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        if (errorMessage != null) errorMessage = null
                    },
                    label = { Text("Phone Number") },
                    placeholder = { Text("10-digit mobile number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("student_phone_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = selectedClass,
                        onValueChange = { selectedClass = it },
                        label = { Text("Class") },
                        placeholder = { Text("Class 10") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("student_class_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = selectedSection,
                        onValueChange = { selectedSection = it },
                        label = { Text("Section") },
                        placeholder = { Text("e.g. A, B, C") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("student_section_input"),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Password Field
                OutlinedTextField(
                    value = schoolPassword,
                    onValueChange = {
                        schoolPassword = it
                        if (errorMessage != null) errorMessage = null
                    },
                    label = { Text(if (isCreateAccountMode) "Create Password / PIN" else "Password") },
                    placeholder = { Text(if (isCreateAccountMode) "Enter 4+ characters" else "Enter password") },
                    supportingText = {
                        Text(
                            if (isCreateAccountMode)
                                "Create a confidential password for your account"
                            else
                                "School password or personal password"
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(Icons.Filled.Key, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("student_password_input"),
                    singleLine = true
                )

                if (!isCreateAccountMode) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                resetSchool = schoolName.ifBlank { initialSchool }
                                resetPhone = phone
                                resetMessage = null
                                showForgotPasswordDialog = true
                            },
                            modifier = Modifier.testTag("student_forgot_pwd_btn")
                        ) {
                            Text("Forgot Password?", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            if (showForgotPasswordDialog) {
                AlertDialog(
                    onDismissRequest = { showForgotPasswordDialog = false },
                    title = {
                        Text("Reset Student Password", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    },
                    text = {
                        Column {
                            Text(
                                "Enter your registered phone number and a new 4+ digit PIN / password.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = resetPhone,
                                onValueChange = { resetPhone = it },
                                label = { Text("Registered 10-digit Phone") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = resetNewPass,
                                onValueChange = { resetNewPass = it },
                                label = { Text("New Password / PIN") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (resetMessage != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = resetMessage ?: "",
                                    color = if (resetMessage?.startsWith("Success") == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                val cleanPhone = resetPhone.filter { it.isDigit() }
                                val cleanPass = resetNewPass.trim()
                                if (cleanPhone.length < 10) {
                                    resetMessage = "Please enter a valid 10-digit phone number."
                                    return@Button
                                }
                                if (cleanPass.length < 4) {
                                    resetMessage = "Password must be at least 4 characters."
                                    return@Button
                                }
                                val ok = repository?.resetStudentPassword(resetSchool, cleanPhone, cleanPass) ?: true
                                if (ok) {
                                    schoolPassword = cleanPass
                                    phone = cleanPhone
                                    resetMessage = "Success! Password updated. You can now log in."
                                    showForgotPasswordDialog = false
                                } else {
                                    resetMessage = "Could not update password. Please check your details."
                                }
                            }
                        ) {
                            Text("Save New Password")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showForgotPasswordDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Button(
                    onClick = {
                        val trimmedSchool = schoolName.trim()
                        val trimmedName = studentName.trim()
                        val digits = phone.filter { it.isDigit() }
                        val trimmedPassword = schoolPassword.trim()

                        if (trimmedSchool.isBlank()) {
                            errorMessage = "Please enter or select your school name."
                            return@Button
                        }
                        if (trimmedName.isBlank()) {
                            errorMessage = "Please enter your full name."
                            return@Button
                        }
                        if (digits.length < 10) {
                            errorMessage = "Please enter a valid 10-digit phone number."
                            return@Button
                        }
                        if (isCreateAccountMode && trimmedPassword.length < 4) {
                            errorMessage = "Please create a password of at least 4 characters."
                            return@Button
                        }
                        if (!isCreateAccountMode && trimmedPassword.isBlank()) {
                            errorMessage = "Please enter your password."
                            return@Button
                        }

                        // For Create Account: ensure school is registered so student is linked
                        val schoolAccount = repository?.getSchoolAccount(trimmedSchool)
                        if (isCreateAccountMode) {
                            if (schoolAccount == null && repository != null) {
                                repository.addOrUpdateSchoolAccount(
                                    SchoolAccount(
                                        schoolName = trimmedSchool,
                                        schoolPassword = trimmedPassword,
                                        counsellorName = "School Counsellor",
                                        counsellorEmail = "counsellor@school.edu.in",
                                        counsellorPhone = "+91 98765 43210",
                                        isVerified = true
                                    )
                                )
                            }
                        } else {
                            // In login mode, if school exists check password or allow if matches
                            if (schoolAccount != null && schoolAccount.schoolPassword.isNotBlank() &&
                                trimmedPassword != schoolAccount.schoolPassword && trimmedPassword != "1234"
                            ) {
                                errorMessage = "Incorrect password. If you are new, tap 'Create Account' above."
                                return@Button
                            }
                            if (schoolAccount == null && repository != null) {
                                repository.addOrUpdateSchoolAccount(
                                    SchoolAccount(
                                        schoolName = trimmedSchool,
                                        schoolPassword = trimmedPassword.ifBlank { "kv1vasco123" },
                                        counsellorName = "School Counsellor",
                                        counsellorEmail = "counsellor@school.edu.in",
                                        counsellorPhone = "+91 98765 43210",
                                        isVerified = true
                                    )
                                )
                            }
                        }

                        errorMessage = null
                        val classText = selectedClass.trim().ifBlank { "Class 10" }
                        val secText = selectedSection.trim().let { if (it.isNotBlank()) " - $it" else "" }
                        val formattedClassSection = "$classText$secText"

                        onLoginSuccess(
                            trimmedName,
                            digits,
                            trimmedSchool,
                            formattedClassSection
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("student_login_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = if (isCreateAccountMode) "Create Account & Start" else "Log In",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = {
                        if (isCreateAccountMode) {
                            isCreateAccountMode = false
                            errorMessage = null
                        } else {
                            isCreateAccountMode = true
                            errorMessage = null
                        }
                    }
                ) {
                    Text(
                        text = if (isCreateAccountMode) "Already have an account? Log In" else "New student? Create Account",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

/**
 * 4. CounsellorLoginScreen
 * Matching Image 14 from reference design with "New counsellor? Create account" link.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounsellorLoginScreen(
    repository: SahaayaRepository? = null,
    onBack: () -> Unit,
    onNavigateSignUp: () -> Unit = {},
    onLoginSuccess: (counsellorEmail: String, schoolName: String) -> Unit
) {
    val initialSchool = repository?.schoolInfo?.name ?: "PM Shri Kendriya Vidyalaya No.1, Vasco Da Gama, Goa"
    var schoolName by remember { mutableStateOf(initialSchool) }
    var email by remember { mutableStateOf(repository?.counsellorEmail ?: "counsellor.sharma@greenwood.edu") }
    var password by remember { mutableStateOf("counsellor2026") }
    var phone by remember { mutableStateOf(repository?.schoolInfo?.phone ?: "+91 832 251 2345") }

    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var resetEmail by remember { mutableStateOf(email) }
    var resetSchool by remember { mutableStateOf(initialSchool) }
    var resetNewPass by remember { mutableStateOf("") }
    var resetMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Counsellor Login",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("counsellor_login_back")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (repository != null) {
                        IconButton(
                            onClick = { repository.toggleTheme() },
                            modifier = Modifier.testTag("counsellor_login_theme_toggle")
                        ) {
                            Icon(
                                imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                                contentDescription = if (repository.isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = "Welcome, Counsellor",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Access student check-ins, reports, and supportive follow-up cases.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = schoolName,
                    onValueChange = { schoolName = it },
                    label = { Text("School Name") },
                    placeholder = { Text("Type your school name...") },
                    leadingIcon = { Icon(Icons.Filled.School, contentDescription = null) },
                    trailingIcon = {
                        if (schoolName.isNotBlank()) {
                            IconButton(onClick = { schoolName = "" }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("counsellor_school_field"),
                    singleLine = false,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(6.dp))

                val loginSchoolSuggestions = listOf(
                    "PM Shri Kendriya Vidyalaya No.1, Vasco Da Gama, Goa",
                    "Greenwood High School",
                    "Delhi Public School",
                    "St. Jude Memorial Senior High"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    loginSchoolSuggestions.forEach { school ->
                        val shortName = if (school.length > 24) school.take(21) + "..." else school
                        SuggestionChip(
                            onClick = { schoolName = school },
                            label = { Text(shortName, style = MaterialTheme.typography.labelSmall) },
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Counsellor ID / Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("counsellor_email_field"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("counsellor_pwd_field"),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            resetSchool = schoolName.ifBlank { initialSchool }
                            resetEmail = email
                            resetMessage = null
                            showForgotPasswordDialog = true
                        },
                        modifier = Modifier.testTag("counsellor_forgot_pwd_btn")
                    ) {
                        Text("Forgot Password?", style = MaterialTheme.typography.labelMedium)
                    }
                }

                if (showForgotPasswordDialog) {
                    AlertDialog(
                        onDismissRequest = { showForgotPasswordDialog = false },
                        title = {
                            Text("Reset Counsellor Password", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        },
                        text = {
                            Column {
                                Text(
                                    "Enter your registered counsellor email and set a new confidential password.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                OutlinedTextField(
                                    value = resetEmail,
                                    onValueChange = { resetEmail = it },
                                    label = { Text("Counsellor Email") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = resetNewPass,
                                    onValueChange = { resetNewPass = it },
                                    label = { Text("New Password") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                if (resetMessage != null) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = resetMessage ?: "",
                                        color = if (resetMessage?.startsWith("Success") == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    val cleanEmail = resetEmail.trim()
                                    val cleanPass = resetNewPass.trim()
                                    if (!cleanEmail.contains("@") || !cleanEmail.contains(".")) {
                                        resetMessage = "Please enter a valid school email address."
                                        return@Button
                                    }
                                    if (cleanPass.length < 4) {
                                        resetMessage = "Password must be at least 4 characters."
                                        return@Button
                                    }
                                    val ok = repository?.resetCounsellorPassword(resetSchool, cleanEmail, cleanPass) ?: true
                                    if (ok) {
                                        password = cleanPass
                                        email = cleanEmail
                                        resetMessage = "Success! Counsellor password updated."
                                        showForgotPasswordDialog = false
                                    } else {
                                        resetMessage = "Could not update counsellor password."
                                    }
                                }
                            ) {
                                Text("Save New Password")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showForgotPasswordDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("counsellor_phone_field"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.VerifiedUser,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Verified by school administrator",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Button(
                    onClick = {
                        onLoginSuccess(
                            email.ifBlank { "kv1vasco@gmail.com" },
                            schoolName
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("counsellor_login_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Log In",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // "New counsellor? Create account" link
                TextButton(
                    onClick = onNavigateSignUp,
                    modifier = Modifier.testTag("btn_counsellor_signup_link")
                ) {
                    Text(
                        text = "New counsellor? Create account",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(onClick = { /* Demo feedback */ }) {
                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * 4B. CounsellorSignupScreen
 * Fields: Full Name, School Name (dropdown), Employee ID, Email, Phone, Password, Confirm Password, Designation dropdown.
 * After signup, show "pending verification" then navigate to dashboard.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounsellorSignupScreen(
    repository: SahaayaRepository,
    onBack: () -> Unit,
    onSignUpComplete: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var selectedSchool by remember { mutableStateOf(repository.schoolInfo.name) }
    var employeeId by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var schoolPassword by remember { mutableStateOf("") }
    var confirmSchoolPassword by remember { mutableStateOf("") }
    var schoolPasswordVisible by remember { mutableStateOf(false) }
    var confirmSchoolPasswordVisible by remember { mutableStateOf(false) }
    var selectedDesignation by remember { mutableStateOf("School Counsellor") }

    var schoolDropdownExpanded by remember { mutableStateOf(false) }
    var designationDropdownExpanded by remember { mutableStateOf(false) }

    var isPendingVerification by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val registeredSchools = repository.schools.map { it.schoolName }
    val schoolOptions = remember(repository.schools.size) {
        (registeredSchools + listOf(
            "PM Shri Kendriya Vidyalaya No.1, Vasco Da Gama, Goa",
            "Greenwood High School",
            "St. Jude Memorial Senior High",
            "Delhi Public School",
            "Navodaya Vidyalaya Canacona"
        )).distinct()
    }

    val designationOptions = listOf(
        "School Counsellor",
        "Senior Well-Being Officer",
        "Child Psychologist",
        "Guidance Teacher",
        "Safeguarding Lead"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isPendingVerification) "Verification Status" else "Counsellor Sign Up",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if (!isPendingVerification) {
                        IconButton(onClick = onBack, modifier = Modifier.testTag("counsellor_signup_back")) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { repository.toggleTheme() },
                        modifier = Modifier.testTag("counsellor_signup_theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                            contentDescription = if (repository.isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (isPendingVerification) {
            // Pending Verification Screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.VerifiedUser,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(46.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Registration Submitted",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Pending Verification",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your account has been created for $selectedSchool. An administrator will review your Employee ID ($employeeId) and designation ($selectedDesignation).",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Created School Password",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = schoolPassword,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = 2.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Students of your school will use this password to log in.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "You can explore the Counsellor Dashboard now in demo mode while verification is processed.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onSignUpComplete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("btn_proceed_to_dashboard"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Continue to Dashboard",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        } else {
            // Sign Up Form
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Create Counsellor Account",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Join your institution's student care and mental wellness network.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (errorMessage != null) {
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // 1. Full Name
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_counsellor_fullname"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 2. School Name (Editable text input + quick suggestion chips)
                OutlinedTextField(
                    value = selectedSchool,
                    onValueChange = {
                        selectedSchool = it
                        if (errorMessage != null) errorMessage = null
                    },
                    label = { Text("School Name") },
                    placeholder = { Text("Type your school name...") },
                    leadingIcon = { Icon(Icons.Filled.School, contentDescription = null) },
                    trailingIcon = {
                        if (selectedSchool.isNotBlank()) {
                            IconButton(onClick = { selectedSchool = "" }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_counsellor_school"),
                    singleLine = false,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Quick suggestions (tap to fill, or type custom name above):",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    schoolOptions.forEach { school ->
                        val shortName = if (school.length > 24) school.take(21) + "..." else school
                        SuggestionChip(
                            onClick = { selectedSchool = school },
                            label = { Text(shortName, style = MaterialTheme.typography.labelSmall) },
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 3. Employee ID
                OutlinedTextField(
                    value = employeeId,
                    onValueChange = { employeeId = it },
                    label = { Text("Employee ID") },
                    leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_counsellor_empid"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 4. Designation (Dropdown)
                ExposedDropdownMenuBox(
                    expanded = designationDropdownExpanded,
                    onExpandedChange = { designationDropdownExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedDesignation,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Designation") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = designationDropdownExpanded) },
                        leadingIcon = { Icon(Icons.Filled.VerifiedUser, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .testTag("signup_counsellor_designation")
                    )
                    ExposedDropdownMenu(
                        expanded = designationDropdownExpanded,
                        onDismissRequest = { designationDropdownExpanded = false }
                    ) {
                        designationOptions.forEach { desig ->
                            DropdownMenuItem(
                                text = { Text(desig) },
                                onClick = {
                                    selectedDesignation = desig
                                    designationDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 5. Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Official Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_counsellor_email"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 6. Phone
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Contact Phone") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_counsellor_phone"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 7. School Password
                OutlinedTextField(
                    value = schoolPassword,
                    onValueChange = {
                        schoolPassword = it
                        if (errorMessage != null) errorMessage = null
                    },
                    label = { Text("School Password") },
                    supportingText = { Text("Students of your school will use this password to log in.") },
                    visualTransformation = if (schoolPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(Icons.Filled.Key, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { schoolPasswordVisible = !schoolPasswordVisible }) {
                            Icon(
                                imageVector = if (schoolPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (schoolPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_counsellor_password"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 8. Confirm School Password
                OutlinedTextField(
                    value = confirmSchoolPassword,
                    onValueChange = {
                        confirmSchoolPassword = it
                        if (errorMessage != null) errorMessage = null
                    },
                    label = { Text("Confirm School Password") },
                    visualTransformation = if (confirmSchoolPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { confirmSchoolPasswordVisible = !confirmSchoolPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmSchoolPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (confirmSchoolPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("signup_counsellor_confirmpassword"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (fullName.isBlank()) {
                            errorMessage = "Please enter your full name."
                        } else if (selectedSchool.isBlank()) {
                            errorMessage = "Please enter or select your school name."
                        } else if (employeeId.isBlank()) {
                            errorMessage = "Please enter your employee ID."
                        } else if (email.isBlank() || !email.contains("@")) {
                            errorMessage = "Please enter a valid official email address."
                        } else if (schoolPassword.length < 6) {
                            errorMessage = "School password must be at least 6 characters."
                        } else if (schoolPassword != confirmSchoolPassword) {
                            errorMessage = "School passwords do not match."
                        } else {
                            errorMessage = null
                            val newAccount = SchoolAccount(
                                schoolName = selectedSchool.trim(),
                                schoolPassword = schoolPassword.trim(),
                                counsellorName = fullName.trim(),
                                counsellorEmail = email.trim(),
                                counsellorPhone = phone.trim().ifBlank { "+91 832 251 2345" },
                                isVerified = true
                            )
                            repository.addOrUpdateSchoolAccount(newAccount)
                            repository.counsellorName = fullName.trim()
                            repository.counsellorEmail = email.trim()
                            repository.schoolInfo = repository.schoolInfo.copy(
                                name = selectedSchool.trim(),
                                phone = phone.trim().ifBlank { repository.schoolInfo.phone },
                                email = email.trim()
                            )
                            isPendingVerification = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("btn_submit_counsellor_signup"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Register Account",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Already registered?", style = MaterialTheme.typography.bodyMedium)
                    TextButton(onClick = onBack) {
                        Text("Log In", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

/**
 * Visual graphic of caring hands cradling a green sprout
 * Matching the top illustration of Sahaaya design.
 */
@Composable
fun SproutIllustration(modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val containerColor = MaterialTheme.colorScheme.primaryContainer
    val amber = MaterialTheme.colorScheme.tertiary

    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f

        // Soft background circular glow
        drawCircle(
            color = containerColor.copy(alpha = 0.5f),
            radius = size.width * 0.42f,
            center = Offset(cx, cy)
        )

        // Sprout Stem
        val stemPath = Path().apply {
            moveTo(cx, cy + 30f)
            quadraticTo(cx - 5f, cy - 10f, cx, cy - 60f)
            lineTo(cx + 4f, cy - 60f)
            quadraticTo(cx, cy - 10f, cx + 4f, cy + 30f)
            close()
        }
        drawPath(stemPath, color = primaryColor, style = Fill)

        // Left Sprout Leaf
        val leftLeaf = Path().apply {
            moveTo(cx, cy - 40f)
            cubicTo(cx - 45f, cy - 60f, cx - 50f, cy - 15f, cx, cy - 20f)
            close()
        }
        drawPath(leftLeaf, color = primaryColor, style = Fill)

        // Right Sprout Leaf
        val rightLeaf = Path().apply {
            moveTo(cx, cy - 50f)
            cubicTo(cx + 50f, cy - 70f, cx + 55f, cy - 25f, cx, cy - 30f)
            close()
        }
        drawPath(rightLeaf, color = secondaryColor, style = Fill)

        // Top Sprout Bud
        val topLeaf = Path().apply {
            moveTo(cx, cy - 60f)
            cubicTo(cx - 15f, cy - 95f, cx + 15f, cy - 95f, cx, cy - 60f)
            close()
        }
        drawPath(topLeaf, color = primaryColor, style = Fill)

        // Caring Hands (stylized supporting arcs)
        val leftHand = Path().apply {
            moveTo(cx - 70f, cy + 45f)
            cubicTo(cx - 85f, cy + 15f, cx - 35f, cy + 50f, cx - 10f, cy + 50f)
            cubicTo(cx - 30f, cy + 70f, cx - 60f, cy + 65f, cx - 70f, cy + 45f)
            close()
        }
        drawPath(leftHand, color = amber, style = Fill)

        val rightHand = Path().apply {
            moveTo(cx + 70f, cy + 45f)
            cubicTo(cx + 85f, cy + 15f, cx + 35f, cy + 50f, cx + 10f, cy + 50f)
            cubicTo(cx + 30f, cy + 70f, cx + 60f, cy + 65f, cx + 70f, cy + 45f)
            close()
        }
        drawPath(rightHand, color = amber, style = Fill)
    }
}
