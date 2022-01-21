package com.ahmetbabacan.movies.util

import androidx.lifecycle.MutableLiveData
import com.ahmetbabacan.movies.data.models.Movie

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}