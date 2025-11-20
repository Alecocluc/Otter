package com.appharbor.otter.data.python

import android.util.Log
import com.appharbor.otter.data.models.VideoInfo
import com.chaquo.python.PyObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject

class YtDlpBridge {
    private val ytdlpModule: PyObject by lazy {
        PythonManager.getModule("ytdlp_wrapper")
    }
    
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getVideoInfo(url: String): VideoInfo = withContext(Dispatchers.IO) {
        try {
            val result = ytdlpModule.callAttr("get_video_info", url)
            val jsonString = result.toString()
            json.decodeFromString<VideoInfo>(jsonString)
        } catch (e: Exception) {
            Log.e("YtDlpBridge", "Error getting video info", e)
            VideoInfo(error = e.message)
        }
    }

    suspend fun downloadVideo(
        url: String,
        outputPath: String,
        formatId: String? = null,
        onProgress: (Float, String) -> Unit
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            Log.d("YtDlpBridge", "Starting download: url=$url, path=$outputPath, format=$formatId")
            
            val callback = object : ProgressCallback {
                override fun onProgress(data: Map<String, Any?>) {
                    try {
                        val status = data["status"]?.toString()
                        if (status == "downloading") {
                            // Parse bytes as Double first to handle potential float strings from Python, then convert to Long
                            val downloaded = data["downloaded_bytes"]?.toString()?.toDoubleOrNull()?.toLong() ?: 0L
                            val total = data["total_bytes"]?.toString()?.toDoubleOrNull()?.toLong() ?: 0L
                            
                            // Try to get pre-calculated percentage from yt-dlp
                            val percentStr = data["percent"]?.toString()?.trim()
                            val percentVal = percentStr?.toFloatOrNull()
                            
                            val progress = if (percentVal != null) {
                                percentVal / 100f
                            } else if (total > 0) {
                                downloaded.toFloat() / total.toFloat()
                            } else {
                                0f
                            }
                            
                            val speed = data["speed"]?.toString() ?: "0"
                            onProgress(progress, speed)
                        }
                    } catch (e: Exception) {
                        Log.e("YtDlpBridge", "Error in progress callback", e)
                    }
                }
            }
            
            val result = ytdlpModule.callAttr("download_video", url, outputPath, formatId, callback)
            val jsonResult = result.toString()
            Log.d("YtDlpBridge", "Download result: $jsonResult")
            
            // Parse JSON response
            val jsonObject = JSONObject(jsonResult)
            val status = jsonObject.getString("status")
            
            if (status == "success") {
                val path = jsonObject.optString("path", outputPath)
                Result.success(path)
            } else {
                val message = jsonObject.optString("message", "Unknown error")
                Log.e("YtDlpBridge", "Download failed: $message")
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Log.e("YtDlpBridge", "Download exception", e)
            Result.failure(e)
        }
    }

    interface ProgressCallback {
        fun onProgress(data: Map<String, Any?>)
    }
}
