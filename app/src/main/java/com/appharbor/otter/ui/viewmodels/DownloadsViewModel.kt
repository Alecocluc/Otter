package com.appharbor.otter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.appharbor.otter.data.models.DownloadedVideo
import com.appharbor.otter.data.repositories.DownloadsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class DownloadsViewModel : ViewModel() {
    val downloads: Flow<List<DownloadedVideo>> = DownloadsRepository.downloadedVideos
}
