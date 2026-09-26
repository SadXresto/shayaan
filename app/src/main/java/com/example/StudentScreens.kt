package com.example

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.launch

/**
 * Main Student Screen Container with Bottom Navigation:
 * Home | Check-In | Garden / Streak | Support | Profile
 */
@Composable
fun StudentMainContainer(
    repository: SahaayaRepository,
    onLogout: () -> Unit,
    onNavigateEmergency: () -> Unit,
    onNavigateFriendConcern: () -> Unit,
    onNavigateNeedToTalk: () -> Unit,
    onNavigateLearningHub: () -> Unit,
    onNavigateChat: () -> Unit = {},
    onNavigateStressManagement: () -> Unit = {},
    onNavigateSupportingFriends: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 6.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Check-In") },
                    label = { Text("Check-In", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Filled.Eco, contentDescription = "Garden") },
                    label = { Text("Garden", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Support") },
                    label = { Text("Support", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                    label = { Text("Profile", style = MaterialTheme.typography.labelSmall) },
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
            when (selectedTab) {
                0 -> StudentHomeScreen(
                    repository = repository,
                    onGoToCheckIn = { selectedTab = 1 },
                    onGoToGarden = { selectedTab = 2 },
                    onGoToSupport = { selectedTab = 3 },
                    onNavigateFriendConcern = onNavigateFriendConcern,
                    onNavigateNeedToTalk = onNavigateNeedToTalk,
                    onNavigateLearningHub = onNavigateLearningHub,
                    onNavigateEmergency = onNavigateEmergency,
                    onNavigateChat = onNavigateChat,
                    onNavigateStressManagement = onNavigateStressManagement,
                    onNavigateSupportingFriends = onNavigateSupportingFriends,
                    onSwitchRole = onLogout
                )
                1 -> CheckInScreen(
                    repository = repository,
                    onDone = { selectedTab = 0 }
                )
                2 -> StreakGardenScreen(
                    repository = repository
                )
                3 -> StudentSupportHubScreen(
                    onNavigateNeedToTalk = onNavigateNeedToTalk,
                    onNavigateFriendConcern = onNavigateFriendConcern,
                    onNavigateEmergency = onNavigateEmergency,
                    onNavigateLearningHub = onNavigateLearningHub,
                    onNavigateChat = onNavigateChat,
                    onNavigateStressManagement = onNavigateStressManagement,
                    onNavigateSupportingFriends = onNavigateSupportingFriends
                )
                4 -> MyProfileScreen(
                    repository = repository,
                    onLogout = onLogout,
                    onNavigateEmergency = onNavigateEmergency,
                    onGoToGarden = { selectedTab = 2 }
                )
            }
        }
    }
}

/**
 * 4. StudentHomeScreen (Matching Image 4)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    repository: SahaayaRepository,
    onGoToCheckIn: () -> Unit,
    onGoToGarden: () -> Unit,
    onGoToSupport: () -> Unit,
    onNavigateFriendConcern: () -> Unit,
    onNavigateNeedToTalk: () -> Unit,
    onNavigateLearningHub: () -> Unit,
    onNavigateEmergency: () -> Unit,
    onNavigateChat: () -> Unit = {},
    onNavigateStressManagement: () -> Unit = {},
    onNavigateSupportingFriends: () -> Unit = {},
    onSwitchRole: () -> Unit = {}
) {
    val student = repository.currentStudent
    var showDailyAdvicePopup by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sahaaya",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(
                        onClick = { repository.toggleTheme() },
                        modifier = Modifier.testTag("student_home_theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (repository.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                            contentDescription = if (repository.isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(
                        onClick = onNavigateChat,
                        modifier = Modifier.testTag("top_bar_chat_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Chat,
                            contentDescription = "Guidance Chat",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    TextButton(
                        onClick = onSwitchRole,
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.testTag("student_home_switch_role_btn")
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
            Spacer(modifier = Modifier.height(18.dp))

            // Greeting
            val firstName = student.name.trim().split(" ").firstOrNull()?.takeIf { it.isNotBlank() } ?: "there"
            Text(
                text = "Good morning, $firstName! 🌱",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "You're doing great. Take a moment for yourself today.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Daily Suggestion & Advice Slot (Requested Feature)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("daily_advice_slot_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Spa,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Daily Suggestion & Advice Slot",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    text = "Mindful Guidance & Peer Support",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        TextButton(
                            onClick = { showDailyAdvicePopup = true },
                            modifier = Modifier.testTag("view_advice_popup_btn")
                        ) {
                            Text("Pop-up", style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "💡 Today's Advice: When exam pressure rises, 4-7-8 breathing grounds your nervous system. Notice a friend being unusually quiet? A simple \"Hey, I'm here if you ever want to vent\" can change their day.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onNavigateStressManagement,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .testTag("btn_slot_stress_management"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Filled.SelfImprovement, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Stress Guides", style = MaterialTheme.typography.labelSmall)
                        }

                        OutlinedButton(
                            onClick = onNavigateSupportingFriends,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .testTag("btn_slot_supporting_friends"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Filled.Group, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Support Friend", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Big CTA card: "Today's Sahaaya"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("today_checkin_cta_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Today's Sahaaya",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (student.checkedInToday) "Completed today! Keep your streak growing." else "Complete your check-in. It only takes a minute.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (student.checkedInToday) Icons.Filled.Check else Icons.Filled.Eco,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onGoToCheckIn,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("home_checkin_action_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = if (student.checkedInToday) "Update Check-In →" else "Check In →",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Streak card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onGoToGarden)
                    .testTag("home_streak_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Eco,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        val streakTitle = if (student.streak > 0) {
                            "${student.streak}-day check-in streak 🌱"
                        } else if (student.checkedInToday) {
                            "1-day check-in streak 🌱"
                        } else {
                            "Start your check-in streak 🌱"
                        }
                        val streakSubtitle = if (student.streak > 0 || student.checkedInToday) {
                            "${repository.studentSeeds} Sahaaya Seeds earned • Tap to view garden"
                        } else {
                            "Take today's 1-minute check-in to sprout your garden"
                        }
                        Text(
                            text = streakTitle,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = streakSubtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Dynamic Today's Reflection & Mindful Moment Card ("Something New")
            val latestCheckIn = repository.checkIns.firstOrNull { it.studentName == student.name || it.studentId == student.id }
            if (student.checkedInToday && latestCheckIn != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Your Reflection Today",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Mood: ${latestCheckIn.mood}${if (latestCheckIn.factors.isNotEmpty()) " • " + latestCheckIn.factors.joinToString(", ") else ""}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (latestCheckIn.energyLevel.isNotBlank() || latestCheckIn.sleepQuality.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Battery: ${latestCheckIn.energyLevel} • Sleep: ${latestCheckIn.sleepQuality}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (latestCheckIn.supportAction.isNotBlank()) {
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Self-care: ${latestCheckIn.supportAction}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        if (latestCheckIn.customNotes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "\"${latestCheckIn.customNotes}\"",
                                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Positive quote card
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "\"A small check-in can open a big conversation. You don't have to handle difficult feelings alone.\"",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Two primary buttons: "Tell us about a friend" / "I need to talk"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateFriendConcern,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("btn_tell_about_friend"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "About a friend",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = onNavigateNeedToTalk,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("btn_i_need_to_talk"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "I need to talk",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onNavigateChat,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("btn_home_chat_assistant"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Spa, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Chat with Sage (AI Companion)",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Section: Your Support Space
            Text(
                text = "Your Support Space",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 2x2 Feature Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureTile(
                    title = "I Need Support",
                    subtitle = "Talk to a counsellor or trusted adult",
                    icon = Icons.Filled.Favorite,
                    iconBgColor = MaterialTheme.colorScheme.primaryContainer,
                    iconColor = MaterialTheme.colorScheme.primary,
                    testTag = "tile_need_support",
                    onClick = onNavigateNeedToTalk,
                    modifier = Modifier.weight(1f)
                )
                FeatureTile(
                    title = "I'm Concerned",
                    subtitle = "About a friend, share securely",
                    icon = Icons.Filled.Warning,
                    iconBgColor = MaterialTheme.colorScheme.secondaryContainer,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    testTag = "tile_friend_concern",
                    onClick = onNavigateFriendConcern,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureTile(
                    title = "Learn & Cope",
                    subtitle = "Resources, audio guides and tips",
                    icon = Icons.Filled.Star,
                    iconBgColor = MaterialTheme.colorScheme.tertiaryContainer,
                    iconColor = MaterialTheme.colorScheme.tertiary,
                    testTag = "tile_learn_cope",
                    onClick = onNavigateLearningHub,
                    modifier = Modifier.weight(1f)
                )
                FeatureTile(
                    title = "My Growth",
                    subtitle = "Garden, milestones and seeds",
                    icon = Icons.Filled.Eco,
                    iconBgColor = MaterialTheme.colorScheme.primaryContainer,
                    iconColor = MaterialTheme.colorScheme.primary,
                    testTag = "tile_my_growth",
                    onClick = onGoToGarden,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        if (showDailyAdvicePopup) {
            AlertDialog(
                onDismissRequest = { showDailyAdvicePopup = false },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Spa,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                },
                title = {
                    Text(
                        text = "Daily Suggestion & Advice Slot",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "💡 Practical Coping Micro-Step:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "When thoughts feel crowded before class or exams, try the 3-3-3 rule: Name 3 things you see, 3 sounds you hear, and move 3 parts of your body (fingers, shoulders, ankles). It re-centers your brain in 30 seconds.",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Divider(modifier = Modifier.padding(vertical = 4.dp))

                        Text(
                            text = "🤝 Supporting a Classmate Today:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "You don't need to fix their problem. Simply saying: \"I noticed you looked tired today; I'm here if you want to walk together\" gives them psychological safety without pressure.",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Divider(modifier = Modifier.padding(vertical = 4.dp))

                        Text(
                            text = "🌱 Growth Affirmation:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Text(
                            text = "\"You are not defined by single test scores or difficult days. You are learning and building resilience one step at a time.\"",
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDailyAdvicePopup = false
                            onNavigateStressManagement()
                        }
                    ) {
                        Text("Open Stress Guides")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDailyAdvicePopup = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}

@Composable
private fun FeatureTile(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBgColor: Color,
    iconColor: Color,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
        }
    }
}

/**
 * 5. CheckInScreen (Matching Image 5 & 6)
 * Step 1 — Mood: 😊 Good | 🙂 Okay | 😐 Not sure | 😟 Not doing well | 😔 I need support
 * Step 2 — "What's affecting your day?" (Select up to 2)
 * Step 3 — "Would you like to talk to someone?" Yes | Maybe | Not right now
 * Step 4 — Confirmation
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckInScreen(
    repository: SahaayaRepository,
    onDone: () -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    val totalSteps = 5

    // Step 1: Mood
    var selectedMood by remember { mutableStateOf("🙂 Okay") }
    val moods = listOf(
        "😊 Good" to "Feeling positive, content, and energized",
        "🙂 Okay" to "Doing alright, balanced and steady",
        "😐 Not sure" to "A bit scattered, neutral, or uncertain",
        "😟 Overwhelmed" to "Stressed, anxious, or facing heavy workload",
        "😔 I need support" to "Feeling down, lonely, or struggling to cope"
    )

    // Step 2: Sleep & Energy
    var selectedSleep by remember { mutableStateOf("😴 Normal / fair") }
    val sleepOptions = listOf(
        "🌙 Deep & restful (7-8+ hrs)" to "Woke up refreshed and ready",
        "😴 Normal / fair" to "Got adequate rest, feeling fine",
        "🥱 Interrupted / restless" to "Woke up multiple times or tossed & turned",
        "⏰ Stayed up late / not enough" to "Lacked sufficient sleep (< 5-6 hrs)"
    )

    var selectedEnergy by remember { mutableStateOf("🔋 Steady & balanced (50-80%)") }
    val energyOptions = listOf(
        "⚡ High energy (80-100%)" to "Ready to tackle activities and challenges",
        "🔋 Steady & balanced (50-80%)" to "Pacing myself well throughout the day",
        "🪫 Running low (20-50%)" to "Feeling drained, slow, or fatigued",
        "🔌 Completely exhausted (<20%)" to "Need quiet rest and minimal demands"
    )

    // Step 3: Life Influences & Custom Thoughts
    val factorOptions = remember {
        mutableStateListOf(
            "Studies & Exams", "Friendships & Peers", "Home & Family", "Extracurriculars & Sports", "Body & Health", "Future Goals", "Prefer not to say"
        )
    }
    val selectedFactors = remember { mutableStateListOf<String>() }
    var customAnswer by remember { mutableStateOf("") }
    var customFactorInput by remember { mutableStateOf("") }
    var showAddCustomField by remember { mutableStateOf(false) }

    // Step 4: What would support you today?
    val selectedSupport = remember { mutableStateListOf<String>() }
    val supportOptions = listOf(
        "🌿 2-minute mindful breathing",
        "🚶 Fresh air or brief physical walk",
        "🎧 Listening to calming music or sounds",
        "🗣️ Chatting with a trusted friend",
        "📖 Taking on just one small task at a time",
        "💬 Quiet chat with school counsellor"
    )

    // Step 5: Counsellor Connection & Mindful Breathing
    var wantsToTalk by remember { mutableStateOf("Not right now, just logging my day") }
    val talkOptions = listOf(
        "Yes — please reach out gently" to "A school counsellor will check in with you discreetly.",
        "Maybe later this week" to "You can request a private session anytime when you're ready.",
        "Not right now, just logging my day" to "Thank you for checking in with yourself today."
    )

    // Interactive 1-Minute Box Breathing State
    var isBreathingActive by remember { mutableStateOf(false) }
    var breathPhaseText by remember { mutableStateOf("Tap to start 1-minute calming breath") }
    var breathCountdown by remember { mutableIntStateOf(4) }
    var breathTargetScale by remember { mutableStateOf(1f) }

    val animatedBreathScale by animateFloatAsState(
        targetValue = breathTargetScale,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 3800),
        label = "breathScale"
    )

    LaunchedEffect(isBreathingActive) {
        if (isBreathingActive) {
            val cycle = listOf(
                Triple("Inhale deeply through nose...", 4, 1.35f),
                Triple("Hold your breath gently...", 4, 1.35f),
                Triple("Exhale slowly through mouth...", 4, 0.85f),
                Triple("Rest and reset...", 4, 1.0f)
            )
            while (isBreathingActive) {
                for ((text, duration, target) in cycle) {
                    if (!isBreathingActive) break
                    breathPhaseText = text
                    breathTargetScale = target
                    for (sec in duration downTo 1) {
                        breathCountdown = sec
                        delay(1000)
                    }
                }
            }
        } else {
            breathPhaseText = "Tap to start 1-minute calming breath"
            breathCountdown = 4
            breathTargetScale = 1.0f
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (step <= totalSteps) "Daily Check-In ($step/$totalSteps)" else "Check-In Complete!",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    if (step in 2..totalSteps) {
                        IconButton(onClick = { step-- }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (step <= totalSteps) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    // Modern styled progress bar
                    LinearProgressIndicator(
                        progress = { step / totalSteps.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    when (step) {
                        // -------------------------------------------------------------
                        // STEP 1: MOOD
                        // -------------------------------------------------------------
                        1 -> {
                            Text(
                                text = "How are you feeling today?",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Tune into your mind and emotions right now. There are no right or wrong answers.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            moods.forEach { (moodTitle, moodDesc) ->
                                val isSelected = selectedMood == moodTitle
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .clickable { selectedMood = moodTitle }
                                        .testTag("mood_${moodTitle.take(2)}"),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = moodTitle,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                                                ),
                                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = moodDesc,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(CircleShape)
                                                    .background(MaterialTheme.colorScheme.primary),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.Check,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.onPrimary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // -------------------------------------------------------------
                        // STEP 2: SLEEP & ENERGY LEVELS (NEW QUESTIONS)
                        // -------------------------------------------------------------
                        2 -> {
                            Text(
                                text = "Sleep & Energy Battery",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Physical recharge directly shapes how we process stress and concentration.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            Text(
                                text = "How did you sleep last night?",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            sleepOptions.forEach { (sleepTitle, sleepDesc) ->
                                val isSelected = selectedSleep == sleepTitle
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { selectedSleep = sleepTitle },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surface
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = { selectedSleep = sleepTitle }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = sleepTitle,
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = sleepDesc,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "What is your energy meter today?",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            energyOptions.forEach { (energyTitle, energyDesc) ->
                                val isSelected = selectedEnergy == energyTitle
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { selectedEnergy = energyTitle },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surface
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = { selectedEnergy = energyTitle }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = energyTitle,
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = energyDesc,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // -------------------------------------------------------------
                        // STEP 3: WHAT'S AFFECTING YOUR DAY & PERSONAL REFLECTION
                        // -------------------------------------------------------------
                        3 -> {
                            Text(
                                text = "What's affecting your day?",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Select or write factors impacting your head space (select up to 3).",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                factorOptions.forEach { factor ->
                                    val isSelected = selectedFactors.contains(factor)
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            if (isSelected) {
                                                selectedFactors.remove(factor)
                                            } else {
                                                if (selectedFactors.size < 3) {
                                                    selectedFactors.add(factor)
                                                }
                                            }
                                        },
                                        label = { Text(factor) },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    )
                                }

                                FilterChip(
                                    selected = showAddCustomField,
                                    onClick = { showAddCustomField = !showAddCustomField },
                                    label = { Text("+ Custom Tag") },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )
                            }

                            if (showAddCustomField) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedTextField(
                                        value = customFactorInput,
                                        onValueChange = { customFactorInput = it },
                                        placeholder = { Text("Add custom factor (e.g. Sports, Art, Sleep)") },
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.weight(1f),
                                        singleLine = true
                                    )
                                    Button(
                                        onClick = {
                                            val trimmed = customFactorInput.trim()
                                            if (trimmed.isNotBlank() && !factorOptions.contains(trimmed)) {
                                                factorOptions.add(0, trimmed)
                                                selectedFactors.add(trimmed)
                                                customFactorInput = ""
                                                showAddCustomField = false
                                            }
                                        },
                                        enabled = customFactorInput.isNotBlank(),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("Add")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Your Customized Answer & Notes",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Write anything on your mind. This is your personal safe space to vent, reflect, or celebrate.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            val starters = listOf("Today I'm thinking about...", "Grateful for...", "Feeling a bit pressured by...", "Excited about...")
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                starters.forEach { starter ->
                                    SuggestionChip(
                                        onClick = {
                                            customAnswer = if (customAnswer.isBlank()) starter else "$customAnswer $starter"
                                        },
                                        label = { Text(starter) },
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = customAnswer,
                                onValueChange = { customAnswer = it },
                                placeholder = { Text("Type your customized answer or notes here...") },
                                minLines = 3,
                                maxLines = 6,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkin_custom_answer_input")
                            )
                        }

                        // -------------------------------------------------------------
                        // STEP 4: SUPPORTIVE CARE: WHAT WOULD HELP YOU TODAY? (NEW)
                        // -------------------------------------------------------------
                        4 -> {
                            Text(
                                text = "What would uplift or help you today?",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Small intentional actions can significantly ease pressure and restore clarity.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            supportOptions.forEach { action ->
                                val isSelected = selectedSupport.contains(action)
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                        .clickable {
                                            if (isSelected) selectedSupport.remove(action)
                                            else selectedSupport.add(action)
                                        },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surface
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = action,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Checkbox(
                                            checked = isSelected,
                                            onCheckedChange = {
                                                if (it) selectedSupport.add(action) else selectedSupport.remove(action)
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // -------------------------------------------------------------
                        // STEP 5: COUNSELLOR CONNECTION & MINDFUL BOX BREATHING RESET
                        // -------------------------------------------------------------
                        5 -> {
                            Text(
                                text = "Counsellor & Mindful Reset",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Choose if you'd like a private check-in, or take a quick calming breath before continuing.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            Text(
                                text = "Would you like to connect with your school counsellor?",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            talkOptions.forEach { (option, description) ->
                                val isSelected = wantsToTalk == option
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                        .clickable { wantsToTalk = option }
                                        .testTag("talk_option_${option.take(3)}"),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text(
                                            text = option,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                            ),
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(22.dp))

                            // Interactive 1-Minute Box Breathing Reset Tool
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(Icons.Filled.Eco, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "1-Minute Calming Breath Reset",
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Pulsing Animated Circle
                                    Box(
                                        modifier = Modifier
                                            .size((110 * animatedBreathScale).dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = if (isBreathingActive) "$breathCountdown" else "🌱",
                                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = breathPhaseText,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    OutlinedButton(
                                        onClick = { isBreathingActive = !isBreathingActive },
                                        shape = RoundedCornerShape(12.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(if (isBreathingActive) "Pause Exercise" else "Start 1-Min Breath")
                                    }
                                }
                            }
                        }
                    }
                }

                // Next / Submit Button
                Column(modifier = Modifier.padding(vertical = 24.dp)) {
                    Button(
                        onClick = {
                            if (step < totalSteps) {
                                step++
                            } else {
                                isBreathingActive = false
                                repository.submitStudentCheckIn(
                                    mood = selectedMood,
                                    factors = selectedFactors.toList(),
                                    wantsToTalk = wantsToTalk,
                                    customNotes = customAnswer.trim(),
                                    energyLevel = selectedEnergy,
                                    sleepQuality = selectedSleep,
                                    supportAction = selectedSupport.joinToString(", "),
                                    academicStress = if (selectedFactors.contains("Studies & Exams")) "High Academic Strain / Exam Pressure" else "Moderate / Manageable",
                                    peerRelations = if (selectedFactors.contains("Friendships & Peers")) "Peer Conflict / Social Tension" else "Normal / Connected",
                                    physicalSymptoms = if (selectedFactors.contains("Body & Health")) "Physical Fatigue / Somatic Tension" else "None"
                                )
                                step = 6
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("checkin_next_submit_btn"),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = if (step < totalSteps) "Next Step →" else "Complete & Save Check-In",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            } else {
                // -------------------------------------------------------------
                // STEP 6: CONFIRMATION & SEEDS CELEBRATION
                // -------------------------------------------------------------
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SproutIllustration(modifier = Modifier.size(150.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Check-in complete!",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "+20 Sahaaya Seeds added to your garden! 🌱",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Rich Summary Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "Today's Wellness Reflection",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Mood: $selectedMood",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Sleep: $selectedSleep • Energy: $selectedEnergy",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (selectedFactors.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Influences: ${selectedFactors.joinToString(", ")}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            if (selectedSupport.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Helpful today: ${selectedSupport.joinToString(", ")}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            if (customAnswer.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "\"$customAnswer\"",
                                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "\"Taking care of your mental health is a sign of courage and strength.\"",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(14.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onDone,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("checkin_back_home_btn"),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Back to Home", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

/**
 * 6. StreakGardenScreen (Matching Image 10, 11, 12)
 */
@Composable
fun StreakGardenScreen(
    repository: SahaayaRepository
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Garden", "Streak", "Missions")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)) }
                )
            }
        }

        when (selectedTab) {
            0 -> GardenTab(repository)
            1 -> StreakTab(repository)
            2 -> MissionsTab(repository)
        }
    }
}

