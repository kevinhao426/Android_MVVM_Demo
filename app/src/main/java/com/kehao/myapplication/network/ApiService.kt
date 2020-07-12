package com.kehao.myapplication.network

import com.kehao.myapplication.model.MovieResults
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface ApiService {

    @GET("/svc/movies/v2/reviews/search.json")
    suspend fun getMovies(
        @Query("api-key") key: String,
        @Query("offset") offset: Int
    ): MovieResults
}