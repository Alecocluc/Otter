package com.appharbor.otter.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appharbor.otter.data.models.VideoInfo
import com.appharbor.otter.data.python.YtDlpBridge
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val ytDlpBridge = YtDlpBridge()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _videoInfo = MutableStateFlow<VideoInfo?>(null)
    val videoInfo: StateFlow<VideoInfo?> = _videoInfo.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _downloadProgress = MutableStateFlow(0f)
    val downloadProgress: StateFlow<Float> = _downloadProgress.asStateFlow()
    
    private val _downloadStatus = MutableStateFlow("")
    val downloadStatus: StateFlow<String> = _downloadStatus.asStateFlow()

    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent: SharedFlow<String> = _snackbarEvent.asSharedFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchVideo() {
        val query = _searchQuery.value
        if (query.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _videoInfo.value = null
            
            val info = ytDlpBridge.getVideoInfo(query)
            
            if (info.error != null) {
                _error.value = info.error
                Log.e("HomeViewModel", "Search error: ${info.error}")
            } else {
                _videoInfo.value = info
                Log.d("HomeViewModel", "Video info retrieved: ${info.title}")
            }
            
            _isLoading.value = false
        }
    }
    
    fun downloadVideo(url: String, outputPath: String, formatId: String? = null) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _downloadStatus.value = "Preparing download…"
                _downloadProgress.value = 0f
                
                Log.d("HomeViewModel", "Download initiated: url=$url, path=$outputPath, format=$formatId")
                
                var lastUpdateTime = 0L
                
                val result = ytDlpBridge.downloadVideo(url, outputPath, formatId) { progress, speed ->
                    val currentTime = System.currentTimeMillis()
                    // Throttle updates to every 200ms (5fps) to prevent UI jitter
                    // Always update if progress is 0 (start) or 1 (finish)
                    if (currentTime - lastUpdateTime >= 200 || progress >= 1f || progress <= 0f) {
                        lastUpdateTime = currentTime

                        // Progress is already 0-1, no need to multiply by 100
                        _downloadProgress.value = progress
                        val speedFormatted = try {
                            val speedBytes = speed.toDoubleOrNull() ?: 0.0
                            if (speedBytes > 1024 * 1024) {
                                "%.2f MB/s".format(speedBytes / (1024 * 1024))
                            } else if (speedBytes > 1024) {
                                "%.2f KB/s".format(speedBytes / 1024)
                            } else {
                                "$speedBytes B/s"
                            }
                        } catch (e: Exception) {
                            speed
                        }
                        _downloadStatus.value = "Downloading: ${(progress * 100).toInt()}% ($speedFormatted)"
                    }
                }
                
                result.onSuccess { path ->
                    _downloadStatus.value = "Download completed!"
                    _snackbarEvent.emit("Download completed: $path")
                    Log.d("HomeViewModel", "Download successful: $path")
                }.onFailure { error ->
                    val errorMsg = error.message ?: "Unknown error"
                    _downloadStatus.value = "Download failed: $errorMsg"
                    _snackbarEvent.emit("Download failed: $errorMsg")
                    Log.e("HomeViewModel", "Download failed", error)
                }
            } catch (e: Exception) {
                _downloadStatus.value = "Download failed: ${e.message}"
                _snackbarEvent.emit("Download failed: ${e.message}")
                Log.e("HomeViewModel", "Download exception", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun clearVideoInfo() {
        _videoInfo.value = null
    }
}
