package com.appharbor.otter.data.python

import com.appharbor.otter.data.models.VideoInfo
import com.chaquo.python.PyObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

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
            VideoInfo(error = e.message)
        }
    }

    suspend fun downloadVideo(
        url: String,
        outputPath: String,
        formatId: String? = null,
        onProgress: (Float, String) -> Unit
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val callback = object : ProgressCallback {
                override fun onProgress(data: Map<String, Any?>) {
                    val status = data["status"]?.toString()
                    if (status == "downloading") {
                        val downloaded = data["downloaded_bytes"]?.toString()?.toLongOrNull() ?: 0L
                        val total = data["total_bytes"]?.toString()?.toLongOrNull() ?: 1L
                        val progress = if (total > 0) downloaded.toFloat() / total.toFloat() else 0f
                        val speed = data["speed"]?.toString() ?: "0"
                        onProgress(progress, speed)
                    }
                }
            }
            
            val result = ytdlpModule.callAttr("download_video", url, outputPath, formatId, callback)
            val jsonResult = result.toString()
            jsonResult.contains("success")
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    interface ProgressCallback {
        fun onProgress(data: Map<String, Any?>)
    }
}
