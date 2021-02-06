package com.heshmat.reddittoppostsmvvm.retrofit


import com.heshmat.reddittoppostsmvvm.data.api.ApiService
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val baseUrl = "https://www.reddit.com/"

    private val retrofitClient: Retrofit.Builder by lazy {

        val okhttpClient = OkHttpClient.Builder()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    val apiInterface: ApiService by lazy {
        retrofitClient
            .build()
            .create(ApiService::class.java)
    }
}