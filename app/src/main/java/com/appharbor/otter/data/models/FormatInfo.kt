package com.appharbor.otter.data.models

import kotlinx.serialization.Serializable

@Serializable
data class FormatInfo(
    val format_id: String? = null,
    val ext: String? = null,
    val resolution: String? = null,
    val filesize: Long? = null,
    val vcodec: String? = null,
    val acodec: String? = null,
    val url: String? = null
)
