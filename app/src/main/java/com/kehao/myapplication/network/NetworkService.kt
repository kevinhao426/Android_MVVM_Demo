package com.kehao.myapplication.network

import com.kehao.myapplication.BuildConfig

object NetworkService {
    // 请求根地址
    private const val BASE_URL = BuildConfig.ROOT_URL

    // 接口API服务(挂起)
    val api by lazy { ApiFactory.createService(BASE_URL, ApiService::class.java) }
}