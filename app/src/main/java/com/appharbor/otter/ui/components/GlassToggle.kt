package com.appharbor.otter.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Glass Toggle Component
 * 
 * A premium toggle switch with glassmorphism effect and smooth animations.
 * Features sliding thumb animation, haptic feedback, and customizable colors.
 * 
 * @param checked Whether the toggle is checked (on)
 * @param onCheckedChange Callback when checked state changes
 * @param modifier Modifier for the toggle
 * @param label Optional label text next to toggle
 * @param enabled Whether the toggle is enabled
 * @param thumbSize Size of the toggle thumb (default: 24.dp)
 * @param trackWidth Width of the toggle track (default: 52.dp)
 * @param trackHeight Height of the toggle track (default: 32.dp)
 * @param backgroundColor Track background color when off
 * @param checkedBackgroundColor Track background color when on
 * @param thumbColor Color of the toggle thumb
 * @param textColor Label text color
 */
@Composable
fun GlassToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    thumbSize: Dp = 24.dp,
    trackWidth: Dp = 52.dp,
    trackHeight: Dp = 32.dp,
    backgroundColor: Color = Color.White.copy(alpha = 0.15f),
    checkedBackgroundColor: Color = Color.White.copy(alpha = 0.3f),
    thumbColor: Color = Color.White,
    textColor: Color = Color.White
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    
    // Thumb position animation
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) trackWidth - thumbSize - 4.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "thumb_offset"
    )
    
    // Background color animation
    val trackColor by animateColorAsState(
        targetValue = if (checked) checkedBackgroundColor else backgroundColor,
        animationSpec = tween(durationMillis = 300),
        label = "track_color"
    )
    
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onCheckedChange(!checked)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Toggle track
        Box(
            modifier = Modifier
                .width(trackWidth)
                .height(trackHeight)
                .clip(RoundedCornerShape(trackHeight / 2))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            trackColor.copy(alpha = trackColor.alpha * 1.1f),
                            trackColor
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(trackHeight / 2)
                )
        ) {
            // Toggle thumb
            Box(
                modifier = Modifier
                    .offset(x = thumbOffset, y = (trackHeight - thumbSize) / 2)
                    .size(thumbSize)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                thumbColor,
                                thumbColor.copy(alpha = 0.9f)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }
        
        // Label
        label?.let {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = it,
                color = if (enabled) textColor else textColor.copy(alpha = 0.4f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

/**
 * Glass Toggle with Icon
 * 
 * A toggle switch with icons inside the track for on/off states.
 * 
 * @param checked Whether the toggle is checked (on)
 * @param onCheckedChange Callback when checked state changes
 * @param modifier Modifier for the toggle
 * @param label Optional label text next to toggle
 * @param enabled Whether the toggle is enabled
 * @param checkedIcon Icon composable to show when checked
 * @param uncheckedIcon Icon composable to show when unchecked
 */
@Composable
fun GlassToggleWithIcons(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    checkedIcon: @Composable () -> Unit,
    uncheckedIcon: @Composable () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    
    val thumbSize = 28.dp
    val trackWidth = 64.dp
    val trackHeight = 36.dp
    
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) trackWidth - thumbSize - 4.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "thumb_offset"
    )
    
    val trackColor by animateColorAsState(
        targetValue = if (checked) {
            Color.White.copy(alpha = 0.3f)
        } else {
            Color.White.copy(alpha = 0.15f)
        },
        animationSpec = tween(durationMillis = 300),
        label = "track_color"
    )
    
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onCheckedChange(!checked)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(trackWidth)
                .height(trackHeight)
                .clip(RoundedCornerShape(trackHeight / 2))
                .background(trackColor)
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(trackHeight / 2)
                )
        ) {
            // Icons
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(16.dp)) {
                    uncheckedIcon()
                }
                Box(modifier = Modifier.size(16.dp)) {
                    checkedIcon()
                }
            }
            
            // Thumb
            Box(
                modifier = Modifier
                    .offset(x = thumbOffset, y = (trackHeight - thumbSize) / 2)
                    .size(thumbSize)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }
        
        label?.let {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = it,
                color = if (enabled) Color.White else Color.White.copy(alpha = 0.4f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

/**
 * Glass Toggle Group
 * 
 * A group of toggle switches with labels and glassmorphism styling.
 * 
 * @param items List of toggle items with label and checked state
 * @param onItemToggled Callback when an item is toggled
 * @param modifier Modifier for the group
 */
@Composable
fun GlassToggleGroup(
    items: List<ToggleItem>,
    onItemToggled: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = item.label,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    item.description?.let { desc ->
                        Text(
                            text = desc,
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                
                GlassToggle(
                    checked = item.checked,
                    onCheckedChange = { onItemToggled(index, it) },
                    enabled = item.enabled
                )
            }
        }
    }
}

/**
 * Data class for toggle items in a group
 */
data class ToggleItem(
    val label: String,
    val description: String? = null,
    val checked: Boolean,
    val enabled: Boolean = true
)
