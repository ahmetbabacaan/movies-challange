package com.ahmetbabacan.movies.ui.home

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.ahmetbabacan.movies.R
import com.ahmetbabacan.movies.data.models.Movie
import com.ahmetbabacan.movies.data.responses.ListResponse
import com.ahmetbabacan.movies.util.plusAssign
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BindingViewModel() {

    val nowPlayingResponse: LiveData<ListResponse>
    var nowPlayingLiveData: MutableLiveData<MutableList<Movie>> =
        MutableLiveData<MutableList<Movie>>()

    val upcomingResponse: LiveData<ListResponse>
    val upcomingLiveData: MutableLiveData<List<Movie>> =
        MutableLiveData<List<Movie>>()

    val currentPage: MutableStateFlow<Int> = MutableStateFlow(1)
    val bannerTrigger: MutableStateFlow<Boolean> = MutableStateFlow(false)

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var isError: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var errorText: Int by bindingProperty(R.string.error_no_movie_found)
        private set

    @get:Bindable
    var toast: String? by bindingProperty(null)
        private set

    init {
        upcomingResponse = bannerTrigger.asLiveData().switchMap {
            homeRepository.getUpcomingMovies(
                onStart = { isLoading = true },
                onSuccess = { isLoading = false },
                onError = { toast = it }
            ).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
        }
        nowPlayingResponse = currentPage.filter { it != 0 }.asLiveData().switchMap { page ->
            homeRepository.getNowPlaying(
                page = page,
                onStart = { isLoading = true },
                onSuccess = { isLoading = false },
                onError = { toast = it }
            ).asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
        }

        viewModelScope.launch {
            nowPlayingResponse.asFlow().collect {
                if (it.results.isNullOrEmpty()) {
                    if (it.status_message != null) {
                        toast = it.status_message.toString()
                        notifyPropertyChanged(::toast)
                    } else {
                        errorText = R.string.error_no_movie_found
                        isError = true
                    }
                    currentPage.value = 0
                } else {
                    isError = false
                    if (currentPage.value == 1) {
                        nowPlayingLiveData.setValue(it.results.toMutableList())
                    } else {
                        nowPlayingLiveData += it.results
                    }
                }
            }

            upcomingResponse.asFlow().collect {
                if (!it.results.isNullOrEmpty()) {
                    upcomingLiveData.value = it.results
                }
            }
        }
    }

    fun isOnLast(): Boolean {
        return nowPlayingResponse.value?.page == nowPlayingResponse.value?.total_pages
    }

    @MainThread
    fun fetchNowPlayingMovies() {
        if (!isLoading) {
            currentPage.value = currentPage.value + 1
        }
    }

    fun refreshData() {
        currentPage.value = 0
        currentPage.value = 1
        bannerTrigger.value = !bannerTrigger.value
    }
}