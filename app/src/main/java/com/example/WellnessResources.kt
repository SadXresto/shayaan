package com.example

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ══════════════════════════════════════════════════════════════════
// 1. AI SITUATION ANALYSIS SYNTHESIZER FOR TEACHERS & COUNSELLORS
// System prompt reasoning engine modeled after clinical triage
// ══════════════════════════════════════════════════════════════════

object SituationAnalysisEngine {

    fun generateDetailedSituationReport(
        student: Student,
        mood: String,
        academicStress: String,
        peerRelations: String,
        sleepQuality: String,
        energyLevel: String,
        physicalSymptoms: String,
        factors: List<String>,
        wantsToTalk: String,
        customNotes: String,
        timestamp: String = "Today"
    ): String {
        val isHighUrgency = mood.contains("support", ignoreCase = true) ||
                academicStress.contains("Severe", ignoreCase = true) ||
                peerRelations.contains("Bullying", ignoreCase = true) ||
                wantsToTalk.contains("Yes", ignoreCase = true)

        val severityLevel = when {
            isHighUrgency -> "ELEVATED CONCERN / ACTION RECOMMENDED"
            mood.contains("Overwhelmed", ignoreCase = true) || academicStress.contains("High", ignoreCase = true) || sleepQuality.contains("not enough", ignoreCase = true) -> "MODERATE ATTENTION REQUIRED"
            else -> "MILD / BASELINE WELLNESS STABLE"
        }

        val primaryDriver = when {
            academicStress.contains("High", ignoreCase = true) || academicStress.contains("Severe", ignoreCase = true) ->
                "Academic workload and performance apprehension"
            peerRelations.contains("Bullying", ignoreCase = true) || peerRelations.contains("Left Out", ignoreCase = true) ->
                "Interpersonal peer friction and social vulnerability"
            sleepQuality.contains("Stayed up late", ignoreCase = true) || physicalSymptoms.contains("Headache", ignoreCase = true) ->
                "Neuro-somatic fatigue and physical depletion"
            else -> "General developmental stressors"
        }

        val recommendedAction = when {
            isHighUrgency -> "Schedule a discreet 1-on-1 private check-in with Counsellor within 24 hours. Inform class mentor to monitor participation without calling public attention."
            severityLevel.contains("MODERATE") -> "Conduct an informal check-in at the end of class. Offer extension or extra clarification on recent homework if requested."
            else -> "Acknowledge check-in positively. Maintain regular monitoring."
        }

        val notesSummary = if (customNotes.isNotBlank()) "\"$customNotes\"" else "Student did not submit additional written text."

        return """
[CLINICAL & PEDAGOGICAL SITUATION SYNTHESIS]
Target: School Counsellor & Class Educator
Student: ${student.name.ifBlank { "Student" }} (${student.classSection})
Timestamp: $timestamp

1. EXECUTIVE RISK TRIAGE
• Severity Classification: $severityLevel
• Primary Stress Driver: $primaryDriver
• Request for Direct Talk: $wantsToTalk

2. MULTI-FACTOR VULNERABILITY MATRIX
• Emotional Tone: $mood
• Academic Pressure Level: $academicStress
• Social & Peer Dynamic: $peerRelations
• Sleep & Energy Baseline: $sleepQuality | $energyLevel
• Somatic Complaints: $physicalSymptoms
• Key Environmental Factors: ${if (factors.isNotEmpty()) factors.joinToString(", ") else "None highlighted"}

3. STUDENT SELF-NARRATIVE
$notesSummary

4. ACTION PROTOCOL FOR TEACHERS & COUNSELLORS
• Immediate Step: $recommendedAction
• Classroom Communication Script:
  "I noticed your thoughtful participation lately, ${student.name.ifBlank { "there" }}. Remember that you can take short breaks, and our school counselling office is always open for a relaxed chat."
• Essential Dos & Don'ts:
  ✓ DO validate their effort before pointing out academic targets.
  ✓ DO provide quiet reassurance during exams.
  ✗ DON'T call attention to their distress in front of peer groups.
  ✗ DON'T dismiss fears as 'just another exam'.
""".trimIndent()
    }
}

// ══════════════════════════════════════════════════════════════════
// 2. FEATURE I: STRESS MANAGEMENT RESOURCES & ARTICLES
// ══════════════════════════════════════════════════════════════════

