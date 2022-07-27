package com.wednesday.template.service.lastfm.remote


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteImageData(
    @SerialName("size")
    val size: String?,
    @SerialName("#text")
    val text: String?
)