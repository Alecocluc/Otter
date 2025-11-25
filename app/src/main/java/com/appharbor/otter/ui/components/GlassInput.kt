package com.appharbor.otter.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.isSystemInDarkTheme
import com.appharbor.otter.ui.theme.*

/**
 * Glass Input Field Component
 * 
 * A premium text input field with glassmorphism effect.
 * Features smooth focus animations, placeholder text, and customizable appearance.
 * 
 * @param value Current input value
 * @param onValueChange Callback when value changes
 * @param modifier Modifier for the input field
 * @param placeholder Placeholder text when field is empty
 * @param label Optional label text above the field
 * @param leadingIcon Optional leading icon composable
 * @param trailingIcon Optional trailing icon composable
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param isPassword Whether to mask input as password
 * @param keyboardType Type of keyboard to display
 * @param imeAction IME action button
 * @param onImeAction Callback for IME action
 * @param cornerRadius Corner radius (default: 20.dp)
 * @param backgroundColor Background color with transparency
 * @param textColor Text color
 * @param placeholderColor Placeholder text color
 */
@Composable
fun GlassInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: (() -> Unit)? = null,
    cornerRadius: Dp = 20.dp,
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    backgroundColor: Color? = null,
    textColor: Color? = null,
    placeholderColor: Color? = null
) {
    val effectiveBackgroundColor = backgroundColor ?: if (darkTheme) {
        GlassBackgroundMid.copy(alpha = 0.3f)
    } else {
        LightGlassSurfaceLight
    }
    
    val effectiveTextColor = textColor ?: if (darkTheme) {
        GlassTextPrimary
    } else {
        LightGlassTextPrimary
    }
    
    val effectivePlaceholderColor = placeholderColor ?: if (darkTheme) {
        GlassTextTertiary
    } else {
        LightGlassTextTertiary
    }
    
    val borderColor = if (darkTheme) {
        GlassBorderMedium
    } else {
        LightGlassBorderMedium
    }
    
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    val borderAlpha by animateFloatAsState(
        targetValue = if (isFocused) 0.4f else 0.2f,
        animationSpec = tween(durationMillis = 300),
        label = "border_alpha"
    )
    
    Column(modifier = modifier) {
        // Label
        label?.let {
            Text(
                text = it,
                color = effectiveTextColor.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
        }
        
        // Input field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            effectiveBackgroundColor.copy(alpha = effectiveBackgroundColor.alpha * 1.2f),
                            effectiveBackgroundColor
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    color = borderColor.copy(alpha = borderAlpha),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Leading icon
                leadingIcon?.let {
                    Box(modifier = Modifier.padding(end = 12.dp)) {
                        it()
                    }
                }
                
                // Text field
                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = enabled,
                        readOnly = readOnly,
                        textStyle = TextStyle(
                            color = effectiveTextColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = keyboardType,
                            imeAction = imeAction
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { onImeAction?.invoke() },
                            onGo = { onImeAction?.invoke() },
                            onSearch = { onImeAction?.invoke() },
                            onSend = { onImeAction?.invoke() }
                        ),
                        singleLine = true,
                        visualTransformation = if (isPassword) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                        cursorBrush = SolidColor(effectiveTextColor),
                        interactionSource = interactionSource
                    )
                    
                    // Placeholder
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = effectivePlaceholderColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                
                // Trailing icon
                trailingIcon?.let {
                    Box(modifier = Modifier.padding(start = 12.dp)) {
                        it()
                    }
                }
            }
        }
    }
}

/**
 * Glass Text Area
 * 
 * A multiline glass input field for longer text input.
 * 
 * @param value Current input value
 * @param onValueChange Callback when value changes
 * @param modifier Modifier for the text area
 * @param placeholder Placeholder text when field is empty
 * @param label Optional label text above the field
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param minLines Minimum number of lines to display
 * @param maxLines Maximum number of lines to display
 * @param cornerRadius Corner radius (default: 20.dp)
 * @param backgroundColor Background color with transparency
 * @param textColor Text color
 * @param placeholderColor Placeholder text color
 */
@Composable
fun GlassTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    minLines: Int = 3,
    maxLines: Int = 6,
    cornerRadius: Dp = 20.dp,
    darkTheme: Boolean = isSystemInDarkTheme(),
    backgroundColor: Color? = null,
    textColor: Color? = null,
    placeholderColor: Color? = null
) {
    val effectiveBackgroundColor = backgroundColor ?: if (darkTheme) {
        GlassBackgroundMid.copy(alpha = 0.3f)
    } else {
        LightGlassSurfaceLight
    }
    
    val effectiveTextColor = textColor ?: if (darkTheme) {
        GlassTextPrimary
    } else {
        LightGlassTextPrimary
    }
    
    val effectivePlaceholderColor = placeholderColor ?: if (darkTheme) {
        GlassTextTertiary
    } else {
        LightGlassTextTertiary
    }
    
    val borderColor = if (darkTheme) GlassBorderMedium else LightGlassBorderMedium
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    val borderAlpha by animateFloatAsState(
        targetValue = if (isFocused) 0.4f else 0.2f,
        animationSpec = tween(durationMillis = 300),
        label = "border_alpha"
    )
    
    Column(modifier = modifier) {
        // Label
        label?.let {
            Text(
                text = it,
                color = effectiveTextColor.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
        }
        
        // Text area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = (minLines * 24).dp, max = (maxLines * 24).dp)
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            effectiveBackgroundColor.copy(alpha = effectiveBackgroundColor.alpha * 1.2f),
                            effectiveBackgroundColor
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    color = borderColor.copy(alpha = borderAlpha),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(16.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = TextStyle(
                    color = effectiveTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp
                ),
                cursorBrush = SolidColor(effectiveTextColor),
                interactionSource = interactionSource
            )
            
            // Placeholder
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = effectivePlaceholderColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp
                )
            }
        }
    }
}



/**
 * Glass Search Input
 * 
 * A specialized glass input optimized for search functionality.
 * 
 * @param value Current search query
 * @param onValueChange Callback when query changes
 * @param onSearch Callback when search is triggered
 * @param modifier Modifier for the search field
 * @param placeholder Placeholder text
 */
@Composable
fun GlassSearchInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme()
) {
    GlassInput(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        darkTheme = darkTheme,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = if (darkTheme) GlassIconSecondary else LightGlassIconSecondary,
                modifier = Modifier.size(20.dp)
            )
        },
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Search,
        onImeAction = onSearch,
        cornerRadius = 28.dp
    )
}
