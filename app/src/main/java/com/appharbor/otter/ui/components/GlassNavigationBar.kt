package com.appharbor.otter.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.isSystemInDarkTheme
import com.appharbor.otter.ui.theme.*

/**
 * Glass Navigation Bar
 * 
 * A floating, capsule-style bottom navigation bar with glassmorphism effect.
 * 
 * @param modifier Modifier for the navigation bar
 * @param content Content of the navigation bar (GlassNavigationBarItem)
 */
@Composable
fun GlassNavigationBar(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(36.dp)) // Capsule shape
    ) {
        // Background with blur
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (darkTheme) {
                            listOf(
                                GlassBackgroundMid.copy(alpha = 0.4f),
                                GlassBackgroundStart.copy(alpha = 0.5f)
                            )
                        } else {
                            listOf(
                                Color.White.copy(alpha = 0.9f),
                                LightGlassBackgroundStart.copy(alpha = 0.95f)
                            )
                        }
                    )
                )
                .blur(radius = 20.dp)
        )
        
        // Border
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = if (darkTheme) {
                            listOf(
                                GlassBorderMedium,
                                GlassBorderLight.copy(alpha = 0.05f)
                            )
                        } else {
                            listOf(
                                LightGlassBorderMedium,
                                LightGlassBorderLight
                            )
                        }
                    ),
                    shape = RoundedCornerShape(36.dp)
                )
        )
        
        // Items
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

/**
 * Glass Navigation Bar Item
 * 
 * An item within the GlassNavigationBar.
 * 
 * @param selected Whether this item is selected
 * @param onClick Callback when item is clicked
 * @param icon Icon for the item
 * @param label Label text for the item
 * @param selectedColor Color when selected (default: primary accent)
 * @param unselectedColor Color when unselected
 */
@Composable
fun RowScope.GlassNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    selectedColor: Color = Color(0xFF6C63FF),
    unselectedColor: Color? = null
) {
    val itemUnselectedColor = unselectedColor ?: if (darkTheme) {
        Color.White.copy(alpha = 0.5f)
    } else {
        Color.Black.copy(alpha = 0.5f)
    }
    
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.1f else 1f,
        animationSpec = spring(),
        label = "scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = spring(),
        label = "alpha"
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                if (!selected) {
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Active indicator glow
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .scale(scale)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                selectedColor.copy(alpha = 0.3f * alpha),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
            
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) {
                    if (darkTheme) Color.White else selectedColor
                } else {
                    itemUnselectedColor
                },
                modifier = Modifier
                    .size(24.dp)
                    .scale(scale)
            )
        }
        
        if (selected) {
            Text(
                text = label,
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.8f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
