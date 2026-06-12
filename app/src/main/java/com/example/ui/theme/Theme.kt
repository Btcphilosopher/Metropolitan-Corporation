package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = Brass,
    secondary = SteelGrey,
    tertiary = DeepGreen,
    background = MidnightBlue,
    surface = MidnightBlue,
    onPrimary = MidnightBlue,
    onSecondary = Cream,
    onTertiary = Cream,
    onBackground = Cream,
    onSurface = Cream,
    surfaceVariant = Color(0xFF182B42),
    onSurfaceVariant = Brass
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MidnightBlue,
    secondary = SteelGrey,
    tertiary = Brass,
    background = Cream,
    surface = PaperPanel,
    onPrimary = Cream,
    onSecondary = MidnightBlue,
    onTertiary = MidnightBlue,
    onBackground = MidnightBlue,
    onSurface = MidnightBlue,
    surfaceVariant = SolidWhite,
    onSurfaceVariant = MidnightBlue
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false, // Choose the gorgeous Victorian Book Cream style by default
  dynamicColor: Boolean = false, // Disable dynamic colors to preserve branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
