package com.appharbor.otter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Glassmorphism Dark Color Scheme
 * 
 * A premium dark theme with glassmorphic aesthetics.
 * Features deep backgrounds, subtle surfaces, and vibrant accents.
 */
private val GlassDarkColorScheme = darkColorScheme(
    primary = GlassAccentPrimary,
    onPrimary = Color.White,
    primaryContainer = GlassSurfaceHeavy,
    onPrimaryContainer = GlassTextPrimary,
    
    secondary = GlassAccentSecondary,
    onSecondary = Color.White,
    secondaryContainer = GlassSurfaceMedium,
    onSecondaryContainer = GlassTextPrimary,
    
    tertiary = GlassAccentTertiary,
    onTertiary = Color.White,
    tertiaryContainer = GlassSurfaceLight,
    onTertiaryContainer = GlassTextPrimary,
    
    background = GlassBackgroundStart,
    onBackground = GlassTextPrimary,
    
    surface = GlassSurfaceLight,
    onSurface = GlassTextPrimary,
    surfaceVariant = GlassSurfaceMedium,
    onSurfaceVariant = GlassTextSecondary,
    
    error = GlassError,
    onError = Color.White,
    errorContainer = GlassError.copy(alpha = 0.2f),
    onErrorContainer = GlassError,
    
    outline = GlassBorderMedium,
    outlineVariant = GlassBorderLight,
)

/**
 * Glassmorphism Light Color Scheme
 * 
 * A light variant for future light mode support.
 * Features soft backgrounds with dark text.
 */
private val GlassLightColorScheme = lightColorScheme(
    primary = GlassAccentPrimary,
    onPrimary = Color.White,
    primaryContainer = LightGlassSurfaceHeavy,
    onPrimaryContainer = LightGlassTextPrimary,
    
    secondary = GlassAccentSecondary,
    onSecondary = Color.White,
    secondaryContainer = LightGlassSurfaceMedium,
    onSecondaryContainer = LightGlassTextPrimary,
    
    tertiary = GlassAccentTertiary,
    onTertiary = Color.White,
    tertiaryContainer = LightGlassSurfaceLight,
    onTertiaryContainer = LightGlassTextPrimary,
    
    background = LightGlassBackgroundStart,
    onBackground = LightGlassTextPrimary,
    
    surface = LightGlassSurfaceLight,
    onSurface = LightGlassTextPrimary,
    surfaceVariant = LightGlassSurfaceMedium,
    onSurfaceVariant = LightGlassTextSecondary,
    
    error = GlassError,
    onError = Color.White,
    errorContainer = GlassError.copy(alpha = 0.1f),
    onErrorContainer = GlassError,
    
    outline = Color.Black.copy(alpha = 0.2f),
    outlineVariant = Color.Black.copy(alpha = 0.1f),
)

/**
 * Otter Theme
 * 
 * The main theme composable for the Otter app.
 * Provides premium glassmorphism aesthetic with liquid glass design.
 * 
 * @param darkTheme Whether to use dark theme (default: system setting)
 * @param dynamicColor Whether to use dynamic colors on Android 12+ (disabled by default for glassmorphism)
 * @param content The content to theme
 */
@Composable
fun OtterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color disabled by default to maintain glassmorphism aesthetic
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> GlassDarkColorScheme
        else -> GlassLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}