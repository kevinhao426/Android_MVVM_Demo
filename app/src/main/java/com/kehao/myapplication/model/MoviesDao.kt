package com.kehao.myapplication.model

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDao {
    @Insert
    suspend fun insertAllMovies(vararg movie: Movie): List<Long>

    @Query("SELECT * FROM movie_list")
    fun getAllMovies(): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movie_list WHERE uuid = :movieId")
    suspend fun getMovie(movieId: Int): Movie

    @Query("DELETE FROM movie_list")
    suspend fun deleteAllMovies()

}