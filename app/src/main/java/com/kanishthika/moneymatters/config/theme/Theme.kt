package com.kanishthika.moneymatters.config.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_onBackgroundSecondary,
    inverseSurface = md_theme_light_inverseSurface,
    tertiary = tertiary,
    tertiaryContainer = tertiary_container,
    onSurfaceVariant = onBackground_variant,
    onTertiaryContainer = onTertiaryContainer
)


@Composable
fun MoneyMattersTheme(
  content: @Composable () -> Unit
) {
  val colors = LightColors

  MaterialTheme(
    colorScheme = colors,
    content = content
  )
}