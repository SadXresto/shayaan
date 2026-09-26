package com.example.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Supported theme appearance modes:
 * - System: Follows device system dark mode setting
 * - Light: Clean, high-contrast forest green & light off-white background
 * - Dark: Eye-safe deep twilight green with dark surface
 * - Eye Comfort: Soothing warm amber/sepia palette with reduced blue light for evening reading
 */
enum class ThemeMode(val title: String, val subtitle: String) {
    SYSTEM("System Default", "Follows your device system settings"),
    LIGHT("Light", "Clean daytime theme with crisp contrast"),
    DARK("Dark", "Dimmed low-glare dark palette"),
    EYE_COMFORT("Eye Comfort", "Soothing warm amber tones with reduced blue light")
}

// Light Theme Colors
val SahaayaPrimaryLight = Color(0xFF1E5E4E) // deep forest green
val SahaayaOnPrimaryLight = Color(0xFFFFFFFF)
val SahaayaPrimaryContainerLight = Color(0xFFC8E6D9)
val SahaayaOnPrimaryContainerLight = Color(0xFF002019)

val SahaayaSecondaryLight = Color(0xFF4A7C6F) // muted sage
val SahaayaOnSecondaryLight = Color(0xFFFFFFFF)
val SahaayaSecondaryContainerLight = Color(0xFFD4E7E1)
val SahaayaOnSecondaryContainerLight = Color(0xFF05201A)

val SahaayaTertiaryLight = Color(0xFFE8B44A) // soft amber - milestones only
val SahaayaOnTertiaryLight = Color(0xFFFFFFFF)
val SahaayaTertiaryContainerLight = Color(0xFFFFE0A3)
val SahaayaOnTertiaryContainerLight = Color(0xFF2B1C00)

val SahaayaBackgroundLight = Color(0xFFF7F8F7) // off-white
val SahaayaOnBackgroundLight = Color(0xFF1A1C1B)
val SahaayaSurfaceLight = Color(0xFFFFFFFF)
val SahaayaOnSurfaceLight = Color(0xFF1A1C1B)
val SahaayaSurfaceVariantLight = Color(0xFFEEF1F0)
val SahaayaOnSurfaceVariantLight = Color(0xFF5F6368)
val SahaayaOutlineLight = Color(0xFFE3E5E4)
val SahaayaErrorLight = Color(0xFFB3261E)
val SahaayaOnErrorLight = Color(0xFFFFFFFF)
val SahaayaErrorContainerLight = Color(0xFFF9DEDC)
val SahaayaOnErrorContainerLight = Color(0xFF410E0B)

// Dark Theme Colors
val SahaayaPrimaryDark = Color(0xFF7FC8B0)
val SahaayaOnPrimaryDark = Color(0xFF00382C)
val SahaayaPrimaryContainerDark = Color(0xFF1E5E4E)
val SahaayaOnPrimaryContainerDark = Color(0xFFC8E6D9)

val SahaayaSecondaryDark = Color(0xFF8BB8AA)
val SahaayaOnSecondaryDark = Color(0xFF1B362E)
val SahaayaSecondaryContainerDark = Color(0xFF335047)
val SahaayaOnSecondaryContainerDark = Color(0xFFD4E7E1)

val SahaayaTertiaryDark = Color(0xFFE8B44A)
val SahaayaOnTertiaryDark = Color(0xFF3F2E00)
val SahaayaTertiaryContainerDark = Color(0xFF5B4300)
val SahaayaOnTertiaryContainerDark = Color(0xFFFFE0A3)

val SahaayaBackgroundDark = Color(0xFF121212)
val SahaayaOnBackgroundDark = Color(0xFFE3E5E4)
val SahaayaSurfaceDark = Color(0xFF1E1E1E)
val SahaayaOnSurfaceDark = Color(0xFFE3E5E4)
val SahaayaSurfaceVariantDark = Color(0xFF2C322F)
val SahaayaOnSurfaceVariantDark = Color(0xFFC0C5C2)
val SahaayaOutlineDark = Color(0xFF424945)
val SahaayaErrorDark = Color(0xFFF2B8B5)
val SahaayaOnErrorDark = Color(0xFF601410)
val SahaayaErrorContainerDark = Color(0xFF8C1D18)
val SahaayaOnErrorContainerDark = Color(0xFFF9DEDC)

// Eye Comfort Theme Colors (Warm sepia/amber tinted soothing palette)
val SahaayaPrimaryEyeComfort = Color(0xFF2E6B56)
val SahaayaOnPrimaryEyeComfort = Color(0xFFFFFFFF)
val SahaayaPrimaryContainerEyeComfort = Color(0xFFE2EBD8)
val SahaayaOnPrimaryContainerEyeComfort = Color(0xFF1A3326)

val SahaayaSecondaryEyeComfort = Color(0xFF6B7F60)
val SahaayaOnSecondaryEyeComfort = Color(0xFFFFFFFF)
val SahaayaSecondaryContainerEyeComfort = Color(0xFFECE5CE)
val SahaayaOnSecondaryContainerEyeComfort = Color(0xFF272111)

val SahaayaTertiaryEyeComfort = Color(0xFFC48C28)
val SahaayaOnTertiaryEyeComfort = Color(0xFFFFFFFF)
val SahaayaTertiaryContainerEyeComfort = Color(0xFFFDE8BA)
val SahaayaOnTertiaryContainerEyeComfort = Color(0xFF3E2600)

val SahaayaBackgroundEyeComfort = Color(0xFFFAF6EE) // warm ivory / soft amber tint
val SahaayaOnBackgroundEyeComfort = Color(0xFF2E2B25)
val SahaayaSurfaceEyeComfort = Color(0xFFFFFDF8) // soothing warm parchment
val SahaayaOnSurfaceEyeComfort = Color(0xFF2E2B25)
val SahaayaSurfaceVariantEyeComfort = Color(0xFFEFE8DA)
val SahaayaOnSurfaceVariantEyeComfort = Color(0xFF6A6559)
val SahaayaOutlineEyeComfort = Color(0xFFDFD7C6)
val SahaayaErrorEyeComfort = Color(0xFFB33A2B)
val SahaayaOnErrorEyeComfort = Color(0xFFFFFFFF)
val SahaayaErrorContainerEyeComfort = Color(0xFFF8DDD8)
val SahaayaOnErrorContainerEyeComfort = Color(0xFF45130D)