data class StressArticle(
    val id: String,
    val title: String,
    val category: String,
    val readTime: String,
    val icon: ImageVector,
    val summary: String,
    val fullContent: String,
    val actionableStep: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StressManagementScreen(
    onBack: () -> Unit,
    onOpenChat: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var viewingArticle by remember { mutableStateOf<StressArticle?>(null) }
    var activeBreathingProtocol by remember { mutableStateOf("Physiological Sigh") }
    var isBreathing by remember { mutableStateOf(false) }
    var breathPhase by remember { mutableStateOf("Inhale through nose") }
    var breathCounter by remember { mutableIntStateOf(4) }
    var circleScale by remember { mutableStateOf(1f) }

    val animatedScale by animateFloatAsState(
        targetValue = circleScale,
        animationSpec = tween(durationMillis = 3500),
        label = "circleScale"
    )

    LaunchedEffect(isBreathing, activeBreathingProtocol) {
        if (isBreathing) {
            when (activeBreathingProtocol) {
                "Physiological Sigh" -> {
                    while (isBreathing) {
                        breathPhase = "1. Deep inhale through nose..."
                        circleScale = 1.3f
                        delay(2500)
                        if (!isBreathing) break
                        breathPhase = "2. Quick extra sip of air..."
                        circleScale = 1.45f
                        delay(1200)
                        if (!isBreathing) break
                        breathPhase = "3. Long, slow exhale through mouth..."
                        circleScale = 0.85f
                        delay(5000)
                    }
                }
                "Box Breathing (4-4-4-4)" -> {
                    while (isBreathing) {
                        breathPhase = "Inhale (4s)..."
                        circleScale = 1.4f
                        delay(4000)
                        if (!isBreathing) break
                        breathPhase = "Hold gently (4s)..."
                        circleScale = 1.4f
                        delay(4000)
                        if (!isBreathing) break
                        breathPhase = "Exhale slowly (4s)..."
                        circleScale = 0.85f
                        delay(4000)
                        if (!isBreathing) break
                        breathPhase = "Rest & Reset (4s)..."
                        circleScale = 1.0f
                        delay(4000)
                    }
                }
                else -> { // 4-7-8 Deep Calm
                    while (isBreathing) {
                        breathPhase = "Inhale calmly (4s)..."
                        circleScale = 1.35f
                        delay(4000)
                        if (!isBreathing) break
                        breathPhase = "Hold breath (7s)..."
                        circleScale = 1.35f
                        delay(7000)
                        if (!isBreathing) break
                        breathPhase = "Slow, releasing whoosh (8s)..."
                        circleScale = 0.8f
                        delay(8000)
                    }
                }
            }
        } else {
            breathPhase = "Tap Start to Begin Exercise"
            circleScale = 1.0f
        }
    }

    val articles = remember {
        listOf(
            StressArticle(
                id = "art_1",
                title = "The 5-4-3-2-1 Sensory Grounding Technique",
                category = "Panic & Anxiety",
                readTime = "3 min read",
                icon = Icons.Filled.Visibility,
                summary = "Quick neuro-grounding protocol when racing thoughts or panic strikes before an exam.",
                fullContent = """
When acute anxiety or panic begins, your sympathetic nervous system hijacks attention into catastrophic future scenarios. 

The 5-4-3-2-1 technique anchors your cognitive executive function back into your immediate physical surroundings:

• 5 things you can SEE: Look around the room right now. Spot a pattern on the wall, a pencil reflection, sunlight on a desk, or texture in your notebook.
• 4 things you can TOUCH: Feel the fabric of your sleeves, the firmness of your chair under your back, the coolness of a desk surface, or your feet grounded on the floor.
• 3 things you can HEAR: Listen carefully. Is a ceiling fan humming? Are birds chirping outside? Can you hear distant footsteps or your own steady breath?
• 2 things you can SMELL: Notice the scent of pencil cedar, fresh paper, eraser, or morning air.
• 1 thing you can TASTE: Sip a drop of water or notice the clean taste on your tongue.

Scientific Mechanism: Engaging all five sensory cortical channels forces the prefrontal cortex to reclaim bandwidth from the amygdala, dropping your heart rate within 90 seconds.
""".trimIndent(),
                actionableStep = "Try finding 3 distinct textures around your desk right now."
            ),
            StressArticle(
                id = "art_2",
                title = "Tackling Academic Burnout & The Perfectionism Trap",
                category = "Exams & Study",
                readTime = "4 min read",
                icon = Icons.Filled.MenuBook,
                summary = "Why working 10 continuous hours lowers retention and how structured recovery boosts grades.",
                fullContent = """
Perfectionism often disguises itself as high ambition, but in reality, it is fear of judgment in disguise. Students frequently trap themselves in 'All-or-Nothing' thinking: 'If I can't study this entire textbook tonight, I am already failing.'

1. The Law of Diminishing Cognitive Returns:
After 50 minutes of intense focus, memory consolidation declines steeply. Cramming for 5 straight hours leaves less than 30% retention in long-term memory.

2. The Pomodoro & Recovery Loop:
Study for 25 minutes with zero digital distractions. Then step away completely for 5 minutes—walk to the window, stretch your shoulders, drink water.

3. Shift from Outcome-Based to Process-Based Goals:
Instead of 'I must get 100/100 on tomorrow's test', reframe your target to: 'I will complete 3 sample problems with full focus.' You control your preparation process, not the exact exam question set.
""".trimIndent(),
                actionableStep = "Set a 25-minute timer today. When it rings, step away from screens for 5 minutes."
            ),
            StressArticle(
                id = "art_3",
                title = "Cognitive Reframing: Stopping The Negative Spiral",
                category = "Mindset & CBT",
                readTime = "3 min read",
                icon = Icons.Filled.Psychology,
                summary = "How to catch catastrophic thoughts ('I will ruin my life') and replace them with factual reality.",
                fullContent = """
Cognitive Distortions are mental blind spots that make everyday challenges feel life-threatening. Here are the three most common student traps:

1. Catastrophizing:
• Thought: 'If I fail this midterm, I will never get into university and my future is ruined.'
• Reality Check: 'A single exam is one data point. It provides feedback on what concepts I need to review, not a verdict on my whole existence.'

2. Mind Reading:
• Thought: 'The teacher looked at me when saying grades were low. Everyone thinks I'm not smart.'
• Reality Check: 'Teachers look around the classroom naturally. I cannot read minds, and other people are thinking about their own concerns.'

3. Emotional Reasoning:
• Thought: 'I feel completely overwhelmed, so it must mean I cannot handle this.'
• Reality Check: 'Feelings are physical sensations in response to hormones, not objective facts. I can feel nervous and still perform competently.'
""".trimIndent(),
                actionableStep = "Write down one recurring worry on paper, then write one balanced fact directly beside it."
            ),
            StressArticle(
                id = "art_4",
                title = "Sleep Hygiene: The Brain's Natural Emotional Detox",
                category = "Physical Wellness",
                readTime = "4 min read",
                icon = Icons.Filled.SelfImprovement,
                summary = "Why staying up late studying backfires and how the glymphatic system cleans emotional waste.",
                fullContent = """
During deep Slow-Wave and REM sleep, the brain activates the glymphatic system, flushing out metabolic waste and consolidating newly learned concepts into long-term synaptic connections.

When students cut sleep down to 4 or 5 hours to cram:
• The emotional amygdala becomes 60% more reactive to small frustrations.
• Working memory retrieval slows down during the actual exam morning.
• Information learned during sleep deprivation is prone to retrieval blackouts.

Golden Sleep Rules for High School Students:
1. Stop screen usage 45 minutes before sleep; blue light blocks melatonin synthesis.
2. Keep your study desk separate from your pillow so your brain associates the mattress exclusively with rest.
3. If you can't fall asleep within 20 minutes, get out of bed in dim light and read a physical book until sleepy.
""".trimIndent(),
                actionableStep = "Charge your phone across the room tonight instead of under your pillow."
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Stress & Well-Being Hub", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                        Text("Interactive tools & psychological guides", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (viewingArticle != null) {
                            viewingArticle = null
                        } else {
                            onBack()
                        }
                    }) {
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
        if (viewingArticle != null) {
            val art = viewingArticle!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${art.category} • ${art.readTime}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = art.title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Micro-Action: ${art.actionableStep}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = art.fullContent,
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 26.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onOpenChat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.SupportAgent, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Talk to Counsellor About This")
                }

                Spacer(modifier = Modifier.height(36.dp))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Interactive Breathing Studio Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Nervous System Reset",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Science-backed breathing protocols",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(Icons.Filled.Spa, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Protocol selector chips
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Physiological Sigh", "Box Breathing (4-4-4-4)", "4-7-8 Deep Calm").forEach { protocol ->
                                FilterChip(
                                    selected = activeBreathingProtocol == protocol,
                                    onClick = {
                                        activeBreathingProtocol = protocol
                                        isBreathing = false
                                    },
                                    label = { Text(protocol, style = MaterialTheme.typography.labelSmall) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Pulsing Breath Visualizer
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .scale(animatedScale)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Air,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = breathPhase,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { isBreathing = !isBreathing },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isBreathing) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(if (isBreathing) "Stop Breathing Session" else "Start Exercise")
                        }
                    }
                }

                // Articles Section
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Clinical Articles & Strategies",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${articles.size} resources",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                articles.forEach { article ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                            .clickable { viewingArticle = article },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(article.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "${article.category} • ${article.readTime}",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = article.title,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = article.summary,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2
                                )
                            }

                            Icon(
                                Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════
// 3. FEATURE II: GUIDANCE FOR SUPPORTING FRIENDS
// ══════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportingFriendsScreen(
    onBack: () -> Unit,
    onOpenFriendReport: () -> Unit,
    onOpenDirectChat: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Warning Signs", "Conversation Scripts", "Dos & Don'ts", "Healthy Boundaries")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Supporting a Friend", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                        Text("Peer support guidance & compassionate action", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
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
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                edgePadding = 16.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)) }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                when (selectedTab) {
                    0 -> { // Warning Signs
                        Text(
                            text = "Warning Signs to Look Out For",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "When friends are silently struggling, changes in daily behavior speak louder than words:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        listOf(
                            Triple("Sudden Social Withdrawal", "Sitting alone at lunch, avoiding eye contact, leaving group chats, or cancelling usual hangout plans.", Icons.Filled.People),
                            Triple("Academic Disengagement", "A previously punctual friend suddenly skipping homework, arriving late, or showing sudden indifference to grades.", Icons.Filled.MenuBook),
                            Triple("Severe Mood Swings or Irritability", "Snapping at minor issues, sudden tearfulness, or appearing completely numb and unengaged.", Icons.Filled.Warning),
                            Triple("Neglecting Physical Care", "Looking constantly exhausted, drastic appetite changes, or wearing long sleeves in very hot weather to hide self-harm.", Icons.Filled.Favorite),
                            Triple("Hopeless Language", "Making remarks like 'Nobody would care if I wasn't here' or giving away cherished possessions.", Icons.Filled.Psychology)
                        ).forEach { (title, desc, icon) ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                    }
                                    Spacer(modifier = Modifier.width(14.dp))
                                    Column {
                                        Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }

                    1 -> { // Conversation Scripts
                        Text(
                            text = "How to Start the Conversation",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "You don't need to have all the answers. Just opening a safe, non-judgmental space makes a huge difference:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        listOf(
                            "The Gentle Observation" to "\"Hey, I noticed you seemed a bit quiet during break today. I just wanted to check in—how are you really doing?\"",
                            "The No-Pressure Invitation" to "\"You don't have to explain anything if you don't want to, but I'm right here if you want to vent or just sit together.\"",
                            "Normalizing Heavy Feelings" to "\"School and exams have been overwhelming for everyone lately. It's completely understandable to feel drained. How have you been holding up?\"",
                            "Reassuring They Aren't a Burden" to "\"You are never a burden to me. When you share things with me, it doesn't push me away—I care about you.\""
                        ).forEach { (title, script) ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(title, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(script, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium), color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    }

                    2 -> { // Dos & Don'ts
                        Text(
                            text = "Compassionate Dos and Don'ts",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("✅ DOs to Practice", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(8.dp))
                                listOf(
                                    "DO listen without interrupting or jumping straight to solutions.",
                                    "DO validate their feelings: 'That sounds exhausting and tough.'",
                                    "DO invite them to low-pressure activities (walking, listening to music).",
                                    "DO encourage them to speak with our school counsellor confidentially."
                                ).forEach {
                                    Text("• $it", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("❌ DON'Ts to Avoid", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.error)
                                Spacer(modifier = Modifier.height(8.dp))
                                listOf(
                                    "DON'T say 'Just cheer up' or 'Look on the bright side' (Toxic Positivity).",
                                    "DON'T compare their pain: 'Others have it way worse than you.'",
                                    "DON'T promise to keep secrets if someone's physical safety is in danger.",
                                    "DON'T take sudden angry outbursts personally—it's often masked distress."
                                ).forEach {
                                    Text("• $it", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                                }
                            }
                        }
                    }

                    3 -> { // Boundaries
                        Text(
                            text = "Healthy Peer Boundaries",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Remember: You are a friend, not a licensed therapist. Supporting a friend should never come at the cost of your own mental health or safety.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("When to Immediately Alert an Adult / Counsellor:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.error)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("• Expressions of self-harm, suicidal ideation, or giving away belongings.\n• Severe abuse, violence, or dangerous external threats.\n• Complete disorientation or substance abuse.\n\nIn these situations, telling a counsellor is NOT breaking trust—it is saving a life.", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Callout Banner
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Worried about a classmate right now?",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Submit a discreet, confidential alert so the school counsellor can check in gently.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(
                                onClick = onOpenFriendReport,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Tell Us About a Friend")
                            }
                            OutlinedButton(onClick = onOpenDirectChat) {
                                Text("Ask Counsellor")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════
// 4. DAILY ADVICE SLOT FOR START SCREEN / DASHBOARD
// ══════════════════════════════════════════════════════════════════

data class DailyAdviceTip(
    val id: String,
    val headline: String,
    val adviceText: String,
    val category: String,
    val actionType: AdviceActionType
)

enum class AdviceActionType {
    STRESS_RESOURCES,
    FRIEND_GUIDANCE,
    DIRECT_CHAT
}

object DailyAdviceProvider {
    private val tips = listOf(
        DailyAdviceTip(
            id = "tip_1",
            headline = "The Double Inhale Reset",
            adviceText = "When exam stress spikes, take two quick inhales through your nose, then one long slow mouth exhale. This immediately drops acute heart rate.",
            category = "Physiological Calming",
            actionType = AdviceActionType.STRESS_RESOURCES
        ),
        DailyAdviceTip(
            id = "tip_2",
            headline = "Notice a Quiet Friend?",
            adviceText = "If a friend seems detached today, you don't need a grand gesture. A simple 'Thinking of you, no need to reply' lifts immense pressure.",
            category = "Peer Support",
            actionType = AdviceActionType.FRIEND_GUIDANCE
        ),
        DailyAdviceTip(
            id = "tip_3",
            headline = "The 50-Minute Focus Boundary",
            adviceText = "Cramming for 4 hours without pausing causes memory saturation. Study for 25-50 minutes, then take a real 5-minute break with zero phone screens.",
            category = "Academic Wellness",
            actionType = AdviceActionType.STRESS_RESOURCES
        ),
        DailyAdviceTip(
            id = "tip_4",
            headline = "Uncertain About Something?",
            adviceText = "You don't have to carry confusing thoughts by yourself. Your school counsellor is available for confidential guidance anytime.",
            category = "School Support",
            actionType = AdviceActionType.DIRECT_CHAT
        ),
        DailyAdviceTip(
            id = "tip_5",
            headline = "Thought Reframing Trick",
            adviceText = "Catch yourself saying 'I can't do this'? Add the word 'YET': 'I don't fully understand this physics chapter YET.' That small pivot rewires motivation.",
            category = "Mindset",
            actionType = AdviceActionType.STRESS_RESOURCES
        )
    )

    fun getTips(): List<DailyAdviceTip> = tips
}

@Composable
fun DailyAdviceSlotCard(
    onNavigateStress: () -> Unit,
    onNavigateFriends: () -> Unit,
    onNavigateChat: () -> Unit
) {
    val allTips = remember { DailyAdviceProvider.getTips() }
    var currentTipIndex by remember { mutableIntStateOf(0) }
    val currentTip = allTips[currentTipIndex % allTips.size]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("daily_advice_slot_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Daily Wellness & Advice Slot",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = currentTip.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = { currentTipIndex = (currentTipIndex + 1) % allTips.size },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lightbulb,
                        contentDescription = "Next Advice",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = currentTip.headline,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = currentTip.adviceText,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Quick 1-Tap Action Chips for the two major requested features
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateStress,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Filled.Spa, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Stress Articles & Breathing", style = MaterialTheme.typography.labelSmall)
                }

                OutlinedButton(
                    onClick = onNavigateFriends,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Filled.People, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Supporting Friends Guide", style = MaterialTheme.typography.labelSmall)
                }

                Button(
                    onClick = onNavigateChat,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Filled.SupportAgent, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Direct Chat", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
