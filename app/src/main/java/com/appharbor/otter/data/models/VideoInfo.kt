package com.appharbor.otter.data.models

import kotlinx.serialization.Serializable

@Serializable
data class VideoInfo(
    val title: String? = null,
    val thumbnail: String? = null,
    val duration: Double? = null,
    val uploader: String? = null,
    val view_count: Long? = null,
    val formats: List<FormatInfo> = emptyList(),
    val error: String? = null
)
