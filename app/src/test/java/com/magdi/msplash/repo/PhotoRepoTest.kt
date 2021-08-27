package com.magdi.msplash.repo

import com.magdi.msplash.data.Photo
import com.magdi.msplash.network.SplashAPI
import com.magdi.msplash.network.response.PhotosResponse
import com.magdi.msplash.network.response.Urls
import com.magdi.msplash.utils.MainCoroutineRule
import com.magdi.msplash.utils.Results
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import java.io.IOException

class PhotoRepoTest {

    private lateinit var repo: PhotoRepo

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var api: SplashAPI

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = PhotoRepo(api, PhotoMapper())

    }

    @Test
    fun `getPhotos returns loading when starting`() = runBlocking(Dispatchers.Main) {
        coEvery { api.loadPhotos() } returns listOf()

        assertTrue(repo.getPhotos(params).first() is Results.Loading)
    }

    @Test
    fun `getPhotos returns error if exception was caught`() = runBlocking(Dispatchers.Main) {
        coEvery { api.loadPhotos() } throws IOException()

        repo.getPhotos(params).dropWhile { it is Results.Loading }.collect {
            assertTrue(it is Results.Error)
        }

        coVerify { api.loadPhotos() }
    }

    @Test
    fun `getPhotos returns success when result is received`() = runBlocking(Dispatchers.Main) {
        coEvery { api.loadPhotos() } returns listOf()

        repo.getPhotos(params).dropWhile { it is Results.Loading }.collect {
            assertTrue(it is Results.Success)
        }

        coVerify { api.loadPhotos() }
    }

    @Test
    fun `getPhotos returns mapped photos`() = runBlocking(Dispatchers.Main) {
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

        coEvery { api.loadPhotos() } returns listOf(photoResponse)

        repo.getPhotos(params).dropWhile { it is Results.Loading }.collect {
            assertEquals(listOf(expectedPhoto), it.data)
        }

        coVerify { api.loadPhotos() }
    }

    @Test
    fun `getPhotos returns error if mapping crashed`() = runBlocking(Dispatchers.Main) {
        val photoResponse = PhotosResponse(
            id = "1",
            width = 100,
            height = 100,
        )

        coEvery { api.loadPhotos() } returns listOf(photoResponse)

        repo.getPhotos(params).dropWhile { it is Results.Loading }.collect {
            assertTrue(it is Results.Error)
        }

        coVerify { api.loadPhotos() }
    }
}