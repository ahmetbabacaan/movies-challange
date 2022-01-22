package com.ahmetbabacan.movies.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import com.ahmetbabacan.movies.data.models.Movie
import com.ahmetbabacan.movies.data.responses.DetailResponse
import com.ahmetbabacan.movies.util.Constants.baseImageUrl


object ViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        if (!text.isNullOrEmpty()) {
            Toast.makeText(view.context, text, Toast.LENGTH_LONG).show()
        }
    }


    @JvmStatic
    @BindingAdapter("imageUrl", "placeHolderDrawable", requireAll = false)
    fun loadPoster(view: ImageView, imageUrl: String?, @DrawableRes placeHolderDrawable: Int?) {
        view.load(baseImageUrl + imageUrl) {
            placeHolderDrawable?.let {
                placeholder(it)
                error(it)
                fallback(it)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("backgroundImageUrl")
    fun loadBackground(view: ImageView, imageUrl: String?) {
        view.load(baseImageUrl + imageUrl) {
            crossfade(true)
        }
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("title")
    fun bindTitle(view: TextView, movie: Movie) {
        if (movie.release_date != null) {
            view.text = "${movie.title} (${movie.release_date.substring(0, 4)})"
        } else {
            view.text = movie.title
        }
    }

    @JvmStatic
    @BindingAdapter("titleDetail")
    fun bindTitleWithDetailResponse(view: TextView, movie: DetailResponse) {
        if (movie.release_date != null) {
            view.text = "${movie.title} (${movie.release_date.substring(0, 4)})"
        } else {
            view.text = movie.title
        }
    }
}
