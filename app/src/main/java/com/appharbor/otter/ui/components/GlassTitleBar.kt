package com.appharbor.otter.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appharbor.otter.R
import com.appharbor.otter.ui.theme.*

/**
 * Glass Title Bar (Hero Component)
 * 
 * A premium hero section with glassmorphism effect, app icon, and title.
 * Features gradient backgrounds and modern styling.
 */
@Composable
fun GlassTitleBar(
    modifier: Modifier = Modifier,
    title: String = "Otter",
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val iconTint = if (darkTheme) GlassIconPrimary else LightGlassIconPrimary
    val textColor = if (darkTheme) GlassTextPrimary else LightGlassTextPrimary
    val gradientColors = if (darkTheme) {
        listOf(
            GlassBackgroundMid.copy(alpha = 0.4f),
            GlassBackgroundStart.copy(alpha = 0.2f)
        )
    } else {
        listOf(
            LightGlassSurfaceMedium.copy(alpha = 0.8f),
            LightGlassSurfaceLight.copy(alpha = 0.6f)
        )
    }
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Decorative gradient bar
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(4.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = glassAccentGradient()
                    )
                )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // App Icon with glass effect
        GlassCard(
            modifier = Modifier.size(72.dp),
            cornerRadius = 20.dp,
            darkTheme = darkTheme
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(56.dp),
                    colorFilter = ColorFilter.tint(iconTint)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Title with enhanced styling
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                letterSpacing = (-0.5).sp
            ),
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}
