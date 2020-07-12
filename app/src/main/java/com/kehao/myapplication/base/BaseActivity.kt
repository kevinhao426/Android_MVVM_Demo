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
//    private lateinit var googleFitTools: GoogleFitTools

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

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when {
//            grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
//                initGoogleFit()
//            }
//            else -> {
//                Timber.d("Permission ACTIVITY_RECOGNITION denied")
//            }
//        }
//    }

//    fun checkAndInitGoogleFit(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1
//            )
//        } else {
//            initGoogleFit()
//        }
//    }

//    private fun initGoogleFit() {
//        googleFitTools = GoogleFitTools(this)
//        googleFitTools.checkSignInForFitData(FitActionRequestCode.SUBSCRIBE)
//        googleFitTools.checkSignInForFitData(FitActionRequestCode.READ_STEP)
//        googleFitTools.checkSignInForFitData(FitActionRequestCode.READ_DISTANCE)
//        lifecycle.addObserver(googleFitTools)
//        googleFitTools.todayStepsUpdateEvent.observe(this, Observer {
//            it.let {
//                Timber.d("receive step data: %s", it)
//            }
//        })
//        googleFitTools.todayDistanceUpdateEvent.observe(this, Observer {
//            it.let {
//                Timber.d("receive distance data: %s", it)
//            }
//        })
//    }


}