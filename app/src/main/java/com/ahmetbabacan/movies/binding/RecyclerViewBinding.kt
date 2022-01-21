package com.ahmetbabacan.movies.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmetbabacan.movies.data.models.Movie
import com.ahmetbabacan.movies.ui.adapters.MovieAdapter
import com.ahmetbabacan.movies.ui.home.HomeViewModel
import com.ahmetbabacan.movies.util.RecyclerViewPaginator

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        }
    }

    @JvmStatic
    @BindingAdapter("paginator")
    fun pagination(view: RecyclerView, viewModel: HomeViewModel) {
        RecyclerViewPaginator(
            recyclerView = view,
            isLoading = { viewModel.isLoading },
            loadMore = { viewModel.fetchNowPlayingMovies() },
            onLast = { viewModel.isOnLast() }
        )
    }

    @JvmStatic
    @BindingAdapter("adapterMovieList")
    fun bindAdapterMovieList(view: RecyclerView, movieList: List<Movie>?) {
        if (!movieList.isNullOrEmpty()) {
            (view.adapter as MovieAdapter).setMovieList(movieList)
        }
    }
}
