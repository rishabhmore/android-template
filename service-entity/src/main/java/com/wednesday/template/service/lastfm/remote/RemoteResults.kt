package com.wednesday.template.service.lastfm.remote


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteResults(
    @SerialName("albummatches")
    val matches: RemoteMatches?,
    @SerialName("opensearch:itemsPerPage")
    val itemsPerPage: String?,
    @SerialName("opensearch:startIndex")
    val startIndex: String?,
    @SerialName("opensearch:totalResults")
    val totalResults: String?
)