package com.appharbor.otter.ui.screens

import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appharbor.otter.R
import com.appharbor.otter.ui.components.GlassTitleBar
import com.appharbor.otter.ui.components.GlassVideoCard
import com.appharbor.otter.ui.viewmodels.DownloadsViewModel
import com.appharbor.otter.ui.theme.*

@Composable
fun DownloadsScreen(
    viewModel: DownloadsViewModel = viewModel(),
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val downloads by viewModel.downloads.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val textPrimary = if (darkTheme) GlassTextPrimary else LightGlassTextPrimary
    val textTertiary = if (darkTheme) GlassTextTertiary else LightGlassTextTertiary

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                GlassTitleBar(title = stringResource(R.string.downloads_title), darkTheme = darkTheme)
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            if (downloads.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_downloads),
                            color = textTertiary,
                            fontSize = 18.sp
                        )
                    }
                }
            } else {
                items(downloads) { video ->
                    val shareText = stringResource(R.string.share_video_text, video.title)
                    val shareTitle = stringResource(R.string.share_video_title)
                    
                    GlassVideoCard(
                        video = video,
                        onShare = { 
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, shareTitle))
                        },
                        onPlay = { /* TODO */ },
                        darkTheme = darkTheme
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
