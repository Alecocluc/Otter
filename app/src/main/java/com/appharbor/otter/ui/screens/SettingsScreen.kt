package com.appharbor.otter.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.appharbor.otter.R
import com.appharbor.otter.ui.components.GlassCard
import com.appharbor.otter.ui.theme.*

@Composable
fun SettingsScreen(
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val textPrimary = if (darkTheme) GlassTextPrimary else LightGlassTextPrimary
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            color = textPrimary,
            fontSize = 24.sp
        )
    }
}
