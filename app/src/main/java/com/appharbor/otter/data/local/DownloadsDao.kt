package com.appharbor.otter.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appharbor.otter.data.models.DownloadedVideo
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadsDao {
    @Query("SELECT * FROM downloaded_videos ORDER BY timestamp DESC")
    fun getAllDownloads(): Flow<List<DownloadedVideo>>

    @Query("SELECT * FROM downloaded_videos WHERE dismissedFromHome = 0 ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentDownloads(limit: Int): Flow<List<DownloadedVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownload(video: DownloadedVideo)

    @Delete
    suspend fun deleteDownload(video: DownloadedVideo)

    @Query("UPDATE downloaded_videos SET dismissedFromHome = 1 WHERE dismissedFromHome = 0")
    suspend fun dismissAllFromHome()
}
