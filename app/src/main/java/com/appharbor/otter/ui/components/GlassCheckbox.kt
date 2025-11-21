package com.appharbor.otter.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appharbor.otter.ui.theme.*

/**
 * Glass Checkbox Component
 *
 * A premium checkbox with glassmorphism effect and smooth animations.
 * Features scale animations, haptic feedback, and customizable appearance.
 *
 * @param checked Whether the checkbox is checked
 * @param onCheckedChange Callback when checked state changes
 * @param modifier Modifier for the checkbox
 * @param label Optional label text next to checkbox
 * @param enabled Whether the checkbox is enabled
 * @param backgroundColor Background color with transparency
 * @param checkedColor Color when checked
 * @param textColor Label text color
 */
@Composable
fun GlassCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    backgroundColor: Color? = null,
    checkedColor: Color? = null,
    textColor: Color? = null
) {
    val effectiveBackgroundColor = backgroundColor ?: if (darkTheme) {
        GlassBackgroundMid.copy(alpha = 0.3f)
    } else {
        LightGlassSurfaceLight
    }

    val effectiveCheckedColor = checkedColor ?: if (darkTheme) {
        GlassAccentPrimary
    } else {
        GlassAccentPrimary
    }

    val effectiveTextColor = textColor ?: if (darkTheme) {
        GlassTextPrimary
    } else {
        LightGlassTextPrimary
    }

    val borderColor = if (darkTheme) GlassBorderMedium else LightGlassBorderMedium
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (checked) 1f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessHigh
        ), label = "check_scale"
    )

    val borderAlpha by animateFloatAsState(
        targetValue = if (checked) 0.9f else 0.3f,
        animationSpec = tween(durationMillis = 200),
        label = "border_alpha"
    )

    Row(
        modifier = modifier.clickable(
            interactionSource = interactionSource, indication = null, enabled = enabled
        ) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onCheckedChange(!checked)
        }, verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox box
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            effectiveBackgroundColor.copy(alpha = effectiveBackgroundColor.alpha * 1.2f),
                            effectiveBackgroundColor
                        )
                    )
                )
                .border(
                    width = 2.dp,
                    color = borderColor.copy(alpha = borderAlpha),
                    shape = RoundedCornerShape(8.dp)
                ), contentAlignment = Alignment.Center
        ) {
            // Checkmark
            if (checked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.6f)
                        .scale(scale)
                        .clip(RoundedCornerShape(4.dp))
                        .background(effectiveCheckedColor)
                )
            }
        }

        // Label
        label?.let {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = it,
                color = if (enabled) effectiveTextColor else effectiveTextColor.copy(alpha = 0.4f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

/**
 * Glass Checkbox Group
 *
 * A group of checkboxes with glassmorphism styling.
 *
 * @param items List of checkbox items with label and checked state
 * @param onItemCheckedChange Callback when an item's checked state changes
 * @param modifier Modifier for the group
 */
@Composable
fun GlassCheckboxGroup(
    items: List<CheckboxItem>,
    onItemCheckedChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEachIndexed { index, item ->
            GlassCheckbox(
                checked = item.checked,
                onCheckedChange = { onItemCheckedChange(index, it) },
                label = item.label,
                enabled = item.enabled
            )
        }
    }
}

/**
 * Data class for checkbox items in a group
 */
data class CheckboxItem(
    val label: String, val checked: Boolean, val enabled: Boolean = true
)

/**
 * Glass Radio Button Component
 *
 * A premium radio button with glassmorphism effect.
 * Similar to checkbox but with circular shape for single-selection scenarios.
 *
 * @param selected Whether the radio button is selected
 * @param onClick Callback when radio button is clicked
 * @param modifier Modifier for the radio button
 * @param label Optional label text next to radio button
 * @param enabled Whether the radio button is enabled
 * @param backgroundColor Background color with transparency
 * @param selectedColor Color when selected
 * @param textColor Label text color
 */
@Composable
fun GlassRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    backgroundColor: Color? = null,
    selectedColor: Color? = null,
    textColor: Color? = null
) {
    val effectiveBackgroundColor = backgroundColor ?: if (darkTheme) {
        GlassBackgroundMid.copy(alpha = 0.3f)
    } else {
        LightGlassSurfaceLight
    }

    val effectiveSelectedColor = selectedColor ?: if (darkTheme) {
        GlassAccentPrimary
    } else {
        GlassAccentPrimary
    }

    val effectiveTextColor = textColor ?: if (darkTheme) {
        GlassTextPrimary
    } else {
        LightGlassTextPrimary
    }

    val borderColor = if (darkTheme) GlassBorderMedium else LightGlassBorderMedium
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (selected) 1f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessHigh
        ), label = "selected_scale"
    )

    val borderAlpha by animateFloatAsState(
        targetValue = if (selected) 0.9f else 0.3f,
        animationSpec = tween(durationMillis = 200),
        label = "border_alpha"
    )

    Row(
        modifier = modifier.clickable(
            interactionSource = interactionSource, indication = null, enabled = enabled
        ) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        }, verticalAlignment = Alignment.CenterVertically
    ) {
        // Radio button circle
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            effectiveBackgroundColor.copy(alpha = effectiveBackgroundColor.alpha * 1.2f),
                            effectiveBackgroundColor
                        )
                    )
                )
                .border(
                    width = 2.dp, color = borderColor.copy(alpha = borderAlpha), shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            // Selected dot
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .scale(scale)
                        .clip(CircleShape)
                        .background(effectiveSelectedColor)
                )
            }
        }

        // Label
        label?.let {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = it,
                color = if (enabled) effectiveTextColor else effectiveTextColor.copy(alpha = 0.4f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

/**
 * Glass Radio Group
 *
 * A group of radio buttons for single selection with glassmorphism styling.
 *
 * @param items List of radio button items with labels
 * @param selectedIndex Currently selected item index
 * @param onItemSelected Callback when an item is selected
 * @param modifier Modifier for the group
 */
@Composable
fun GlassRadioGroup(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEachIndexed { index, item ->
            GlassRadioButton(
                selected = index == selectedIndex, onClick = { onItemSelected(index) }, label = item
            )
        }
    }
}
