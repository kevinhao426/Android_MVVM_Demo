package com.kehao.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kehao.myapplication.utils.LogUtil
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding>(val useActivityVM: Boolean) :
        Fragment() {
    lateinit var binding: VDB
    lateinit var viewModel: VM

    abstract fun getContentViewId(): Int

    abstract fun observeData()

    abstract fun handleEvent();

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        LogUtil.d(javaClass.simpleName)
        binding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val modelClass: Class<*>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<*>
        } else {
            BaseViewModel::class.java
        }
        if (useActivityVM) {
            viewModel = requireActivity().run {
                ViewModelProvider(this).get(modelClass as Class<BaseViewModel>) as VM
            }
        } else {
            viewModel =
                    ViewModelProvider(this).get(modelClass as Class<BaseViewModel>) as VM
        }
        observeData()
        handleEvent()
    }
}