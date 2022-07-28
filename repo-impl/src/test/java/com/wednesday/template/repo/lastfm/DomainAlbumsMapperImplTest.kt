package com.wednesday.template.repo.lastfm

import com.wednesday.template.repo.lastfm.models.album
import com.wednesday.template.repo.lastfm.models.remoteAlbum
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DomainAlbumsMapperImplTest {

    private lateinit var domainAlbumsMapper: DomainAlbumsMapperImpl

    @Before
    fun setup() {
        domainAlbumsMapper = DomainAlbumsMapperImpl()
    }

    @Test
    fun `Given RemoteAlbum, When map is called, Then Album is returned with correct mapping`() {
        //Given
        val remoteData = remoteAlbum

        //When
        val result = domainAlbumsMapper.map(remoteData)

        //Then
        assertEquals(expected = album, actual = result)
    }
}