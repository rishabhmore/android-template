package com.wednesday.template.repo.lastfm.models

import com.wednesday.template.service.lastfm.remote.RemoteAlbum
import com.wednesday.template.service.lastfm.remote.RemoteAlbumResults
import com.wednesday.template.service.lastfm.remote.RemoteImageData
import com.wednesday.template.service.lastfm.remote.RemoteMatches
import com.wednesday.template.service.lastfm.remote.RemoteResults

val remoteAlbum = RemoteAlbum(
    artist = "Various Artists",
    image = listOf(
        RemoteImageData("small", "https://lastfm.freetls.fastly.net/i/u/34s/3690b31930c89a468cb77b7d87320f58.png"),
        RemoteImageData("medium", "https://lastfm.freetls.fastly.net/i/u/64s/3690b31930c89a468cb77b7d87320f58.png"),
        RemoteImageData("large", "https://lastfm.freetls.fastly.net/i/u/174s/3690b31930c89a468cb77b7d87320f58.png"),
        RemoteImageData("extralarge", "https://lastfm.freetls.fastly.net/i/u/300x300/3690b31930c89a468cb77b7d87320f58.png")
    ),
    name = "Magnatron",
    url = "https://www.last.fm/music/Various+Artists/Magnatron",
)

val remoteMatches = RemoteMatches(
    listOf(remoteAlbum)
)

val remoteResults = RemoteResults(remoteMatches, "50", "0", "536")

val remoteAlbumResults = RemoteAlbumResults(
    remoteResults
)
