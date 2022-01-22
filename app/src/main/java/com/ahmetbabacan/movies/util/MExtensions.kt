package com.ahmetbabacan.movies.util

import androidx.lifecycle.MutableLiveData
import com.ahmetbabacan.movies.data.models.Movie
import com.ahmetbabacan.movies.data.responses.DetailResponse


fun Movie.toMovieDetailModel(): DetailResponse {

    return DetailResponse(
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        belongs_to_collection = null,
        budget = null,
        genres = null,
        homepage = null,
        id = this.id,
        imdb_id = null,
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        production_companies = null,
        production_countries = null,
        release_date = this.release_date,
        revenue = null,
        runtime = null,
        spoken_languages = null,
        status = null,
        tagline = null,
        title, video, vote_average, vote_count
    )
}

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}