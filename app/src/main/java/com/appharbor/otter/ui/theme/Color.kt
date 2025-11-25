package com.appharbor.otter.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

// ============================================================================
// GLASSMORPHISM THEME COLORS
// A premium, unified color system for the Otter app
// ============================================================================

// ----------------------------------------------------------------------------
// DARK THEME - Deep, rich backgrounds with premium feel
// ----------------------------------------------------------------------------

// Background gradient colors
val GlassBackgroundStart = Color(0xFF0A0E27)      // Deep navy blue
val GlassBackgroundMid = Color(0xFF1A1F3A)        // Rich midnight blue
val GlassBackgroundEnd = Color(0xFF2D1B3D)        // Deep purple

// Accent colors for highlights and interactive elements
val GlassAccentPrimary = Color(0xFF6C63FF)        // Vibrant purple
val GlassAccentSecondary = Color(0xFF5B9FFF)      // Bright blue
val GlassAccentTertiary = Color(0xFF00D4FF)       // Cyan blue

// Surface colors with transparency for glass effect
val GlassSurfaceLight = Color.White.copy(alpha = 0.1f)
val GlassSurfaceMedium = Color.White.copy(alpha = 0.15f)
val GlassSurfaceHeavy = Color.White.copy(alpha = 0.25f)

// Border colors for subtle definition
val GlassBorderLight = Color.White.copy(alpha = 0.1f)
val GlassBorderMedium = Color.White.copy(alpha = 0.2f)
val GlassBorderHeavy = Color.White.copy(alpha = 0.3f)

// Text colors
val GlassTextPrimary = Color.White
val GlassTextSecondary = Color.White.copy(alpha = 0.7f)
val GlassTextTertiary = Color.White.copy(alpha = 0.5f)
val GlassTextDisabled = Color.White.copy(alpha = 0.3f)

// Icon colors
val GlassIconPrimary = Color.White
val GlassIconSecondary = Color.White.copy(alpha = 0.7f)
val GlassIconTertiary = Color.White.copy(alpha = 0.5f)
val GlassIconDisabled = Color.White.copy(alpha = 0.3f)

// Status colors with glass aesthetic
val GlassSuccess = Color(0xFF00E676)              // Bright green
val GlassWarning = Color(0xFFFFAB00)              // Amber
val GlassError = Color(0xFFFF5252)                // Bright red
val GlassInfo = Color(0xFF40C4FF)                 // Light blue

// Overlay colors
val GlassOverlayLight = Color.Black.copy(alpha = 0.2f)
val GlassOverlayMedium = Color.Black.copy(alpha = 0.3f)
val GlassOverlayHeavy = Color.Black.copy(alpha = 0.5f)

// Shimmer effect colors
val GlassShimmerBase = Color.Transparent
val GlassShimmerHighlight = Color.White.copy(alpha = 0.1f)

// Progress bar gradient colors
val GlassProgressStart = Color(0xFF00D9FF)        // Bright cyan
val GlassProgressEnd = Color(0xFF0066FF)          // Deep blue

// Divider colors
val GlassDividerColor = Color.White.copy(alpha = 0.1f)

// Navigation selected color (uses accent primary)
val GlassNavSelected = GlassAccentPrimary

// ----------------------------------------------------------------------------
// LIGHT THEME - Soft, clean backgrounds with subtle glass effect
// ----------------------------------------------------------------------------

// Background gradient colors
val LightGlassBackgroundStart = Color(0xFFF5F7FA)  // Very light grayish blue
val LightGlassBackgroundMid = Color(0xFFFFFFFF)    // Pure white
val LightGlassBackgroundEnd = Color(0xFFEEF2F7)    // Light blue-gray

// Alternate light background (for special screens)
val LightGlassBackgroundAlt1 = Color(0xFFE8EAF6)   // Light indigo
val LightGlassBackgroundAlt2 = Color(0xFFC5CAE9)   // Soft indigo
val LightGlassBackgroundAlt3 = Color(0xFFB39DDB)   // Soft purple

// Surface colors
val LightGlassSurfaceLight = Color.Black.copy(alpha = 0.03f)
val LightGlassSurfaceMedium = Color.Black.copy(alpha = 0.05f)
val LightGlassSurfaceHeavy = Color.Black.copy(alpha = 0.08f)

// Text colors
val LightGlassTextPrimary = Color(0xFF1A1A1A)
val LightGlassTextSecondary = Color(0xFF666666)
val LightGlassTextTertiary = Color(0xFF999999)
val LightGlassTextDisabled = Color(0xFFCCCCCC)

