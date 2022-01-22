@file:Suppress("SpellCheckingInspection")

package com.ahmetbabacan.movies.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.ahmetbabacan.movies.MainCoroutinesRule
import com.ahmetbabacan.movies.MockUtil.mockListResponse
import com.ahmetbabacan.movies.network.MoviesService
import com.ahmetbabacan.movies.ui.home.HomeRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class HomeRepositoryTest {

    private lateinit var repository: HomeRepository
    private val service: MoviesService = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        repository = HomeRepository(service)
    }

    @Test
    fun nowPlayingMoviesTest() = runBlocking {
        val mockResponse = mockListResponse()
        whenever(service.nowPlaying(1)).thenReturn(Response.success(mockResponse))
        val mockModel = mockResponse.results!![0]

        repository.getNowPlaying(
            page = 1,
            onStart = {},
            onSuccess = {},
            onError = {}
        ).test {
            assertEquals(expectItem().results?.get(0)?.id, mockModel.id)
            expectComplete()
        }
    }

    @Test
    fun upcomingMoviesTest() = runBlocking {
        val mockResponse = mockListResponse()
        whenever(service.upcoming()).thenReturn(Response.success(mockResponse))
        val mockModel = mockResponse.results!![0]

        repository.getUpcomingMovies(
            onStart = {},
            onSuccess = {},
            onError = {}
        ).test {
            assertEquals(expectItem().results?.get(0)?.id, mockModel.id)
            expectComplete()
        }
    }
}
