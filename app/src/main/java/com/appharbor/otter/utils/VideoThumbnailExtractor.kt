package com.appharbor.otter.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream

/**
 * Utility for extracting video thumbnails from local video files.
 * 
 * This mimics how gallery apps display video thumbnails by extracting
 * a frame from the actual video file rather than using remote URLs.
 */
object VideoThumbnailExtractor {
    
    private const val TAG = "VideoThumbnailExtractor"
    
    /**
     * Extract a thumbnail from a video file and save it to cache.
     * 
     * @param context Android context
     * @param videoPath Absolute path to the video file
     * @return URI to the cached thumbnail file, or null if extraction fails
     */
    fun extractThumbnail(context: Context, videoPath: String): Uri? {
        return try {
            val videoFile = File(videoPath)
            if (!videoFile.exists()) {
                Log.e(TAG, "Video file does not exist: $videoPath")
                return null
            }
            
            // Check if thumbnail already exists in cache
            val thumbnailFile = getThumbnailFile(context, videoPath)
            if (thumbnailFile.exists()) {
                Log.d(TAG, "Using cached thumbnail: ${thumbnailFile.absolutePath}")
                return Uri.fromFile(thumbnailFile)
            }
            
            // Extract thumbnail using MediaMetadataRetriever
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(videoPath)
            
            // Get frame at 1 second (or first frame if video is shorter)
            val bitmap = retriever.getFrameAtTime(
                1_000_000, // 1 second in microseconds
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC
            )
            
            retriever.release()
            
            if (bitmap == null) {
                Log.e(TAG, "Failed to extract frame from video: $videoPath")
                return null
            }
            
            // Save thumbnail to cache
            thumbnailFile.parentFile?.mkdirs()
            FileOutputStream(thumbnailFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
            }
            bitmap.recycle()
            
            Log.d(TAG, "Thumbnail extracted and saved: ${thumbnailFile.absolutePath}")
            Uri.fromFile(thumbnailFile)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting thumbnail from $videoPath", e)
            null
        }
    }
    
    /**
     * Get the cache file path for a video thumbnail.
     */
    private fun getThumbnailFile(context: Context, videoPath: String): File {
        val cacheDir = File(context.cacheDir, "video_thumbnails")
        val videoFileName = File(videoPath).name
        val thumbnailName = "${videoFileName.hashCode()}.jpg"
        return File(cacheDir, thumbnailName)
    }
    
    /**
     * Clear all cached thumbnails.
     */
    fun clearThumbnailCache(context: Context) {
        try {
            val cacheDir = File(context.cacheDir, "video_thumbnails")
            if (cacheDir.exists()) {
                cacheDir.deleteRecursively()
                Log.d(TAG, "Thumbnail cache cleared")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing thumbnail cache", e)
        }
    }
}