// Icon colors
val LightGlassIconPrimary = Color(0xFF1A1A1A)
val LightGlassIconSecondary = Color(0xFF666666)
val LightGlassIconTertiary = Color(0xFF999999)
val LightGlassIconDisabled = Color(0xFFCCCCCC)

// Border colors
val LightGlassBorderLight = Color.Black.copy(alpha = 0.08f)
val LightGlassBorderMedium = Color.Black.copy(alpha = 0.12f)
val LightGlassBorderHeavy = Color.Black.copy(alpha = 0.2f)

// Overlay colors
val LightGlassOverlayLight = Color.Black.copy(alpha = 0.1f)
val LightGlassOverlayMedium = Color.Black.copy(alpha = 0.2f)
val LightGlassOverlayHeavy = Color.Black.copy(alpha = 0.4f)

// Shimmer effect colors
val LightGlassShimmerBase = Color.Transparent
val LightGlassShimmerHighlight = Color.Black.copy(alpha = 0.05f)

// Divider colors
val LightGlassDividerColor = Color.Black.copy(alpha = 0.1f)

// Navigation selected color (uses accent primary)
val LightGlassNavSelected = GlassAccentPrimary

// ============================================================================
// THEME-AWARE COLOR ACCESSORS
// Use these composable functions to get the correct color for the current theme
// ============================================================================

/**
 * Returns the appropriate text primary color based on the current theme
 */
@Composable
fun glassTextPrimary(darkTheme: Boolean = isSystemInDarkTheme()): Color =
    if (darkTheme) GlassTextPrimary else LightGlassTextPrimary

/**
 * Returns the appropriate text secondary color based on the current theme
 */
@Composable
fun glassTextSecondary(darkTheme: Boolean = isSystemInDarkTheme()): Color =
    if (darkTheme) GlassTextSecondary else LightGlassTextSecondary

/**
 * Returns the appropriate text tertiary color based on the current theme
 */
@Composable
fun glassTextTertiary(darkTheme: Boolean = isSystemInDarkTheme()): Color =
    if (darkTheme) GlassTextTertiary else LightGlassTextTertiary

/**
 * Returns the appropriate icon primary color based on the current theme
 */
@Composable
fun glassIconPrimary(darkTheme: Boolean = isSystemInDarkTheme()): Color =
    if (darkTheme) GlassIconPrimary else LightGlassIconPrimary

/**
 * Returns the appropriate icon secondary color based on the current theme
 */
@Composable
fun glassIconSecondary(darkTheme: Boolean = isSystemInDarkTheme()): Color =
    if (darkTheme) GlassIconSecondary else LightGlassIconSecondary

/**
 * Returns the appropriate overlay color based on the current theme
 */
@Composable
fun glassOverlay(darkTheme: Boolean = isSystemInDarkTheme()): Color =
    if (darkTheme) GlassOverlayMedium else LightGlassOverlayMedium

/**
 * Returns the appropriate divider color based on the current theme
 */
@Composable
fun glassDivider(darkTheme: Boolean = isSystemInDarkTheme()): Color =
    if (darkTheme) GlassDividerColor else LightGlassDividerColor

/**
 * Returns the appropriate shimmer highlight color based on the current theme
 */
@Composable
fun glassShimmerHighlight(darkTheme: Boolean = isSystemInDarkTheme()): Color =
    if (darkTheme) GlassShimmerHighlight else LightGlassShimmerHighlight

/**
 * Returns the background gradient colors based on the current theme
 */
@Composable
fun glassBackgroundGradient(darkTheme: Boolean = isSystemInDarkTheme()): List<Color> =
    if (darkTheme) {
        listOf(GlassBackgroundStart, GlassBackgroundMid, GlassBackgroundEnd)
    } else {
        listOf(LightGlassBackgroundStart, LightGlassBackgroundMid, LightGlassBackgroundEnd)
    }

/**
 * Returns the alternate background gradient colors (for special screens)
 */
@Composable
fun glassBackgroundGradientAlt(): List<Color> =
    listOf(LightGlassBackgroundAlt1, LightGlassBackgroundAlt2, LightGlassBackgroundAlt3)

/**
 * Returns the progress bar gradient colors (same for both themes)
 */
fun glassProgressGradient(): List<Color> =
    listOf(GlassProgressStart, GlassProgressEnd)