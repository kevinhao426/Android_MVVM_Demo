package com.kehao.myapplication.network

import com.kehao.myapplication.BuildConfig
import com.kehao.myapplication.utils.LogUtil
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {
    private val loggingInterceptor: Interceptor by lazy { LoggingInterceptor() }

    private val mClient: OkHttpClient by lazy { newClient() }

    fun <T> createService(baseUrl: String, clazz: Class<T>): T =
        Retrofit.Builder().baseUrl(baseUrl).client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(clazz)

    private fun newClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(10, TimeUnit.SECONDS)
        writeTimeout(10, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
    }.build()

    private class LoggingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = StringBuilder()
            val startTime = System.nanoTime()
            val response: Response = with(chain.request()) {
                builder.append(method + "\n")
                builder.append("Sending request\n$url")
                if (method == "POST") {
                    builder.append("?")
                    when (val body = body) {
                        is FormBody -> {
                            for (j in 0 until body.size) {
                                builder.append(body.name(j) + "=" + body.value(j))
                                if (j != body.size - 1) {
                                    builder.append("&")
                                }
                            }
                        }
//                        is MultipartBody -> {}
                    }
                }
                builder.append("\n").append(headers)
                LogUtil.d(builder.toString())
                chain.proceed(this)
            }
            builder.clear()
            builder.append("Received response in " + (System.nanoTime() - startTime) / 1e6 + "ms\n")
            builder.append("code" + response.code + "\n")
            LogUtil.d(builder.toString())
            return response
        }
    }
}