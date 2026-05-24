package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ProfessionalBlue,
    onPrimary = OnProfessionalBlue,
    primaryContainer = ProfessionalLightBlue,
    onPrimaryContainer = OnProfessionalLightBlue,
    secondary = ProfessionalLightBlue,
    onSecondary = OnProfessionalLightBlue,
    background = PolishBackground,
    surface = PolishSurface,
    onBackground = PolishOnSurface,
    onSurface = PolishOnSurface,
    outline = PolishBorder,
    onSurfaceVariant = PolishTextSecondary,
    tertiary = BadgeContainer,
    onTertiary = BadgeText
)

private val LightColorScheme = lightColorScheme(
    primary = ProfessionalBlue,
    onPrimary = OnProfessionalBlue,
    primaryContainer = ProfessionalLightBlue,
    onPrimaryContainer = OnProfessionalLightBlue,
    secondary = ProfessionalLightBlue,
    onSecondary = OnProfessionalLightBlue,
    background = PolishBackground,
    surface = PolishSurface,
    onBackground = PolishOnSurface,
    onSurface = PolishOnSurface,
    outline = PolishBorder,
    onSurfaceVariant = PolishTextSecondary,
    tertiary = BadgeContainer,
    onTertiary = BadgeText
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We override dynamicColor default to false so that the custom Professional Polish theme is honored perfectly
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
