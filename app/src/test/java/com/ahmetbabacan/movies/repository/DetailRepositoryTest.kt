@file:Suppress("SpellCheckingInspection")

package com.ahmetbabacan.movies.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.ahmetbabacan.movies.MainCoroutinesRule
import com.ahmetbabacan.movies.MockUtil.mockDetailResponse
import com.ahmetbabacan.movies.network.MoviesService
import com.ahmetbabacan.movies.ui.detail.DetailRepository
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
class DetailRepositoryTest {

    private lateinit var repository: DetailRepository
    private val service: MoviesService = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        repository = DetailRepository(service)
    }


    @Test
    fun fetchMovieDetailTest() = runBlocking {
        val mockData = mockDetailResponse()
        whenever(service.detail(634649)).thenReturn(Response.success(mockData))

        repository.detail(
            movieId = 634649,
            onStart = {},
            onSuccess = {},
            onError = {}
        ).test {
            assertEquals(expectItem().runtime, 148)
            expectComplete()
        }
    }

}
