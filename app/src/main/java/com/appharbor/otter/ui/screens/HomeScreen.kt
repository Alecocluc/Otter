package com.appharbor.otter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appharbor.otter.ui.components.GlassSearchInput
import com.appharbor.otter.ui.components.GlassButton
import com.appharbor.otter.ui.components.GlassSnackbarHost
import com.appharbor.otter.ui.components.GlassBottomSheetContent
import com.appharbor.otter.ui.viewmodels.HomeViewModel
import com.appharbor.otter.data.models.VideoInfo
import android.os.Environment
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val videoInfo by viewModel.videoInfo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val downloadStatus by viewModel.downloadStatus.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { 
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                GlassSnackbarHost(hostState = snackbarHostState)
            }
        },
        containerColor = Color.Transparent,
        contentColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                
                GlassSearchInput(
                    value = searchQuery,
                    onValueChange = viewModel::onSearchQueryChange,
                    onSearch = viewModel::searchVideo,
                    modifier = Modifier.fillMaxWidth()
                )

                if (isLoading) {
                    Spacer(modifier = Modifier.height(32.dp))
                    CircularProgressIndicator(color = Color.White)
                    if (downloadStatus.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = downloadStatus, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { downloadProgress },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }

                error?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = it, color = Color.Red)
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
                            videoInfo = videoInfo!!, onDownload = { formatId ->
                                val filename = "${videoInfo!!.title?.take(50) ?: "video"}.mp4"
                                val downloadDir =
                                    context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                                val path = File(downloadDir, filename).absolutePath
                                viewModel.downloadVideo(searchQuery, path, formatId)
                                viewModel.clearVideoInfo()
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun VideoDownloadSheetContent(
    videoInfo: VideoInfo, onDownload: (String?) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = videoInfo.title ?: "Unknown Title",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Duration: ${videoInfo.duration ?: 0}s", color = Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Formats available: ${videoInfo.formats.size}", color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))

        GlassButton(
            text = "Download Best Quality",
            onClick = { onDownload("best") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
