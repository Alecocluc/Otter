package com.appharbor.otter.ui.viewmodels

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
            } else {
                _videoInfo.value = info
            }
            
            _isLoading.value = false
        }
    }
    
    fun downloadVideo(url: String, outputPath: String, formatId: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _downloadStatus.value = "Starting download..."
            _downloadProgress.value = 0f
            _snackbarEvent.emit("Download started")
            
            val success = ytDlpBridge.downloadVideo(url, outputPath, formatId) { progress, speed ->
                _downloadProgress.value = progress
                _downloadStatus.value = "Downloading: ${(progress * 100).toInt()}% ($speed)"
            }
            
            if (success) {
                _downloadStatus.value = "Download completed!"
                _snackbarEvent.emit("Download completed successfully")
            } else {
                _downloadStatus.value = "Download failed."
                _snackbarEvent.emit("Download failed")
            }
            _isLoading.value = false
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun clearVideoInfo() {
        _videoInfo.value = null
    }
}
