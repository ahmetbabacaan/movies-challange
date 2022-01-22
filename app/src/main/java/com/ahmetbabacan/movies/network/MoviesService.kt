package com.ahmetbabacan.movies.network

import com.ahmetbabacan.movies.data.responses.DetailResponse
import com.ahmetbabacan.movies.data.responses.ListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MoviesService {

    @GET("movie/{movieId}")
    suspend fun detail(
        @Path("movieId") movieId: Int
    ): Response<DetailResponse>

    @GET("movie/upcoming")
    suspend fun upcoming(): Response<ListResponse>

    @GET("movie/now_playing")
    suspend fun nowPlaying(@Query("page") page: Int): Response<ListResponse>

}
