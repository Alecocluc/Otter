package com.appharbor.otter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.appharbor.otter.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appharbor.otter.ui.components.GlassSearchInput
import com.appharbor.otter.ui.components.GlassButton
import com.appharbor.otter.ui.components.GlassSnackbarHost
import com.appharbor.otter.ui.components.GlassProgressSnackbar
import com.appharbor.otter.ui.components.GlassBottomSheetContent
import com.appharbor.otter.ui.components.GlassTitleBar
import com.appharbor.otter.ui.components.GlassVideoCard
import com.appharbor.otter.ui.components.GlassCard
import com.appharbor.otter.ui.viewmodels.HomeViewModel
import com.appharbor.otter.data.models.VideoInfo
import com.appharbor.otter.ui.theme.*
import android.os.Environment
import java.io.File
import android.content.Intent
import androidx.core.content.FileProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val videoInfo by viewModel.videoInfo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val downloadStatus by viewModel.downloadStatus.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()
    val recentDownloads by viewModel.recentDownloads.collectAsState(initial = emptyList())

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    
    val textPrimary = if (darkTheme) GlassTextPrimary else LightGlassTextPrimary
    val textSecondary = if (darkTheme) GlassTextSecondary else LightGlassTextSecondary

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
            ) {
                item {
                    // Hero Section
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GlassTitleBar(
                            title = stringResource(R.string.app_name),
                            darkTheme = darkTheme
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = "Download videos with ease",
                            style = MaterialTheme.typography.bodyMedium,
                            color = textSecondary,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    
                    // Search Input
                    GlassSearchInput(
                        value = searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        onSearch = viewModel::searchVideo,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = "Paste video URL here...",
                        darkTheme = darkTheme
                    )
                }

                if (isLoading && downloadStatus.isEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(48.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = textPrimary)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading video info...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = textSecondary
                            )
                        }
                    }
                }

                item {
                    error?.let {
                        Spacer(modifier = Modifier.height(24.dp))
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            darkTheme = darkTheme
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Error",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = GlassError
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = textSecondary
                                )
                            }
                        }
                    }
                }
                
                if (recentDownloads.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        // Section Header
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            cornerRadius = 12.dp,
                            darkTheme = darkTheme
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = stringResource(R.string.recent_downloads),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = textPrimary
                                    )
                                    Text(
                                        text = "${recentDownloads.size} video${if (recentDownloads.size > 1) "s" else ""}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = textSecondary
                                    )
                                }
                                
                                TextButton(onClick = { viewModel.clearRecentDownloads() }) {
                                    Text(
                                        text = stringResource(R.string.clear_recent),
                                        color = textSecondary,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    
                    items(recentDownloads.take(3)) { video ->
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
                            onPlay = { 
                                // Simple play intent
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    // Note: This might fail without FileProvider on newer Android versions for file:// URIs
                                    // But for now we keep it simple as requested
                                    // intent.setDataAndType(android.net.Uri.parse(video.filePath), "video/mp4")
                                    // context.startActivity(intent)
                                } catch (e: Exception) {
                                    // Handle error
                                }
                            },
                            darkTheme = darkTheme
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

        if (videoInfo != null) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.clearVideoInfo() },
                sheetState = sheetState,
                containerColor = Color.Transparent,
                dragHandle = null
            ) {
                GlassBottomSheetContent {
                    VideoDownloadSheetContent(
                        videoInfo = videoInfo!!,
                        darkTheme = darkTheme,
                        onDownload = { formatId ->
                            // Clean filename by removing invalid characters
                            val cleanTitle = videoInfo!!.title
                                ?.replace(Regex("[\\\\/:*?\"<>|]"), "")
                                ?.take(50) ?: "video"
                            val filename = "$cleanTitle.mp4"
                            
                            // Use public Downloads folder with Otter subfolder
                            val downloadDir = File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                "Otter"
                            )
                            
                            // Create directory if it doesn't exist
                            if (!downloadDir.exists()) {
                                downloadDir.mkdirs()
                            }
                            
                            val path = File(downloadDir, filename).absolutePath
                            viewModel.downloadVideo(searchQuery, path, formatId)
                            viewModel.clearVideoInfo()
                        }
                    )
                }
            }
        }
        
        // Snackbar positioned at the bottom, just above the navbar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp), // Above the floating navbar
            contentAlignment = Alignment.BottomCenter
        ) {
            if (downloadStatus.isNotEmpty() && !downloadStatus.startsWith("Download completed")) {
                GlassProgressSnackbar(
                    message = downloadStatus,
                    progress = downloadProgress,
                    darkTheme = darkTheme
                )
            } else {
                GlassSnackbarHost(hostState = snackbarHostState)
            }
        }
    }
}

@Composable
fun VideoDownloadSheetContent(
    videoInfo: VideoInfo, 
    onDownload: (String?) -> Unit,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val textPrimary = if (darkTheme) GlassTextPrimary else LightGlassTextPrimary
    val textSecondary = if (darkTheme) GlassTextSecondary else LightGlassTextSecondary
    
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Show thumbnail preview if available
        if (videoInfo.thumbnail != null) {
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(videoInfo.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "Video Preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        Text(
            text = videoInfo.title ?: stringResource(R.string.unknown_title),
            style = MaterialTheme.typography.titleLarge,
            color = textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.duration_format, videoInfo.duration ?: 0),
            color = textSecondary
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.formats_available, videoInfo.formats.size),
            color = textPrimary
        )
        Spacer(modifier = Modifier.height(24.dp))

        GlassButton(
            text = stringResource(R.string.download_best_quality),
            onClick = { onDownload("best") },
            modifier = Modifier.fillMaxWidth(),
            darkTheme = darkTheme
        )
    }
}
