package com.ahmetbabacan.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.ahmetbabacan.movies.MainCoroutinesRule
import com.ahmetbabacan.movies.MockUtil
import com.ahmetbabacan.movies.data.responses.ListResponse
import com.ahmetbabacan.movies.network.MoviesService
import com.ahmetbabacan.movies.ui.home.HomeRepository
import com.ahmetbabacan.movies.ui.home.HomeViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var homeRepository: HomeRepository
    private val moviesService: MoviesService = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        homeRepository = HomeRepository(moviesService)
        viewModel = HomeViewModel(homeRepository)
    }

    @Test
    fun nowPlayingTest() = runBlocking {
        val mockData = MockUtil.mockListResponse()
        whenever(moviesService.nowPlaying(1)).thenReturn(Response.success(mockData))

        val observer: Observer<ListResponse> = mock()
        val fetchedData: LiveData<ListResponse> =
            homeRepository.getNowPlaying(
                page = 1,
                onStart = {},
                onSuccess = {},
                onError = {}).asLiveData()
        fetchedData.observeForever(observer)

        viewModel.fetchNowPlayingMovies()
        delay(500L)

        verify(observer).onChanged(mockData)
        fetchedData.removeObserver(observer)
    }

    @Test
    fun upcomingTest() = runBlocking {
        val mockData = MockUtil.mockListResponse()
        whenever(moviesService.upcoming()).thenReturn(Response.success(mockData))

        val observer: Observer<ListResponse> = mock()
        val fetchedData: LiveData<ListResponse> =
            homeRepository.getUpcomingMovies(
                onStart = {},
                onSuccess = {},
                onError = {}).asLiveData()
        fetchedData.observeForever(observer)

        viewModel.fetchNowPlayingMovies()
        delay(500L)

        verify(observer).onChanged(mockData)
        fetchedData.removeObserver(observer)
    }


}
