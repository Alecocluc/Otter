package com.appharbor.otter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.appharbor.otter.data.models.DownloadedVideo
import com.appharbor.otter.utils.VideoThumbnailExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun GlassVideoCard(
    video: DownloadedVideo,
    onShare: (DownloadedVideo) -> Unit,
    onPlay: (DownloadedVideo) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var localThumbnailUri by remember { mutableStateOf<android.net.Uri?>(null) }
    
    // Extract thumbnail from local video file
    LaunchedEffect(video.filePath) {
        withContext(Dispatchers.IO) {
            val uri = VideoThumbnailExtractor.extractThumbnail(context, video.filePath)
            localThumbnailUri = uri
        }
    }
    
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 16.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            GlassCardCompact(
                modifier = Modifier.size(60.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Use local thumbnail extracted from video file
                    if (localThumbnailUri != null) {
                        AsyncImage(
                            model = localThumbnailUri,
                            contentDescription = "Video Thumbnail",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Overlay play icon
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        // Fallback: show play icon while loading or if extraction fails
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Video",
                            tint = Color.White
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (video.duration != null) formatDuration(video.duration) else "Unknown duration",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
            
            IconButton(onClick = { onShare(video) }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White
                )
            }
        }
    }
}

private fun formatDuration(seconds: Double): String {
    val s = seconds.toLong()
    val m = s / 60
    val h = m / 60
    return if (h > 0) {
        "%d:%02d:%02d".format(h, m % 60, s % 60)
    } else {
        "%d:%02d".format(m, s % 60)
    }
}
