package com.heshmat.reddittoppostsmvvm.data.api


import com.heshmat.reddittoppostsmvvm.data.model.RedditPosts
import com.heshmat.reddittoppostsmvvm.retrofit.RetrofitClient
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

class ApiServiceImpl: ApiService {
    override fun getRedditPosts(): Single<RedditPosts> {
        return RetrofitClient.apiInterface.getRedditPosts()

    }

    override fun getDataBefore(before: String, limit: Int): Single<RedditPosts> {
        return RetrofitClient.apiInterface.getDataBefore(before, limit)
    }

    override fun getDataAfter(after: String, limit: Int): Single<RedditPosts> {
        return RetrofitClient.apiInterface.getDataAfter(after, limit)
    }


}