package com.magdi.msplash.data

import com.magdi.msplash.network.response.PhotosResponse
import com.magdi.msplash.network.response.Urls
import org.junit.Assert.*
import org.junit.Test
import java.lang.NullPointerException

class PhotoMapperTest {

    @Test
    fun `map returns the correct object`() {
        val photoResponse = PhotosResponse(
            id = "1",
            width = 100,
            height = 100,
            description = "desc",
            color = "#1234",
            alt_description = "alt_desc",
            urls = Urls(
                regular = "regular_url"
            )
        )

        val expectedPhoto = Photo(
            id = "1",
            width = 100,
            height = 100,
            description = "desc",
            color = "#1234",
            url = "regular_url"
        )

        val mapper = PhotoMapper()
        assertEquals(expectedPhoto,  mapper.map(photoResponse))
    }

    @Test
    fun `map throws NullPointerException if the data is broken`() {
        val photoResponse = PhotosResponse(
            width = 100,
            height = 100,
        )

        val mapper = PhotoMapper()
        assertThrows(NullPointerException::class.java) {
            mapper.map(photoResponse)
        }

    }
}