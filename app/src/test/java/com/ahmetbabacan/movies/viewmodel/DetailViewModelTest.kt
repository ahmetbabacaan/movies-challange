package com.ahmetbabacan.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.ahmetbabacan.movies.MainCoroutinesRule
import com.ahmetbabacan.movies.MockUtil
import com.ahmetbabacan.movies.data.responses.DetailResponse
import com.ahmetbabacan.movies.network.MoviesService
import com.ahmetbabacan.movies.ui.detail.DetailRepository
import com.ahmetbabacan.movies.ui.detail.DetailViewModel
import com.ahmetbabacan.movies.util.Constants
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
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var detailRepository: DetailRepository
    private val moviesService: MoviesService = mock()
    private var handle: SavedStateHandle = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        detailRepository = DetailRepository(moviesService)
        val savedStateHandle = SavedStateHandle()
        savedStateHandle.set(Constants.argKeyMovie, MockUtil.mockMovie())
        viewModel = DetailViewModel(detailRepository, savedStateHandle)
    }

    @Test
    fun fetchMovieDetailTest() = runBlocking {
        val mockData = MockUtil.mockDetailResponse()
        whenever(moviesService.detail(634649)).thenReturn(Response.success(mockData))

        val observer: Observer<DetailResponse> = mock()
        val fetchedData: LiveData<DetailResponse> =
            detailRepository.detail(
                movieId = 634649,
                onStart = {},
                onSuccess = {},
                onError = {}).asLiveData()
        fetchedData.observeForever(observer)

        viewModel.fetchMovieDetail()
        delay(500L)

        verify(observer).onChanged(mockData)
        fetchedData.removeObserver(observer)
    }


}
