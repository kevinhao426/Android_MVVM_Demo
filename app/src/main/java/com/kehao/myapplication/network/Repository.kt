package com.kehao.myapplication.network

import com.kehao.myapplication.BuildConfig
import com.kehao.myapplication.model.MovieResults

object Repository {
    suspend fun getMovieList(offset: Int): MovieResults =
        NetworkService.api.getMovies(BuildConfig.API_KEY, offset)
}