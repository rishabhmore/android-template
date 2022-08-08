package com.wednesday.template.service.lastfm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wednesday.template.service.lastfm.local.LocalAlbum
import com.wednesday.template.service.weather.LastFMLocalService
import kotlinx.coroutines.flow.Flow

@Dao
interface LastFMLocalServiceImpl : LastFMLocalService {

    @Query("Select * from saved_albums")
    override fun getFavouriteAlbumsFlow(): Flow<List<LocalAlbum>>

    @Query("Select * from saved_albums")
    override suspend fun getFavouriteAlbums(): List<LocalAlbum>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun saveAlbumToFavourites(album: LocalAlbum)

    @Delete
    override suspend fun removeAlbumFromFavourites(album: LocalAlbum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun upsertSavedAlbum(album: LocalAlbum)
}
