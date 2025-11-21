package com.appharbor.otter.data.repositories

import android.content.Context
import androidx.room.Room
import com.appharbor.otter.data.local.AppDatabase
import com.appharbor.otter.data.models.DownloadedVideo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object DownloadsRepository {
    private var database: AppDatabase? = null
    
    private val _downloadedVideos = MutableStateFlow<List<DownloadedVideo>>(emptyList())
    val downloadedVideos: Flow<List<DownloadedVideo>>
        get() = database?.downloadsDao()?.getAllDownloads() ?: _downloadedVideos

    fun initialize(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "otter_database"
            )
            .fallbackToDestructiveMigration() // For simplicity in development
            .build()
        }
    }

    suspend fun addVideo(video: DownloadedVideo) {
        database?.downloadsDao()?.insertDownload(video)
    }
    
    fun getRecent(limit: Int = 5): Flow<List<DownloadedVideo>> {
        return database?.downloadsDao()?.getRecentDownloads(limit) ?: MutableStateFlow(emptyList())
    }

    suspend fun clearRecent() {
        database?.downloadsDao()?.dismissAllFromHome()
    }
}
