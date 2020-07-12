package com.kehao.myapplication.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kehao.myapplication.network.NetworkService
import com.kehao.myapplication.network.LoadState
import com.kehao.myapplication.utils.SharedPreferencesHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val loadState = MutableLiveData<LoadState>()

    val spHelper = SharedPreferencesHelper(application)

    fun ViewModel.launchRetrofit(
        block: suspend CoroutineScope.() -> Unit,
        onError: (e: Throwable) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                run {
                    onError(throwable)
                }
            }
        ) {
            try {
                block.invoke(this)
            } finally {
                onComplete()
            }
        }
    }
}