package com.heshmat.reddittoppostsmvvm.data.api


import com.heshmat.reddittoppostsmvvm.data.model.RedditPosts
import com.heshmat.reddittoppostsmvvm.retrofit.RetrofitClient
import io.reactivex.Single

class ApiServiceImpl: ApiService {
    override fun getRedditPosts(): Single<RedditPosts> {
        return RetrofitClient.apiInterface.getRedditPosts()

    }
}