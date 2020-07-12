package com.kehao.myapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.kehao.myapplication.utils.LogUtil
import com.kehao.myapplication.utils.askForInternet
import com.kehao.myapplication.utils.isConnective
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : BaseViewModel, VDB : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: VDB
    lateinit var viewModel: VM

    abstract fun getContentViewId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(javaClass.simpleName)
        binding = DataBindingUtil.setContentView(this, getContentViewId())
        binding.lifecycleOwner = this
        createViewBinding()
        if (!isConnective(this)) {
            askForInternet(this)
        }
        observeData()
        handleEvent()
    }

    abstract fun observeData()
    abstract fun handleEvent()

    @Suppress("UNCHECKED_CAST")
    private fun createViewBinding() {
        val modelClass: Class<*>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<*>
        } else {
            BaseViewModel::class.java
        }
        viewModel =
            ViewModelProvider(this).get<BaseViewModel>(modelClass as Class<BaseViewModel>) as VM
    }
}