@Composable
private fun GardenTab(repository: SahaayaRepository) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sahaaya Garden",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Your well-being grows with every positive step.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Visual Garden Plant Art
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SproutIllustration(modifier = Modifier.size(200.dp))

                Spacer(modifier = Modifier.height(16.dp))

                val streak = repository.currentStudent.streak
                val (stageTitle, stageSubtext) = when {
                    streak == 0 -> "Stage 1: Seedling Planted 🌱" to "Complete your check-in today to sprout your garden!"
                    streak in 1..2 -> "Stage 2: Growing Sprout 🌿" to "Keep going! $streak/3 days to leaf budding."
                    streak in 3..6 -> "Stage 3: Budding Plant 🌸" to "Wonderful consistency! $streak/7 days to full garden bloom."
                    else -> "Stage 4: Blooming Garden 🌺" to "Master gardener! Thriving $streak-day streak."
                }

                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = stageTitle,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stageSubtext,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Seeds Badge
        Card(
            modifier = Modifier.fillMaxWidth(),
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
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Star, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Your Seeds",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Next reward at 200 seeds",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Text(
                    text = "${repository.studentSeeds}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun StreakTab(repository: SahaayaRepository) {
    val student = repository.currentStudent
    val dayLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Today")
    val currentStreak = student.streak
    val checkedToday = student.checkedInToday

    val days = remember(currentStreak, checkedToday) {
        val list = mutableListOf<Pair<String, Boolean>>()
        for (i in 0..6) {
            val isChecked = if (i == 6) {
                checkedToday
            } else {
                val daysAgo = 6 - i
                val priorDays = if (checkedToday) (currentStreak - 1).coerceAtLeast(0) else currentStreak
                daysAgo <= priorDays && currentStreak > 0
            }
            list.add(dayLabels[i] to isChecked)
        }
        list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Check-in Streak",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Every check-in counts. Missing a day is okay.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 7-day display
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "${student.streak} Days Active Streak 🔥",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    days.forEach { (day, checked) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (checked) Icons.Filled.Check else Icons.Filled.Eco,
                                    contentDescription = null,
                                    tint = if (checked) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(day, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Milestones",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        val milestones = listOf(
            Triple("First Step", "Completed your first check-in", student.streak > 0 || student.checkedInToday),
            Triple("Finding Support", "Explored school support pathways", repository.supportRequests.isNotEmpty() || student.streak > 0),
            Triple("Supportive Friend", "Learned how to listen to peers", repository.friendReports.isNotEmpty()),
            Triple("Consistent Participant", "Maintained a 7-day streak", student.streak >= 7)
        )

        milestones.forEach { (title, desc, isAchieved) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isAchieved) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isAchieved) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )
                        Text(
                            if (isAchieved) desc else "$desc (In progress)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MissionsTab(repository: SahaayaRepository) {
    var completed1 by remember { mutableStateOf(false) }
    var completed2 by remember { mutableStateOf(false) }
    var completed3 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Weekly Missions",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Complete missions, earn seeds, and practice self-care.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        MissionCard(
            title = "Learn one way to support a friend",
            reward = "+20 seeds",
            isCompleted = completed1,
            onComplete = {
                if (!completed1) {
                    completed1 = true
                    repository.addSeeds(20)
                }
            }
        )

        MissionCard(
            title = "Complete a 2-minute relaxation activity",
            reward = "+20 seeds",
            isCompleted = completed2,
            onComplete = {
                if (!completed2) {
                    completed2 = true
                    repository.addSeeds(20)
                }
            }
        )

        MissionCard(
            title = "Explore one school support resource",
            reward = "+20 seeds",
            isCompleted = completed3,
            onComplete = {
                if (!completed3) {
                    completed3 = true
                    repository.addSeeds(20)
                }
            }
        )
    }
}

