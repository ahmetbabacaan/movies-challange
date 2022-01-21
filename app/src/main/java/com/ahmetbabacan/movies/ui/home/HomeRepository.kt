package com.ahmetbabacan.movies.ui.home

import androidx.annotation.WorkerThread
import com.ahmetbabacan.movies.data.responses.ListResponse
import com.ahmetbabacan.movies.network.MoviesService
import com.ahmetbabacan.movies.network.NetworkRepository
import com.ahmetbabacan.movies.network.NetworkResult
import com.ahmetbabacan.movies.util.Constants
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HomeRepository @Inject constructor(private val moviesService: MoviesService) :
    NetworkRepository {

    @WorkerThread
    fun getNowPlaying(
        page: Int,
        onStart: () -> Unit,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) = flow<ListResponse> {

        val result: NetworkResult<ListResponse> = wrapNetworkResult(call = {
            moviesService.nowPlaying(page = page)
        })

        when (result) {
            is NetworkResult.SuccessfulNetworkResult -> {
                emit(result.body)
                onSuccess()
            }
            is NetworkResult.EmptyNetworkResult -> onError(result.customErrorMessage)
            is NetworkResult.ErrorNetworkResult -> {
                try {
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter: JsonAdapter<ListResponse> =
                        moshi.adapter(ListResponse::class.java)
                    jsonAdapter.fromJson(result.errorMessage)?.let { emit(it) }

                } catch (e: Exception) {
                    onError(result.errorMessage)
                }
            }
            is NetworkResult.ExceptionResult -> onError(result.errorMessage)
        }

    }.onStart { onStart() }.flowOn(Dispatchers.IO)



    @WorkerThread
    fun getUpcomingMovies(
        onStart: () -> Unit,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) = flow<ListResponse> {

        val result: NetworkResult<ListResponse> = wrapNetworkResult(call = {
            moviesService.upcoming()
        })

        when (result) {
            is NetworkResult.SuccessfulNetworkResult -> {
                emit(result.body)
                onSuccess()
            }
            is NetworkResult.EmptyNetworkResult -> onError(result.customErrorMessage)
            is NetworkResult.ErrorNetworkResult -> {
                try {
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter: JsonAdapter<ListResponse> =
                        moshi.adapter(ListResponse::class.java)
                    jsonAdapter.fromJson(result.errorMessage)?.let { emit(it) }

                } catch (e: Exception) {
                    onError(result.errorMessage)
                }
            }
            is NetworkResult.ExceptionResult -> onError(result.errorMessage)
        }

    }.onStart { onStart() }.flowOn(Dispatchers.IO)
}