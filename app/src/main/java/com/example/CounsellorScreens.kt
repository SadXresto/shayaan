package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Main Counsellor Container with Bottom Navigation:
 * Dashboard | Classes | Support | Reports | Setup
 */
@Composable
fun CounsellorMainContainer(
    repository: SahaayaRepository,
    onLogout: () -> Unit,
    onNavigateEmergency: () -> Unit,
    onNavigateChatHub: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var viewingClassDetail by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 6.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0 && viewingClassDetail == null,
                    onClick = {
                        viewingClassDetail = null
                        selectedTab = 0
                    },
                    icon = { Icon(Icons.Filled.Dashboard, contentDescription = "Dashboard") },
                    label = { Text("Dashboard", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1 || viewingClassDetail != null,
                    onClick = {
                        viewingClassDetail = null
                        selectedTab = 1
                    },
                    icon = { Icon(Icons.Filled.Group, contentDescription = "Classes") },
                    label = { Text("Classes", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2 && viewingClassDetail == null,
                    onClick = {
                        viewingClassDetail = null
                        selectedTab = 2
                    },
                    icon = { Icon(Icons.Filled.SupportAgent, contentDescription = "Support") },
                    label = { Text("Support", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3 && viewingClassDetail == null,
                    onClick = {
                        viewingClassDetail = null
                        selectedTab = 3
                    },
                    icon = { Icon(Icons.Filled.Report, contentDescription = "Reports") },
                    label = { Text("Reports", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 4 && viewingClassDetail == null,
                    onClick = {
                        viewingClassDetail = null
                        selectedTab = 4
                    },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Setup") },
                    label = { Text("Setup", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (viewingClassDetail != null) {
                ClassDetailScreen(
                    className = viewingClassDetail!!,
                    repository = repository,
                    onBack = { viewingClassDetail = null }
                )
            } else {
                when (selectedTab) {
                    0 -> CounsellorDashboardScreen(
                        repository = repository,
                        onSwitchRole = onLogout,
                        onNavigateEmergency = onNavigateEmergency,
                        onGoToClasses = { selectedTab = 1 },
                        onGoToSupport = { selectedTab = 2 },
                        onGoToReports = { selectedTab = 3 },
                        onNavigateChatHub = onNavigateChatHub,
                        onGoToSetup = { selectedTab = 4 }
                    )
                    1 -> ClassWiseScreen(
                        repository = repository,
                        onSelectClass = { cls -> viewingClassDetail = cls }
                    )
                    2 -> SupportRequiredScreen(
                        repository = repository
                    )
                    3 -> FriendReportsScreen(
                        repository = repository
                    )
                    4 -> SchoolSetupScreen(
                        repository = repository,
                        onLogout = onLogout
                    )
                }
            }
        }
    }
}

/**
 * 9. CounsellorDashboardScreen (Matching Image 15)
 * - TopAppBar: School name
 * - Welcome, Ms. Sharma (School Counsellor)
 * - 5 summary cards: Total Students, Checked In Today, Awaiting Follow-Up, Friend Reports, Support Requests
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounsellorDashboardScreen(
    repository: SahaayaRepository,
    onSwitchRole: () -> Unit,
    onNavigateEmergency: () -> Unit,
    onGoToClasses: () -> Unit,
    onGoToSupport: () -> Unit,
    onGoToReports: () -> Unit,
    onNavigateChatHub: () -> Unit = {},
    onGoToSetup: () -> Unit = {}
) {
    val totalStudents = repository.students.size
    val checkedInCount = repository.students.count { it.checkedInToday }
    val awaitingFollowUp = repository.counsellorCases.count { it.status != "Resolved" }
    val friendReportsCount = repository.friendReports.size
    val supportRequestsCount = repository.supportRequests.size

    var selectedCaseForReview by remember { mutableStateOf<CounsellorCase?>(null) }
    var reviewNotice by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = repository.schoolInfo.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Counsellor Portal",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { repository.toggleTheme() },
                        modifier = Modifier.testTag("counsellor_dashboard_theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                            contentDescription = if (repository.isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(
                        onClick = onNavigateChatHub,
                        modifier = Modifier.testTag("counsellor_chat_hub_top_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Chat,
                            contentDescription = "Direct Student Messages",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    TextButton(
                        onClick = onSwitchRole,
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Text("Switch Role", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Welcome banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Welcome, ${repository.counsellorName}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Designated School Well-Being Counsellor",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // School Password Quick Access Card
            var showPwOnDash by remember { mutableStateOf(false) }
            val currentPw = repository.currentSchoolPassword
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_school_password_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(
                            imageVector = Icons.Filled.Key,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Student Login Password",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = if (showPwOnDash) currentPw else "••••••••",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { showPwOnDash = !showPwOnDash }) {
                            Icon(
                                imageVector = if (showPwOnDash) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (showPwOnDash) "Hide password" else "Show password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        TextButton(onClick = onGoToSetup) {
                            Text("Manage", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Direct Student Messages Card
            val directMsgCount = repository.directMessages.size
            val studentQuestionsCount = repository.directMessages.count { it.isFromStudent }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateChatHub() }
                    .testTag("dashboard_direct_chat_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Chat,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Direct Student Messages",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (studentQuestionsCount > 0)
                                    "$studentQuestionsCount active student inquiries • Tap to reply"
                                else
                                    "Direct 1-on-1 confidential chat channel",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (studentQuestionsCount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (studentQuestionsCount > 0) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                    Button(
                        onClick = onNavigateChatHub,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Open Chat", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Today's Overview",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 5 summary cards
            // Row 1: Total & Checked in
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryStatCard(
                    count = "$totalStudents",
                    label = "Total Students",
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    testTag = "stat_total_students",
                    modifier = Modifier.weight(1f),
                    onClick = onGoToClasses
                )
                SummaryStatCard(
                    count = "$checkedInCount",
                    label = "Checked In Today",
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    testTag = "stat_checked_in",
                    modifier = Modifier.weight(1f),
                    onClick = onGoToClasses
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Row 2: Follow-up & Friend Reports
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryStatCard(
                    count = "$awaitingFollowUp",
                    label = "Awaiting Follow-Up",
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    testTag = "stat_awaiting_followup",
                    modifier = Modifier.weight(1f),
                    onClick = onGoToSupport
                )
                SummaryStatCard(
                    count = "$friendReportsCount",
                    label = "Friend Reports",
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    testTag = "stat_friend_reports",
                    modifier = Modifier.weight(1f),
                    onClick = onGoToReports
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Row 3: Support Requests
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryStatCard(
                    count = "$supportRequestsCount",
                    label = "Support Requests",
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    testTag = "stat_support_requests",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onGoToSupport
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Needs Review Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Needs Review (${repository.counsellorCases.size})",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = onGoToSupport) {
                    Text("View All →", style = MaterialTheme.typography.labelMedium)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            repository.counsellorCases.take(3).forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable { selectedCaseForReview = item },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = item.studentName,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = item.studentClass,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            SuggestionChip(
                                onClick = { selectedCaseForReview = item },
                                label = { Text(item.urgency, style = MaterialTheme.typography.labelSmall) },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = if (item.urgency == "High") MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                                    labelColor = if (item.urgency == "High") MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.notes,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Logged ${item.timestamp}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Button(
                                onClick = { selectedCaseForReview = item },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text("Review", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    if (selectedCaseForReview != null) {
        val item = selectedCaseForReview!!
        AlertDialog(
            onDismissRequest = { selectedCaseForReview = null },
            title = { Text("Review Case: ${item.studentName}") },
            text = {
                Column {
                    Text(
                        text = "Class: ${item.studentClass} • Category: ${item.category}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = item.notes, style = MaterialTheme.typography.bodyMedium)
                    if (item.situationReport.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Psychology,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Situation Synthesis (Clinical Insight):",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.situationReport,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Status: ${item.status} • Urgency: ${item.urgency}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        repository.updateCaseStatus(item.id, "Scheduled", "Follow-up session scheduled in Room 204")
                        reviewNotice = "Scheduled private consultation for ${item.studentName}."
                        selectedCaseForReview = null
                    }
                ) {
                    Text("Schedule Follow-Up")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        repository.updateCaseStatus(item.id, "Resolved", "Marked resolved by counsellor")
                        reviewNotice = "Marked ${item.studentName}'s alert as resolved."
                        selectedCaseForReview = null
                    }
                ) {
                    Text("Mark Resolved")
                }
            }
        )
    }

    if (reviewNotice != null) {
        AlertDialog(
            onDismissRequest = { reviewNotice = null },
            title = { Text("Action Updated") },
            text = { Text(reviewNotice ?: "") },
            confirmButton = {
                TextButton(onClick = { reviewNotice = null }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun SummaryStatCard(
    count: String,
    label: String,
    containerColor: Color,
    contentColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(98.dp)
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor, contentColor = contentColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = count,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = contentColor
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = contentColor
            )
        }
    }
}

/**
 * 10. ClassWiseScreen (Requirement 10)
 * - List: Class 6, 7, 8, 9, 10, 11, 12
 * - Each shows "Checked In: 32/40"
 * - Tap → ClassDetailScreen with student list (Name — Checked In)
 * - Only counsellor sees this
 */
@Composable
fun ClassWiseScreen(
    repository: SahaayaRepository,
    onSelectClass: (String) -> Unit
) {
    val classes = listOf("Class 6", "Class 7", "Class 8", "Class 9", "Class 10", "Class 11", "Class 12")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Class-Wise Overview",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "School attendance & check-in participation. Confidential to counsellor.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        classes.forEach { className ->
            val classStudents = repository.students.filter { it.classSection.startsWith(className, ignoreCase = true) }
            val checkedInCount = classStudents.count { it.checkedInToday }
            val totalInClass = classStudents.size
            val checkedRatioText = if (totalInClass == 0) "No registered students" else "Checked In: $checkedInCount / $totalInClass"

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onSelectClass(className) }
                    .testTag("class_card_${className.replace(" ", "_")}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = className,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = checkedRatioText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

/**
 * ClassDetailScreen
 * Shows student list for the selected class, with real-time "Checked In" status
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailScreen(
    className: String,
    repository: SahaayaRepository,
    onBack: () -> Unit
) {
    val studentsInClass = repository.students.filter { it.classSection.startsWith(className) }
    var actionDialogStudent by remember { mutableStateOf<Student?>(null) }
    var sentNudgeMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$className Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Enrolled Students",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "View individual student check-in participation and mood logs.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (studentsInClass.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.School,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No Registered Students in $className",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Student privacy is protected. When real students in $className register or log daily check-ins, their verified records will be displayed here.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                studentsInClass.forEach { student ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable { actionDialogStudent = student },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(if (student.checkedInToday) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = null,
                                        tint = if (student.checkedInToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column {
                                    Text(student.name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = if (student.checkedInToday) "Checked In Today ${if (student.mood.isNotBlank()) "• ${student.mood}" else ""}" else "Check-in Pending",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (student.checkedInToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Surface(
                                color = if (student.checkedInToday) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (student.checkedInToday) "Done" else "Pending",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (student.checkedInToday) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    if (actionDialogStudent != null) {
        val s = actionDialogStudent!!
        AlertDialog(
            onDismissRequest = { actionDialogStudent = null },
            title = { Text("Student: ${s.name}") },
            text = {
                Column {
                    Text("Class: ${s.classSection} • Streak: ${s.streak} days")
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Today's Status: ${if (s.checkedInToday) "Completed (${s.mood})" else "Pending Check-In"}")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        sentNudgeMessage = "Sent a gentle well-being reminder to ${s.name}."
                        actionDialogStudent = null
                    }
                ) {
                    Text("Send Friendly Nudge")
                }
            },
            dismissButton = {
                TextButton(onClick = { actionDialogStudent = null }) {
                    Text("Close")
                }
            }
        )
    }

    if (sentNudgeMessage != null) {
        AlertDialog(
            onDismissRequest = { sentNudgeMessage = null },
            title = { Text("Notification Sent") },
            text = { Text(sentNudgeMessage ?: "") },
            confirmButton = {
                TextButton(onClick = { sentNudgeMessage = null }) {
                    Text("OK")
                }
            }
        )
    }
}

/**
 * 11. SupportRequiredScreen (Needs Review)
 * Categories: Follow-up requested | Conversation requested | Concern reported | Needs review
 * Card per student: name, class, category, timestamp
 * Actions: Mark Reviewed | Schedule Follow-up | Add Notes | Mark Resolved
 */
@Composable
fun SupportRequiredScreen(
    repository: SahaayaRepository
) {
    var selectedCategoryTab by remember { mutableStateOf("All") }
    val categories = listOf("All", "Follow-up requested", "Conversation requested", "Concern reported", "Needs review")

    var activeCaseForAction by remember { mutableStateOf<CounsellorCase?>(null) }
    var actionMessage by remember { mutableStateOf<String?>(null) }

    val filteredCases = if (selectedCategoryTab == "All") {
        repository.counsellorCases
    } else {
        repository.counsellorCases.filter { it.category.equals(selectedCategoryTab, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Support Required",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Prioritized safeguarding queue. Take action to follow up or mark reviewed.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        ScrollableTabRow(
            selectedTabIndex = categories.indexOf(selectedCategoryTab).coerceAtLeast(0),
            containerColor = MaterialTheme.colorScheme.surface,
            edgePadding = 0.dp
        ) {
            categories.forEach { cat ->
                Tab(
                    selected = selectedCategoryTab == cat,
                    onClick = { selectedCategoryTab = cat },
                    text = { Text(cat, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (filteredCases.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No active cases in this category", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("All student alerts here have been reviewed and addressed.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            filteredCases.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { activeCaseForAction = item }
                        .testTag("support_case_${item.id}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(item.studentName, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                Text(item.studentClass, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            Surface(
                                color = if (item.urgency == "High") MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "${item.urgency} Urgency",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (item.urgency == "High") MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Category: ${item.category}",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = item.notes, style = MaterialTheme.typography.bodyMedium)

                        if (item.situationReport.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "Clinical Situation Synthesis:",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = item.situationReport,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Status: ${item.status} • ${item.timestamp}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Button(
                                onClick = { activeCaseForAction = item },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text("Actions", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (activeCaseForAction != null) {
        val c = activeCaseForAction!!
        AlertDialog(
            onDismissRequest = { activeCaseForAction = null },
            title = { Text("Case Actions: ${c.studentName}") },
            text = {
                Column {
                    Text("Select an action to update this case:", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            repository.updateCaseStatus(c.id, "Reviewed", "Counsellor acknowledged and reviewed.")
                            actionMessage = "Marked case as Reviewed."
                            activeCaseForAction = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Mark Reviewed")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            repository.updateCaseStatus(c.id, "Scheduled", "Follow-up session scheduled in Room 204.")
                            actionMessage = "Follow-up scheduled with ${c.studentName}."
                            activeCaseForAction = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Schedule Follow-Up")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            repository.updateCaseStatus(c.id, "Resolved", "Safeguarding follow-up concluded.")
                            actionMessage = "Case marked as Resolved."
                            activeCaseForAction = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Mark Resolved")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { activeCaseForAction = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (actionMessage != null) {
        AlertDialog(
            onDismissRequest = { actionMessage = null },
            title = { Text("Status Updated") },
            text = { Text(actionMessage ?: "") },
            confirmButton = {
                TextButton(onClick = { actionMessage = null }) {
                    Text("OK")
                }
            }
        )
    }
}

/**
 * 12. FriendReportsScreen
 * Card: Reporter name, Concerned student, Date, Category
 * Status chips: New → Under Review → Follow-Up → Closed
 * Tap → detail + status update
 */
@Composable
fun FriendReportsScreen(
    repository: SahaayaRepository
) {
    var filterStatus by remember { mutableStateOf("All") }
    val statuses = listOf("All", "New", "Under Review", "Follow-Up", "Closed")

    var selectedReportForUpdate by remember { mutableStateOf<FriendReport?>(null) }

    val displayedReports = if (filterStatus == "All") {
        repository.friendReports
    } else {
        repository.friendReports.filter { it.status.equals(filterStatus, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Friend Reports",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Peer safeguarding notices submitted by students.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            statuses.forEach { status ->
                FilterChip(
                    selected = filterStatus == status,
                    onClick = { filterStatus = status },
                    label = { Text(status, style = MaterialTheme.typography.labelSmall) },
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (displayedReports.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No friend reports matching '$filterStatus'", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            }
        } else {
            displayedReports.forEach { report ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selectedReportForUpdate = report }
                        .testTag("report_card_${report.id}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Concerned: ${report.concernedName}",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "Reported by: ${report.reporterName} • ${report.classSection}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                color = when (report.status) {
                                    "New" -> MaterialTheme.colorScheme.errorContainer
                                    "Under Review" -> MaterialTheme.colorScheme.tertiaryContainer
                                    "Follow-Up" -> MaterialTheme.colorScheme.secondaryContainer
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = report.status,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = report.concern, style = MaterialTheme.typography.bodyMedium)

                        if (report.notes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Notes: ${report.notes}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = report.timestamp,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = "Tap to update status →",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (selectedReportForUpdate != null) {
        val r = selectedReportForUpdate!!
        AlertDialog(
            onDismissRequest = { selectedReportForUpdate = null },
            title = { Text("Update Status: ${r.concernedName}") },
            text = {
                Column {
                    Text("Select new workflow status for this safeguarding report:")
                    Spacer(modifier = Modifier.height(14.dp))
                    listOf("New", "Under Review", "Follow-Up", "Closed").forEach { st ->
                        Button(
                            onClick = {
                                repository.updateFriendReportStatus(r.id, st)
                                selectedReportForUpdate = null
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (r.status == st) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = if (r.status == st) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Text(st)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedReportForUpdate = null }) {
                    Text("Dismiss")
                }
            }
        )
    }
}

/**
 * 13. SchoolSetupScreen (Admin)
 * Configure: School Name, Phone, Counsellor Account, Classes, Student Accounts, Check-in Frequency
 */
@Composable
fun SchoolSetupScreen(
    repository: SahaayaRepository,
    onLogout: () -> Unit
) {
    var schoolName by remember { mutableStateOf(repository.schoolInfo.name) }
    var phone by remember { mutableStateOf(repository.schoolInfo.phone) }
    var email by remember { mutableStateOf(repository.schoolInfo.email) }
    var address by remember { mutableStateOf(repository.schoolInfo.address) }
    var frequency by remember { mutableStateOf(repository.checkInFrequency) }

    var passwordVisible by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var newPasswordInput by remember { mutableStateOf("") }
    var confirmPasswordInput by remember { mutableStateOf("") }
    var changePasswordError by remember { mutableStateOf<String?>(null) }
    val clipboardManager = LocalClipboardManager.current
    var copySuccessMessage by remember { mutableStateOf(false) }

    var saveFeedback by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "School Setup & Admin",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Configure institutional details and well-being parameters.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = schoolName,
            onValueChange = { schoolName = it },
            label = { Text("School Name") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("school_setup_name_input")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("School Helpline Phone") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("school_setup_phone_input")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("School Official Email") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("school_setup_email_input")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("School Address") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("school_setup_address_input")
        )

        Spacer(modifier = Modifier.height(18.dp))

        // School Password Card
        val currentPassword = repository.currentSchoolPassword
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("school_password_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Key,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "School Password",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.testTag("toggle_school_password_visibility")
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(currentPassword))
                                copySuccessMessage = true
                            },
                            modifier = Modifier.testTag("copy_school_password_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "Copy password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (passwordVisible) currentPassword else "••••••••",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = if (passwordVisible) 1.5.sp else 4.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (copySuccessMessage) {
                            Text(
                                text = "Copied!",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Share this password with your students so they can log in.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        newPasswordInput = ""
                        confirmPasswordInput = ""
                        changePasswordError = null
                        showChangePasswordDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("change_school_password_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Change Password")
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text("Check-In Frequency", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("Daily", "Twice Weekly", "Weekly").forEach { opt ->
                FilterChip(
                    selected = frequency == opt,
                    onClick = { frequency = opt },
                    label = { Text(opt) },
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Enrolled Classes: 7 (Class 6 - Class 12)", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(4.dp))
                Text("Total Student Accounts: ${repository.students.size} Registered", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Appearance & Theme Mode Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Theme Mode",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (repository.isDarkTheme) "Dark mode is currently ON" else "Light mode is currently ON",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { repository.toggleTheme() },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("admin_theme_toggle_btn")
                    ) {
                        Icon(
                            imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (repository.isDarkTheme) "Switch to Light" else "Switch to Dark")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                repository.schoolInfo = SchoolInfo(
                    name = schoolName.trim(),
                    phone = phone.trim(),
                    email = email.trim(),
                    address = address.trim()
                )
                repository.checkInFrequency = frequency
                saveFeedback = "Institutional details updated and synchronized across student and counsellor portals."
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("save_school_setup_btn"),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save Settings", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("counsellor_logout_btn"),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Log Out")
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (showChangePasswordDialog) {
        var newPwVisible by remember { mutableStateOf(false) }
        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            title = { Text("Change School Password") },
            text = {
                Column {
                    Text(
                        text = "Set a new password for students of ${schoolName.ifBlank { repository.schoolInfo.name }}:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (changePasswordError != null) {
                        Text(
                            text = changePasswordError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    OutlinedTextField(
                        value = newPasswordInput,
                        onValueChange = {
                            newPasswordInput = it
                            if (changePasswordError != null) changePasswordError = null
                        },
                        label = { Text("New Password (min 6 chars)") },
                        visualTransformation = if (newPwVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { newPwVisible = !newPwVisible }) {
                                Icon(
                                    imageVector = if (newPwVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("new_password_input"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = confirmPasswordInput,
                        onValueChange = {
                            confirmPasswordInput = it
                            if (changePasswordError != null) changePasswordError = null
                        },
                        label = { Text("Confirm New Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("confirm_new_password_input"),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val trimmedNew = newPasswordInput.trim()
                        if (trimmedNew.length < 6) {
                            changePasswordError = "Password must be at least 6 characters."
                        } else if (trimmedNew != confirmPasswordInput.trim()) {
                            changePasswordError = "Passwords do not match."
                        } else {
                            repository.updateSchoolPassword(schoolName.trim().ifBlank { repository.schoolInfo.name }, trimmedNew)
                            showChangePasswordDialog = false
                            saveFeedback = "School password updated successfully! Students must now use the new password."
                        }
                    },
                    modifier = Modifier.testTag("submit_new_password_btn")
                ) {
                    Text("Update Password")
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (saveFeedback != null) {
        AlertDialog(
            onDismissRequest = { saveFeedback = null },
            title = { Text("Configuration Updated") },
            text = { Text(saveFeedback ?: "") },
            confirmButton = {
                TextButton(onClick = { saveFeedback = null }) {
                    Text("OK")
                }
            }
        )
    }
}

/**
 * CounsellorChatHubScreen:
 * Real-life 1-on-1 direct messaging hub between School Counsellor and students.
 * - Thread list with students who sent messages or registered students
 * - Live thread view with message bubbles, situation context header
 * - Quick empathetic clinical reply templates
 * - Direct sending with repository.sendCounsellorDirectMessage(...)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounsellorChatHubScreen(
    repository: SahaayaRepository,
    onBack: () -> Unit
) {
    // Current thread selected
    var selectedStudentId by remember { mutableStateOf<String?>(null) }
    var selectedStudentName by remember { mutableStateOf("") }
    var selectedStudentClass by remember { mutableStateOf("") }

    var replyText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Collect all students who either have messages or are registered
    val messageStudentIds = repository.directMessages.map { it.studentId }.distinct()
    val allActiveStudents = remember(repository.directMessages.size, repository.students.size) {
        val list = mutableListOf<Triple<String, String, String>>() // id, name, class
        // First add students who have messaged
        repository.directMessages.reversed().forEach { msg ->
            if (list.none { it.first == msg.studentId }) {
                list.add(Triple(msg.studentId, msg.studentName, msg.studentClass))
            }
        }
        // Then add other registered students
        repository.students.forEach { std ->
            if (list.none { it.first == std.id }) {
                list.add(Triple(std.id, std.name, std.classSection))
            }
        }
        list
    }

    val counsellorTemplates = listOf(
        "Hello! I received your note. Please know you are supported and safe here.",
        "Would you like to drop by the counselling room during recess or zero period?",
        "Take a few slow, steady breaths. You don't have to carry this alone.",
        "Thank you for sharing with me. I have scheduled a confidential slot for us."
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (selectedStudentId != null) selectedStudentName else "Direct Student Messages",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (selectedStudentId != null) "$selectedStudentClass • Confidential Channel" else "${allActiveStudents.size} student threads",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (selectedStudentId != null) {
                                selectedStudentId = null
                            } else {
                                onBack()
                            }
                        },
                        modifier = Modifier.testTag("counsellor_chat_hub_back")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { repository.toggleTheme() },
                        modifier = Modifier.testTag("counsellor_chat_theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                            contentDescription = "Toggle Theme"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            if (selectedStudentId != null) {
                Surface(
                    tonalElevation = 6.dp,
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        // Quick reply templates
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            counsellorTemplates.forEach { template ->
                                SuggestionChip(
                                    onClick = {
                                        replyText = template
                                    },
                                    label = { Text(template.take(28) + "...", style = MaterialTheme.typography.labelSmall) }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = replyText,
                                onValueChange = { replyText = it },
                                placeholder = { Text("Type confidential reply to student...", style = MaterialTheme.typography.bodyMedium) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("counsellor_reply_input"),
                                shape = RoundedCornerShape(24.dp),
                                maxLines = 4
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = {
                                    if (replyText.isNotBlank() && selectedStudentId != null) {
                                        val text = replyText.trim()
                                        replyText = ""
                                        repository.sendCounsellorDirectMessage(
                                            studentId = selectedStudentId!!,
                                            studentName = selectedStudentName,
                                            studentClass = selectedStudentClass,
                                            messageText = text
                                        )
                                        coroutineScope.launch {
                                            val threadMessages = repository.directMessages.filter { it.studentId == selectedStudentId }
                                            if (threadMessages.isNotEmpty()) {
                                                listState.animateScrollToItem(threadMessages.size - 1)
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .testTag("counsellor_reply_send_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Send Reply",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (selectedStudentId == null) {
            // Thread List View
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Direct student chats are confidential and stored on this school instance. Reply with warmth and schedule in-person time when necessary.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Active Student Channels",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (allActiveStudents.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Filled.Chat, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(44.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("No Direct Messages Yet", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "When students reach out through their chat or request support, conversation threads will appear here.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    allActiveStudents.forEach { (id, name, cls) ->
                        val threadMessages = repository.directMessages.filter { it.studentId == id }
                        val lastMessage = threadMessages.lastOrNull()
                        val unreadCount = threadMessages.count { it.isFromStudent }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {
                                    selectedStudentId = id
                                    selectedStudentName = name
                                    selectedStudentClass = cls
                                }
                                .testTag("chat_thread_${id}"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(CircleShape)
                                            .background(if (unreadCount > 0) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = name.take(1).uppercase(),
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = if (unreadCount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(14.dp))

                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = name,
                                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Surface(
                                                color = MaterialTheme.colorScheme.surfaceVariant,
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text(
                                                    text = cls,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = lastMessage?.message ?: "Start confidential conversation",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (unreadCount > 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontWeight = if (unreadCount > 0) FontWeight.SemiBold else FontWeight.Normal,
                                            maxLines = 1
                                        )
                                    }
                                }

                                if (unreadCount > 0) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape,
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "$unreadCount",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        } else {
            // Live Student Chat Thread
            val threadMessages = repository.directMessages.filter { it.studentId == selectedStudentId }
            val studentCheckIn = repository.checkIns.firstOrNull { it.studentId == selectedStudentId || it.studentName == selectedStudentName }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Situation context card banner
                if (studentCheckIn != null && studentCheckIn.situationReport.isNotBlank()) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Psychology,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Student Context: ${studentCheckIn.mood} • ${studentCheckIn.timestamp}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = studentCheckIn.situationReport,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 3
                            )
                        }
                    }
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (threadMessages.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No direct messages exchanged with $selectedStudentName yet.\nSend an encouraging message or invitation below.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        items(threadMessages) { msg ->
                            val isCounsellor = !msg.isFromStudent
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = if (isCounsellor) Alignment.End else Alignment.Start
                            ) {
                                Surface(
                                    color = if (isCounsellor) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = if (isCounsellor) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isCounsellor) 16.dp else 4.dp,
                                        bottomEnd = if (isCounsellor) 4.dp else 16.dp
                                    ),
                                    modifier = Modifier.widthIn(max = 300.dp)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text(
                                            text = if (isCounsellor) "You (Counsellor)" else msg.senderName,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (isCounsellor) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f) else MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = msg.message,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = msg.timestamp,
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                            color = if (isCounsellor) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
