package com.kehao.myapplication.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kehao.myapplication.base.BaseViewModel
import com.kehao.myapplication.model.AppDataBase
import com.kehao.myapplication.model.Movie
import com.kehao.myapplication.network.LoadState
import com.kehao.myapplication.network.Repository
import com.kehao.myapplication.utils.LogUtil
import com.kehao.myapplication.utils.showShortToast
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : BaseViewModel(application) {
    private val dao = AppDataBase(getApplication()).moviesDao()
    val movieData = MutableLiveData<List<Movie>>()
    val movie = MutableLiveData<Movie>()

    val allMovies = LivePagedListBuilder(
        dao.getAllMovies(), PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .build()
    ).setBoundaryCallback(MovieListBoundaryCallBack(this))
        .build()

    fun callApiForData(offset: Int) = launchRetrofit(
        {
            loadState.value = LoadState.Loading()
            showShortToast("Start fetching From server", getApplication())
            movieData.value = Repository.getMovieList(offset).results
            showShortToast("Fetching Completed", getApplication())
            insertToLocalDB(movieData.value!!)
            LogUtil.d(movieData.value.toString())
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )

    fun insertToLocalDB(movieList: List<Movie>) = viewModelScope.launch {
        showShortToast("Insert to local db", getApplication())
        dao.insertAllMovies(*movieList.toTypedArray())
    }

    fun getMovieItem(movieId : Int) = viewModelScope.launch {
        movie.value = dao.getMovie(movieId)
    }

    fun refreshAll() = viewModelScope.launch {
        loadState.value = LoadState.Loading()
        showShortToast("database cleared for refresh all", getApplication())
        dao.deleteAllMovies()
        callApiForData(0)
    }

    class MovieListBoundaryCallBack(val viewmodel: MainActivityViewModel) :
        PagedList.BoundaryCallback<Movie>() {

        override fun onZeroItemsLoaded() = viewmodel.callApiForData(0)


        override fun onItemAtEndLoaded(itemAtEnd: Movie) =
            viewmodel.callApiForData(viewmodel.allMovies.value?.size ?: 0)

    }
}