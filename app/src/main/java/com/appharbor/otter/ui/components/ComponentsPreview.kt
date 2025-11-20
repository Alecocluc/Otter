package com.appharbor.otter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.appharbor.otter.ui.theme.OtterTheme

/**
 * Components Preview Screen
 * 
 * A comprehensive showcase of all glassmorphism components available in the app.
 * This screen serves as both a visual reference and a testing ground for components.
 * 
 * Use this screen during development to:
 * - Preview all components in one place
 * - Test interactions and animations
 * - Verify visual consistency
 * - Demonstrate the design system
 */
@Composable
fun ComponentsPreview() {
    var inputValue by remember { mutableStateOf("") }
    var searchValue by remember { mutableStateOf("") }
    var textAreaValue by remember { mutableStateOf("") }
    var toggleStates by remember { mutableStateOf(listOf(false, true, false)) }
    var checkboxStates by remember { mutableStateOf(listOf(true, false, true, false)) }
    var selectedRadio by remember { mutableStateOf(0) }
    var selectedSegment by remember { mutableStateOf(0) }
    
    GlassBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "Glass Components",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Premium UI Component Library",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            
            // Cards Section
            item {
                ComponentSection(title = "Cards") {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Standard Glass Card",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        
                        GlassCardCompact(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Compact Glass Card",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        
                        GlassCardShimmer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        )
                    }
                }
            }
            
            // Buttons Section
            item {
                ComponentSection(title = "Buttons") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        GlassButton(
                            text = "Primary Button",
                            onClick = { },
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        GlassButton(
                            text = "Disabled Button",
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            GlassIconButton(
                                onClick = { }
                            ) {
                                Text(
                                    text = "♥",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }
                            
                            GlassIconButton(
                                onClick = { }
                            ) {
                                Text(
                                    text = "★",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }
                            
                            GlassFAB(
                                onClick = { }
                            ) {
                                Text(
                                    text = "+",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        GlassExtendedFAB(
                            text = "New Download",
                            onClick = { },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        
                        GlassSegmentedButton(
                            options = listOf("Video", "Audio", "Both"),
                            selectedIndex = selectedSegment,
                            onSelectionChanged = { selectedSegment = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            
            // Input Fields Section
            item {
                ComponentSection(title = "Input Fields") {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        GlassInput(
                            value = inputValue,
                            onValueChange = { inputValue = it },
                            placeholder = "Enter URL...",
                            label = "Video URL",
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        GlassSearchInput(
                            value = searchValue,
                            onValueChange = { searchValue = it },
                            onSearch = { },
                            placeholder = "Search downloads...",
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        GlassTextArea(
                            value = textAreaValue,
                            onValueChange = { textAreaValue = it },
                            placeholder = "Enter multiple URLs (one per line)...",
                            label = "Batch Download",
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 4
                        )
                    }
                }
            }
            
            // Toggles Section
            item {
                ComponentSection(title = "Toggles") {
                    GlassToggleGroup(
                        items = listOf(
                            ToggleItem(
                                label = "Auto-download",
                                description = "Start downloads automatically",
                                checked = toggleStates[0]
                            ),
                            ToggleItem(
                                label = "High Quality",
                                description = "Download highest available quality",
                                checked = toggleStates[1]
                            ),
                            ToggleItem(
                                label = "Notifications",
                                description = "Show download progress notifications",
                                checked = toggleStates[2]
                            )
                        ),
                        onItemToggled = { index, checked ->
                            toggleStates = toggleStates.toMutableList().apply {
                                this[index] = checked
                            }
                        }
                    )
                }
            }
            
            // Checkboxes Section
            item {
                ComponentSection(title = "Checkboxes") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        GlassCheckboxGroup(
                            items = listOf(
                                CheckboxItem("Download video", checkboxStates[0]),
                                CheckboxItem("Download audio", checkboxStates[1]),
                                CheckboxItem("Download subtitles", checkboxStates[2]),
                                CheckboxItem("Download thumbnail", checkboxStates[3])
                            ),
                            onItemCheckedChange = { index, checked ->
                                checkboxStates = checkboxStates.toMutableList().apply {
                                    this[index] = checked
                                }
                            }
                        )
                    }
                }
            }
            
            // Radio Buttons Section
            item {
                ComponentSection(title = "Radio Buttons") {
                    GlassRadioGroup(
                        items = listOf("Best Quality", "1080p", "720p", "Audio Only"),
                        selectedIndex = selectedRadio,
                        onItemSelected = { selectedRadio = it }
                    )
                }
            }

            // Navigation Section
            item {
                ComponentSection(title = "Navigation") {
                    var selectedNav by remember { mutableStateOf(0) }
                    
                    GlassNavigationBar(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        GlassNavigationBarItem(
                            selected = selectedNav == 0,
                            onClick = { selectedNav = 0 },
                            icon = Icons.Default.Home,
                            label = "Home"
                        )
                        GlassNavigationBarItem(
                            selected = selectedNav == 1,
                            onClick = { selectedNav = 1 },
                            icon = Icons.Default.Search,
                            label = "Search"
                        )
                        GlassNavigationBarItem(
                            selected = selectedNav == 2,
                            onClick = { selectedNav = 2 },
                            icon = Icons.Default.Settings,
                            label = "Settings"
                        )
                    }
                }
            }

            // Feedback Section
            item {
                ComponentSection(title = "Feedback") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        GlassSnackbarContent(
                            message = "Download started successfully",
                            actionLabel = "View"
                        )
                        
                        GlassSnackbarContent(
                            message = "Connection lost. Retrying...",
                        )
                    }
                }
            }
            
            // Dividers Section
            item {
                ComponentSection(title = "Dividers & Spacers") {
                    Column {
                        Text(
                            text = "Content Above",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                        
                        GlassSpacer(height = 24.dp, showDivider = true)
                        
                        Text(
                            text = "Content Below",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        GlassDivider()
                    }
                }
            }
            
            // Footer Spacer
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

/**
 * Component Section Container
 * 
 * A reusable container for grouping related components in the preview.
 * 
 * @param title Section title
 * @param content Section content
 */
@Composable
private fun ComponentSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        
        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComponents() {
    OtterTheme {
        ComponentsPreview()
    }
}
