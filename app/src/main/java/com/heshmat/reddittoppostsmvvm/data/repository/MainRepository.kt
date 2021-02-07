package com.heshmat.reddittoppostsmvvm.data.repository

import com.heshmat.reddittoppostsmvvm.data.api.ApiHelper
import com.heshmat.reddittoppostsmvvm.data.model.RedditPosts
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

class MainRepository(private val apiHelper: ApiHelper) {


    fun getRedditPosts(): Single<RedditPosts> {
        return apiHelper.getRedditPosts()
    }
    fun getDataBefore(before: String, limit: Int): Single<RedditPosts>{
        return apiHelper.getDataBefore(before,limit)
    }
    fun getDataAfter(after: String, limit: Int): Single<RedditPosts> {
        return apiHelper.getDataAfter(after, limit)
    }


}