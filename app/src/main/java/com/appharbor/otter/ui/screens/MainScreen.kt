package com.appharbor.otter.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.appharbor.otter.R
import com.appharbor.otter.ui.components.GlassBackground
import com.appharbor.otter.ui.components.GlassNavigationBar
import com.appharbor.otter.ui.components.GlassNavigationBarItem

@Composable
fun MainScreen(
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    var selectedNav by remember { mutableIntStateOf(0) }

    GlassBackground(darkTheme = darkTheme) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                GlassNavigationBar(darkTheme = darkTheme) {
                    GlassNavigationBarItem(
                        selected = selectedNav == 0,
                        onClick = { selectedNav = 0 },
                        icon = Icons.Default.Home,
                        label = stringResource(R.string.nav_home),
                        darkTheme = darkTheme
                    )
                    GlassNavigationBarItem(
                        selected = selectedNav == 1,
                        onClick = { selectedNav = 1 },
                        icon = Icons.Default.Download,
                        label = stringResource(R.string.nav_downloads),
                        darkTheme = darkTheme
                    )
                    GlassNavigationBarItem(
                        selected = selectedNav == 2,
                        onClick = { selectedNav = 2 },
                        icon = Icons.Default.Settings,
                        label = stringResource(R.string.nav_settings),
                        darkTheme = darkTheme
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedNav) {
                    0 -> HomeScreen(darkTheme = darkTheme)
                    1 -> DownloadsScreen(darkTheme = darkTheme)
                    2 -> SettingsScreen(darkTheme = darkTheme)
                }
            }
        }
    }
}
