package com.appharbor.otter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.isSystemInDarkTheme
import com.appharbor.otter.ui.theme.*

/**
 * Glass Bottom Sheet Content
 * 
 * A container for bottom sheet content with glassmorphism styling.
 * 
 * @param modifier Modifier for the container
 * @param content The content to display inside the sheet
 */
@Composable
fun GlassBottomSheetContent(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable ColumnScope.() -> Unit
) {
    val backgroundColor = if (darkTheme) {
        listOf(
            GlassBackgroundMid.copy(alpha = 0.5f),
            GlassBackgroundStart.copy(alpha = 0.6f)
        )
    } else {
        listOf(
            Color.White.copy(alpha = 0.9f),
            LightGlassBackgroundStart.copy(alpha = 0.95f)
        )
    }
    
    val handleColor = if (darkTheme) {
        GlassBorderMedium
    } else {
        LightGlassBorderHeavy
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(
                brush = Brush.verticalGradient(colors = backgroundColor)
            )
    ) {
        // Top handle
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 12.dp)
                .width(40.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(handleColor)
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp, bottom = 80.dp),
            content = content
        )
    }
}
