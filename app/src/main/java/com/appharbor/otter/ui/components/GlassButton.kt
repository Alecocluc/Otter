package com.appharbor.otter.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Glass Button Component
 * 
 * A premium button with glassmorphism effect, smooth animations, and haptic feedback.
 * Features press states, scale animations, and gradient backgrounds.
 * 
 * @param text Button text
 * @param onClick Callback when button is clicked
 * @param modifier Modifier for the button
 * @param enabled Whether the button is enabled
 * @param cornerRadius Corner radius (default: 20.dp)
 * @param backgroundColor Background color with transparency
 * @param textColor Text color
 * @param icon Optional leading icon composable
 */
@Composable
fun GlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    cornerRadius: Dp = 20.dp,
    backgroundColor: Color = Color.White.copy(alpha = 0.15f),
    textColor: Color = Color.White,
    icon: (@Composable () -> Unit)? = null
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    Box(
        modifier = modifier
            .scale(scale)
            .height(56.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                brush = Brush.verticalGradient(
                    colors = if (enabled) {
                        listOf(
                            backgroundColor.copy(alpha = backgroundColor.alpha * 1.3f),
                            backgroundColor
                        )
                    } else {
                        listOf(
                            backgroundColor.copy(alpha = 0.05f),
                            backgroundColor.copy(alpha = 0.05f)
                        )
                    }
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        // Subtle border
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .clip(RoundedCornerShape(cornerRadius - 1.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.2f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    )
                )
        )
        
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            icon?.let {
                it()
                Spacer(modifier = Modifier.width(12.dp))
            }
            
            Text(
                text = text,
                color = if (enabled) textColor else textColor.copy(alpha = 0.4f),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Glass Icon Button
 * 
 * A circular glass button with icon only, perfect for action buttons.
 * 
 * @param onClick Callback when button is clicked
 * @param modifier Modifier for the button
 * @param enabled Whether the button is enabled
 * @param size Button size (default: 48.dp)
 * @param backgroundColor Background color with transparency
 * @param icon Icon composable
 */
@Composable
fun GlassIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 48.dp,
    backgroundColor: Color = Color.White.copy(alpha = 0.15f),
    icon: @Composable () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    Box(
        modifier = modifier
            .scale(scale)
            .size(size)
            .clip(RoundedCornerShape(size / 2))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = backgroundColor.alpha * 1.3f),
                        backgroundColor
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

/**
 * Glass Floating Action Button
 * 
 * A premium FAB with glassmorphism effect and extended shadow.
 * 
 * @param onClick Callback when FAB is clicked
 * @param modifier Modifier for the FAB
 * @param backgroundColor Background color with transparency
 * @param icon Icon composable
 */
@Composable
fun GlassFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White.copy(alpha = 0.2f),
    icon: @Composable () -> Unit
) {
    GlassIconButton(
        onClick = onClick,
        modifier = modifier,
        size = 64.dp,
        backgroundColor = backgroundColor,
        icon = icon
    )
}

/**
 * Glass Extended Floating Action Button
 * 
 * A wide FAB with text and icon.
 * 
 * @param text Text to display
 * @param onClick Callback when FAB is clicked
 * @param modifier Modifier for the FAB
 * @param backgroundColor Background color with transparency
 * @param icon Optional icon composable
 */
@Composable
fun GlassExtendedFAB(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White.copy(alpha = 0.2f),
    icon: (@Composable () -> Unit)? = null
) {
    GlassButton(
        text = text,
        onClick = onClick,
        modifier = modifier.height(56.dp),
        cornerRadius = 28.dp,
        backgroundColor = backgroundColor,
        icon = icon
    )
}

/**
 * Glass Segmented Button
 * 
 * A segmented control with multiple options, glassmorphism style.
 * 
 * @param options List of option texts
 * @param selectedIndex Currently selected option index
 * @param onSelectionChanged Callback when selection changes
 * @param modifier Modifier for the segmented button
 */
@Composable
fun GlassSegmentedButton(
    options: List<String>,
    selectedIndex: Int,
    onSelectionChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.1f))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = index == selectedIndex
            val haptic = LocalHapticFeedback.current
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isSelected) {
                            Color.White.copy(alpha = 0.25f)
                        } else {
                            Color.Transparent
                        }
                    )
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onSelectionChanged(index)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}