@Composable
private fun MissionCard(
    title: String,
    reward: String,
    isCompleted: Boolean,
    onComplete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
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
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(4.dp))
                Text(reward, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiary)
            }

            Button(
                onClick = onComplete,
                enabled = !isCompleted,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCompleted) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(if (isCompleted) "Completed ✓" else "Start")
            }
        }
    }
}

/**
 * 7. FriendConcernScreen
 * Text: "Sometimes we notice that a friend may need support. You can let a trusted adult know."
 * Fields: Friend's name, Friend's phone, Class, Concern description, Optional notes
 * Notice: "Your report will be handled by the designated school counsellor according to school safeguarding procedures."
 * Submit button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendConcernScreen(
    repository: SahaayaRepository,
    onBack: () -> Unit
) {
    var friendName by remember { mutableStateOf("") }
    var friendPhone by remember { mutableStateOf("") }
    var friendClass by remember { mutableStateOf("Class 10 - Section B") }
    var concernDescription by remember { mutableStateOf("") }
    var optionalNotes by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf("Medium") }
    var isAnonymous by remember { mutableStateOf(false) }

    var submitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Concern About a Friend", style = MaterialTheme.typography.titleLarge) },
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

            if (!submitted) {
                Text(
                    text = "Worried about someone?",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Sometimes we notice that a friend may need support. You can let a trusted adult know.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = friendName,
                    onValueChange = { friendName = it },
                    label = { Text("Friend's Name") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("friend_name_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = friendPhone,
                    onValueChange = { friendPhone = it },
                    label = { Text("Friend's Phone (Optional)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = friendClass,
                    onValueChange = { friendClass = it },
                    label = { Text("Class & Section") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = concernDescription,
                    onValueChange = { concernDescription = it },
                    label = { Text("What have you noticed? (Concern Description)") },
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("friend_concern_desc")
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = optionalNotes,
                    onValueChange = { optionalNotes = it },
                    label = { Text("Optional Notes (best time to check in, etc.)") },
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text("Urgency Level", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Low", "Medium", "High").forEach { level ->
                        FilterChip(
                            selected = urgency == level,
                            onClick = { urgency = level },
                            label = { Text(level) },
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isAnonymous, onCheckedChange = { isAnonymous = it })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share anonymously", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Your report will be handled by the designated school counsellor according to school safeguarding procedures.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (friendName.isNotBlank() && concernDescription.isNotBlank()) {
                            repository.submitFriendReport(
                                concernedName = friendName,
                                concernedPhone = friendPhone,
                                classSection = friendClass,
                                concern = concernDescription,
                                notes = optionalNotes,
                                urgency = urgency
                            )
                            submitted = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("submit_friend_concern_btn"),
                    enabled = friendName.isNotBlank() && concernDescription.isNotBlank(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Submit Concern", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Thank you for speaking up.",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your concern for $friendName has been securely delivered to the school counsellor. You made a difference today.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = onBack,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Return to Dashboard")
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

/**
 * 8. INeedToTalkScreen
 * "You deserve to be heard."
 * Button: "Request a Conversation"
 * Confirmation: "Your request has been sent to your school counsellor."
 * NO diagnosis, NO automated advice
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun INeedToTalkScreen(
    repository: SahaayaRepository,
    onBack: () -> Unit,
    onOpenChat: () -> Unit = {}
) {
    var selectedChannel by remember { mutableStateOf("Talk to my counsellor") }
    var notes by remember { mutableStateOf("") }
    var requested by remember { mutableStateOf(false) }

    val options = listOf(
        "Talk to my counsellor" to "Private one-on-one session in Room 204",
        "Talk to a trusted adult" to "Teacher, parent, or student coordinator",
        "Explore support resources" to "Self-guided coping toolkits and reading",
        "I'm not sure" to "Help me figure out next steps"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("I Need to Talk", style = MaterialTheme.typography.titleLarge) },
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
            Spacer(modifier = Modifier.height(16.dp))

            if (!requested) {
                Text(
                    text = "You deserve to be heard.",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Reaching out is an important and brave step. Choose how you'd prefer to connect.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                options.forEach { (title, subtitle) ->
                    val isSelected = selectedChannel == title
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { selectedChannel = title }
                            .testTag("talk_channel_${title.take(5)}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        ),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onOpenChat)
                        .testTag("need_to_talk_chat_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Chat, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Want to chat right now?", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text("Interactive guidance chat or leave a message for the team", style = MaterialTheme.typography.bodySmall)
                        }
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Anything you'd like to share in advance (Optional)") },
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Sahaaya provides school pastoral support. It is not an automated medical diagnostic tool.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        repository.submitSupportRequest(selectedChannel, notes)
                        requested = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("request_conversation_btn"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Request a Conversation", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Request Sent",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your request has been sent to your school counsellor (${repository.counsellorName}). They will reach out to you discreetly.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = onBack,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Return Home")
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

/**
 * 9. Support Hub Screen inside bottom nav
 */
