package com.appharbor.otter.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "downloaded_videos")
data class DownloadedVideo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val thumbnail: String?,
    val duration: Double?,
    val filePath: String,
    val timestamp: Long = System.currentTimeMillis(),
    val dismissedFromHome: Boolean = false
)
