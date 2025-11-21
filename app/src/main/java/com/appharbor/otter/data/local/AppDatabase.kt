package com.appharbor.otter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appharbor.otter.data.models.DownloadedVideo

@Database(entities = [DownloadedVideo::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadsDao(): DownloadsDao
}
