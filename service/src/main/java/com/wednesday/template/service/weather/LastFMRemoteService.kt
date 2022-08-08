package com.wednesday.template.service.weather

import com.wednesday.template.service.BuildConfig
import com.wednesday.template.service.lastfm.remote.RemoteAlbumResults
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMRemoteService {

    @GET("/2.0/")
    suspend fun searchAlbums(
        @Query("method") method: String = "album.search",
        @Query("album") album: String,
        @Query("api_key") apiKey: String = BuildConfig.LAST_FM_API_KEY,
        @Query("format") format: String = "json"
    ): RemoteAlbumResults
}