@Composable
fun StudentSupportHubScreen(
    onNavigateNeedToTalk: () -> Unit,
    onNavigateFriendConcern: () -> Unit,
    onNavigateEmergency: () -> Unit,
    onNavigateLearningHub: () -> Unit,
    onNavigateChat: () -> Unit = {},
    onNavigateStressManagement: () -> Unit = {},
    onNavigateSupportingFriends: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Support Hub",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Every student has access to guidance and a caring ear.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Chat card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onNavigateChat)
                .testTag("support_hub_chat_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Chat, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondary)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Sage AI Companion & Counsellor", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("Safe, non-judgmental space with Sage or private counsellor chat", style = MaterialTheme.typography.bodySmall)
                }

                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onNavigateNeedToTalk)
                .testTag("support_hub_i_need_talk"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("I Need to Talk", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("Request a confidential session with your counsellor", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onNavigateStressManagement)
                .testTag("support_hub_stress_toolkit"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.SelfImprovement, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Stress Management Toolkit", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("Breathing drills, exam anxiety resets & grounding routines", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onNavigateSupportingFriends)
                .testTag("support_hub_supporting_friends_guide"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Group, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Supporting Friends in Need", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("Active listening scripts, warning signs & ethical boundaries", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onNavigateFriendConcern)
                .testTag("support_hub_friend_concern"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondary)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Concern About a Friend", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("Safeguarding and peer care notice", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onNavigateLearningHub),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Learn & Cope Hub", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("Articles on stress, emotional regulation, and listening", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

/**
 * 10. LearningHubScreen (Matching Image 9)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningHubScreen(onBack: () -> Unit) {
    var selectedCategory by remember { mutableStateOf("For Me") }
    var activeArticle by remember { mutableStateOf<Pair<String, String>?>(null) }

    val categories = listOf("For Me", "For Friends", "Getting Help")

    val articles = when (selectedCategory) {
        "For Me" -> listOf(
            "Recognising Stress" to "Stress shows up physically (headaches, tight chest), emotionally (irritability, anxiety), and behaviorally (procrastination, sleep disruption). Identifying your baseline early allows you to pause and recalibrate.",
            "Understanding Emotions" to "Feelings are signals, not commands. You don't have to suppress anger or sadness—acknowledging them with curiosity softens their grip.",
            "Healthy Routines" to "A consistent sleep schedule and 10 minutes of screen-free downtime before bed drastically improves emotional equilibrium.",
            "When to Seek Help" to "If low mood or anxiety interferes with your schoolwork, friendships, or appetite for more than two consecutive weeks, speaking with the school counsellor provides immediate relief."
        )
        "For Friends" -> listOf(
            "How to Listen" to "Listen to understand, not to debate or immediately solve. A simple 'That sounds really hard, I'm glad you told me' does wonders.",
            "What to Say" to "'I'm here for you,' 'Would you like company or advice?', and 'You aren't a burden to me.'",
            "What Not to Do" to "Avoid saying 'It could be worse' or 'Just stay positive.' Avoid promising to keep secrets when their safety is at risk.",
            "When to Involve an Adult" to "If a friend mentions self-harm, severe neglect, or feeling unsafe at home or school, immediately notify a trusted counsellor or safeguarding teacher."
        )
        else -> listOf(
            "School Counsellor" to "Located in Room 204. Free, confidential, and available every school day between 09:00 AM and 03:30 PM.",
            "Trusted Adults" to "Homeroom teachers, sports coaches, and school nurses are all trained to guide students to safety.",
            "School Support System" to "Pastoral care teams collaborate strictly within privacy guidelines to ensure academic pressure is accommodated during personal hardships.",
            "Community Resources" to "National Helplines (112, Childline 1098, Tele-MANAS 14416) are available 24 hours a day, 7 days a week."
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sahaaya Learning Hub", style = MaterialTheme.typography.titleLarge) },
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
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            articles.forEach { (title, detail) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { activeArticle = title to detail },
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
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        }

                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    if (activeArticle != null) {
        val (title, text) = activeArticle!!
        AlertDialog(
            onDismissRequest = { activeArticle = null },
            title = { Text(title, style = MaterialTheme.typography.titleLarge) },
            text = { Text(text, style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                TextButton(onClick = { activeArticle = null }) {
                    Text("Close")
                }
            }
        )
    }
}

/**
 * 13. MyProfileScreen (Matching Image 13)
 */
@Composable
fun MyProfileScreen(
    repository: SahaayaRepository,
    onLogout: () -> Unit,
    onNavigateEmergency: () -> Unit,
    onGoToGarden: () -> Unit
) {
    val student = repository.currentStudent
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var editName by remember(student.name) { mutableStateOf(student.name) }
    var editClass by remember(student.classSection) { mutableStateOf(student.classSection) }
    var editSchool by remember(student.schoolName) { mutableStateOf(student.schoolName) }
    var savedBanner by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(46.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = student.name.ifBlank { "Student" },
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "${student.classSection.ifBlank { "Class 10 - Section A" }} • ${student.schoolName.ifBlank { repository.schoolInfo.name }}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Inline Profile Customization Card (No login required)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Student Profile Details",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = editName,
                    onValueChange = { editName = it; savedBanner = false },
                    label = { Text("Your Name") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = editClass,
                    onValueChange = { editClass = it; savedBanner = false },
                    label = { Text("Class & Section") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = editSchool,
                    onValueChange = { editSchool = it; savedBanner = false },
                    label = { Text("School Name") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                if (savedBanner) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Profile saved!",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        repository.updateStudentProfile(editName, editClass, editSchool)
                        savedBanner = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Save Profile")
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Options
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onGoToGarden),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Eco, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("My Growth & Garden", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    Text("${student.streak}-day streak • ${repository.studentSeeds} seeds", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Appearance / Theme Mode Settings
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("appearance_settings_card"),
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
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Palette,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Appearance",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Choose your preferred viewing experience",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                ThemeMode.entries.forEach { mode ->
                    val isSelected = repository.themeMode == mode
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { repository.themeMode = mode }
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                            .testTag("theme_radio_${mode.name.lowercase()}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { repository.themeMode = mode },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = mode.title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                ),
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = mode.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showPrivacyDialog = true },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(14.dp))
                Text("Privacy & Safety Notice", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("student_logout_btn"),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Switch Role (Student / Counsellor)", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy & Safety Notice") },
            text = {
                Text(
                    "Your check-in responses are confidential and accessible only by authorised school counsellors for safeguarding and pastoral support. Sahaaya does not share your data publicly or with advertisers."
                )
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("Understood")
                }
            }
        )
    }
}

/**
 * 14. EmergencyScreen (always accessible)
 * Red banner: "If you are in immediate danger, contact a trusted adult, school authority, or local emergency service (112) NOW."
 * Contact list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(
    repository: SahaayaRepository? = null,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val schoolName = repository?.schoolInfo?.name ?: "Kendriya Vidyalaya No.1 Vasco"
    val counsellorName = repository?.counsellorName ?: "Ms. Sharma"
    val schoolPhone = repository?.schoolInfo?.phone?.takeIf { it.isNotBlank() } ?: "+91 98765 00001"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Contacts", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    titleContentColor = MaterialTheme.colorScheme.onError,
                    navigationIconContentColor = MaterialTheme.colorScheme.onError
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

            // Red Banner
            Surface(
                color = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Emergency,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "If you are in immediate danger, contact a trusted adult, school authority, or local emergency service (112) NOW.",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Emergency Pathways",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            EmergencyContactItem(
                name = "National Emergency Services",
                phone = "112",
                subtitle = "Police, Ambulance, Fire (Toll-Free, 24/7)",
                onClick = { launchEmergencyCall(context, "112") }
            )

            EmergencyContactItem(
                name = "Childline India",
                phone = "1098",
                subtitle = "24-hour free emergency service for children in need of care & protection",
                onClick = { launchEmergencyCall(context, "1098") }
            )

            EmergencyContactItem(
                name = "Tele-MANAS Mental Health Helpline",
                phone = "14416",
                subtitle = "Government 24/7 tele-mental health helpline",
                onClick = { launchEmergencyCall(context, "14416") }
            )

            EmergencyContactItem(
                name = "School Counsellor ($counsellorName)",
                phone = schoolPhone,
                subtitle = "$schoolName Guidance Office (08:30 AM - 04:00 PM)",
                onClick = { launchEmergencyCall(context, schoolPhone) }
            )

            EmergencyContactItem(
                name = "School Safeguarding Officer",
                phone = schoolPhone,
                subtitle = "Designated school safeguarding lead at $schoolName",
                onClick = { launchEmergencyCall(context, schoolPhone) }
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun EmergencyContactItem(
    name: String,
    phone: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick),
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
            Column(modifier = Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(2.dp))
                Text(phone, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(2.dp))
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Phone, contentDescription = "Call", tint = MaterialTheme.colorScheme.onErrorContainer)
            }
        }
    }
}

/**
 * 15. Sahaaya Guidance Chat Screen
 * Interactive guidance chat between student and Sahaaya Support / Counsellor
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    repository: SahaayaRepository,
    onBack: () -> Unit,
    onConnectWithCounsellor: () -> Unit = {}
) {
    var chatMode by remember { mutableIntStateOf(0) } // 0: Sage AI Companion, 1: Connect with a Counsellor
    var inputText by remember { mutableStateOf("") }
    val assistantListState = rememberLazyListState()
    val counsellorListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val schoolName = repository.currentStudent.schoolName.ifBlank { repository.schoolInfo.name }
    val counsellorName = repository.counsellorName

    val studentId = repository.currentStudent.id
    val counsellorMessages = remember(repository.directMessages.size, studentId) {
        repository.directMessages.filter { it.studentId == studentId || it.studentId.isBlank() || it.studentName == repository.currentStudent.name }
    }

    val suggestedPrompts = listOf(
        "I'm so stressed about my exams. I feel like I'm going to fail everything.",
        "I've been feeling really overwhelmed and lonely lately",
        "Can you suggest a gentle breathing or grounding exercise?",
        "How can I connect with a human counsellor?"
    )

    LaunchedEffect(repository.chatMessages.size, repository.isSageTyping) {
        if (chatMode == 0 && repository.chatMessages.isNotEmpty()) {
            assistantListState.animateScrollToItem(repository.chatMessages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (chatMode == 0) "Sage • AI Companion" else "Connect with $counsellorName",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (chatMode == 0) "$schoolName • Compassionate Space" else "$schoolName • Private & Secure",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (chatMode == 0 && repository.chatMessages.size > 1) {
                        TextButton(
                            onClick = { repository.clearSageChatHistory() },
                            modifier = Modifier.testTag("sage_reset_chat_btn")
                        ) {
                            Text("New Chat", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    IconButton(onClick = { chatMode = if (chatMode == 0) 1 else 0 }) {
                        Icon(
                            imageVector = if (chatMode == 0) Icons.Filled.SupportAgent else Icons.Filled.Spa,
                            contentDescription = "Switch Chat Mode"
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
                    // Quick suggestion chips on Sage mode
                    if (chatMode == 0) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            suggestedPrompts.forEach { prompt ->
                                SuggestionChip(
                                    onClick = {
                                        if (!repository.isSageTyping) {
                                            coroutineScope.launch {
                                                repository.sendSageMessageAsync(prompt)
                                            }
                                        }
                                    },
                                    label = { Text(prompt, style = MaterialTheme.typography.labelSmall) }
                                )
                            }
                        }
                    } else {
                        // Counsellor thread info chip
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Private & secure human counsellor thread with $counsellorName.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(onClick = onConnectWithCounsellor) {
                                Text("Book Session", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = {
                                Text(
                                    if (chatMode == 0) "Share how you're feeling with Sage..." else "Type private message to $counsellorName...",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("chat_input_field"),
                            shape = RoundedCornerShape(24.dp),
                            maxLines = 4
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    val textToSend = inputText.trim()
                                    inputText = ""
                                    if (chatMode == 0) {
                                        coroutineScope.launch {
                                            repository.sendSageMessageAsync(textToSend)
                                        }
                                    } else {
                                        repository.sendStudentDirectMessage(textToSend)
                                        coroutineScope.launch {
                                            if (counsellorMessages.isNotEmpty()) {
                                                counsellorListState.animateScrollToItem(counsellorMessages.size)
                                            }
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (inputText.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                .testTag("chat_send_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send",
                                tint = if (inputText.isNotBlank()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Mode Tab Selector
            TabRow(
                selectedTabIndex = chatMode,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = chatMode == 0,
                    onClick = { chatMode = 0 },
                    text = { Text("Sage (AI Companion)") },
                    icon = { Icon(Icons.Filled.Spa, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = chatMode == 1,
                    onClick = { chatMode = 1 },
                    text = { Text("Connect with a Counsellor") },
                    icon = { Icon(Icons.Filled.SupportAgent, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
            }

            // Confidentiality & Companion Role Banner
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (chatMode == 0) {
                            "Sage is an empathetic AI companion, not a licensed therapist. Conversations with human counsellors are private & secure."
                        } else {
                            "Private and secure human counsellor channel with $counsellorName at $schoolName."
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            if (chatMode == 0) {
                // Sage AI Companion Message List
                LazyColumn(
                    state = assistantListState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    items(repository.chatMessages) { chatItem ->
                        val isUser = chatItem.isFromUser
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                            ) {
                                if (!isUser) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (chatItem.isCrisisAlert) MaterialTheme.colorScheme.errorContainer
                                                else MaterialTheme.colorScheme.secondaryContainer
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (chatItem.isCrisisAlert) Icons.Filled.Favorite else Icons.Filled.Spa,
                                            contentDescription = null,
                                            tint = if (chatItem.isCrisisAlert) MaterialTheme.colorScheme.onErrorContainer
                                            else MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                }

                                Surface(
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isUser) 16.dp else 4.dp,
                                        bottomEnd = if (isUser) 4.dp else 16.dp
                                    ),
                                    color = when {
                                        isUser -> MaterialTheme.colorScheme.primary
                                        chatItem.isCrisisAlert -> MaterialTheme.colorScheme.errorContainer
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    },
                                    tonalElevation = 1.dp,
                                    modifier = Modifier.widthIn(max = 295.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        if (!isUser) {
                                            Text(
                                                text = if (chatItem.isCrisisAlert) "Sage • Safety & Care Support" else "${chatItem.senderName} • AI Companion",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = if (chatItem.isCrisisAlert) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                        }
                                        Text(
                                            text = chatItem.message,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = when {
                                                isUser -> MaterialTheme.colorScheme.onPrimary
                                                chatItem.isCrisisAlert -> MaterialTheme.colorScheme.onErrorContainer
                                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                                            }
                                        )

                                        if (!isUser && (chatItem.suggestCounsellorConnection || chatItem.isCrisisAlert)) {
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Button(
                                                    onClick = { chatMode = 1 },
                                                    shape = RoundedCornerShape(10.dp),
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = if (chatItem.isCrisisAlert) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                                                    ),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .testTag("sage_connect_counsellor_btn")
                                                ) {
                                                    Icon(Icons.Filled.SupportAgent, contentDescription = null, modifier = Modifier.size(16.dp))
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text("Connect with a Counsellor", style = MaterialTheme.typography.labelSmall)
                                                }
                                            }
                                            if (chatItem.isCrisisAlert) {
                                                Spacer(modifier = Modifier.height(6.dp))
                                                OutlinedButton(
                                                    onClick = { launchEmergencyCall(context, "14416") },
                                                    shape = RoundedCornerShape(10.dp),
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Icon(Icons.Filled.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text("Call Crisis Hotline (14416)", style = MaterialTheme.typography.labelSmall)
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = chatItem.timestamp,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = (if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant).copy(alpha = 0.7f),
                                            modifier = Modifier.align(Alignment.End)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (repository.isSageTyping) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.secondaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Spa,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = "Sage is listening and reflecting...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            } else {
                // Counsellor Direct Thread List
                LazyColumn(
                    state = counsellorListState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Direct Line to $counsellorName",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Send questions or request discreet check-ins. Your counsellor receives these on their portal and will respond here.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    if (counsellorMessages.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Filled.SupportAgent,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "No direct messages yet",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Start by saying hello or asking a question below.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    } else {
                        items(counsellorMessages) { msg ->
                            val isStudentMsg = msg.isFromStudent
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isStudentMsg) Arrangement.End else Arrangement.Start
                            ) {
                                if (!isStudentMsg) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.secondary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.SupportAgent,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSecondary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                }

                                Surface(
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isStudentMsg) 16.dp else 4.dp,
                                        bottomEnd = if (isStudentMsg) 4.dp else 16.dp
                                    ),
                                    color = if (isStudentMsg) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                                    tonalElevation = 1.dp,
                                    modifier = Modifier.widthIn(max = 280.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        if (!isStudentMsg) {
                                            Text(
                                                text = msg.senderName,
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                        }
                                        Text(
                                            text = msg.message,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (isStudentMsg) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = msg.timestamp,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = (if (isStudentMsg) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer).copy(alpha = 0.7f),
                                            modifier = Modifier.align(Alignment.End)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
