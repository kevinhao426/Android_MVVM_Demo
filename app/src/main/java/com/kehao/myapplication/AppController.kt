package com.kehao.myapplication

import androidx.multidex.MultiDexApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class AppController : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
                .methodCount(0) // (Optional) How many method line to show. Default 2
                .methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
                .tag(
                    applicationInfo.loadLabel(packageManager).toString()
                ) // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        }
    }

    companion object {
        val instance: AppController by lazy {
            AppController()
        }
    }
}