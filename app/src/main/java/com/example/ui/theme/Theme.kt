package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = SahaayaPrimaryLight,
    onPrimary = SahaayaOnPrimaryLight,
    primaryContainer = SahaayaPrimaryContainerLight,
    onPrimaryContainer = SahaayaOnPrimaryContainerLight,
    secondary = SahaayaSecondaryLight,
    onSecondary = SahaayaOnSecondaryLight,
    secondaryContainer = SahaayaSecondaryContainerLight,
    onSecondaryContainer = SahaayaOnSecondaryContainerLight,
    tertiary = SahaayaTertiaryLight,
    onTertiary = SahaayaOnTertiaryLight,
    tertiaryContainer = SahaayaTertiaryContainerLight,
    onTertiaryContainer = SahaayaOnTertiaryContainerLight,
    background = SahaayaBackgroundLight,
    onBackground = SahaayaOnBackgroundLight,
    surface = SahaayaSurfaceLight,
    onSurface = SahaayaOnSurfaceLight,
    surfaceVariant = SahaayaSurfaceVariantLight,
    onSurfaceVariant = SahaayaOnSurfaceVariantLight,
    outline = SahaayaOutlineLight,
    outlineVariant = SahaayaOutlineLight,
    error = SahaayaErrorLight,
    onError = SahaayaOnErrorLight,
    errorContainer = SahaayaErrorContainerLight,
    onErrorContainer = SahaayaOnErrorContainerLight
)

private val DarkColorScheme = darkColorScheme(
    primary = SahaayaPrimaryDark,
    onPrimary = SahaayaOnPrimaryDark,
    primaryContainer = SahaayaPrimaryContainerDark,
    onPrimaryContainer = SahaayaOnPrimaryContainerDark,
    secondary = SahaayaSecondaryDark,
    onSecondary = SahaayaOnSecondaryDark,
    secondaryContainer = SahaayaSecondaryContainerDark,
    onSecondaryContainer = SahaayaOnSecondaryContainerDark,
    tertiary = SahaayaTertiaryDark,
    onTertiary = SahaayaOnTertiaryDark,
    tertiaryContainer = SahaayaTertiaryContainerDark,
    onTertiaryContainer = SahaayaOnTertiaryContainerDark,
    background = SahaayaBackgroundDark,
    onBackground = SahaayaOnBackgroundDark,
    surface = SahaayaSurfaceDark,
    onSurface = SahaayaOnSurfaceDark,
    surfaceVariant = SahaayaSurfaceVariantDark,
    onSurfaceVariant = SahaayaOnSurfaceVariantDark,
    outline = SahaayaOutlineDark,
    outlineVariant = SahaayaOutlineDark,
    error = SahaayaErrorDark,
    onError = SahaayaOnErrorDark,
    errorContainer = SahaayaErrorContainerDark,
    onErrorContainer = SahaayaOnErrorContainerDark
)

private val EyeComfortColorScheme = lightColorScheme(
    primary = SahaayaPrimaryEyeComfort,
    onPrimary = SahaayaOnPrimaryEyeComfort,
    primaryContainer = SahaayaPrimaryContainerEyeComfort,
    onPrimaryContainer = SahaayaOnPrimaryContainerEyeComfort,
    secondary = SahaayaSecondaryEyeComfort,
    onSecondary = SahaayaOnSecondaryEyeComfort,
    secondaryContainer = SahaayaSecondaryContainerEyeComfort,
    onSecondaryContainer = SahaayaOnSecondaryContainerEyeComfort,
    tertiary = SahaayaTertiaryEyeComfort,
    onTertiary = SahaayaOnTertiaryEyeComfort,
    tertiaryContainer = SahaayaTertiaryContainerEyeComfort,
    onTertiaryContainer = SahaayaOnTertiaryContainerEyeComfort,
    background = SahaayaBackgroundEyeComfort,
    onBackground = SahaayaOnBackgroundEyeComfort,
    surface = SahaayaSurfaceEyeComfort,
    onSurface = SahaayaOnSurfaceEyeComfort,
    surfaceVariant = SahaayaSurfaceVariantEyeComfort,
    onSurfaceVariant = SahaayaOnSurfaceVariantEyeComfort,
    outline = SahaayaOutlineEyeComfort,
    outlineVariant = SahaayaOutlineEyeComfort,
    error = SahaayaErrorEyeComfort,
    onError = SahaayaOnErrorEyeComfort,
    errorContainer = SahaayaErrorContainerEyeComfort,
    onErrorContainer = SahaayaOnErrorContainerEyeComfort
)

val SahaayaShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),    // Buttons & TextFields: 12dp
    medium = RoundedCornerShape(16.dp),   // Cards: 16dp
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun SahaayaTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        ThemeMode.SYSTEM -> if (darkTheme) DarkColorScheme else LightColorScheme
        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.DARK -> DarkColorScheme
        ThemeMode.EYE_COMFORT -> EyeComfortColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SahaayaTypography,
        shapes = SahaayaShapes,
        content = content
    )
}

// Alias for compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    SahaayaTheme(themeMode = if (darkTheme) ThemeMode.DARK else ThemeMode.LIGHT, content = content)
}
