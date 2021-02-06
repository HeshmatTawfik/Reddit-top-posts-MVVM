package com.heshmat.reddittoppostsmvvm.data.repository

import com.heshmat.reddittoppostsmvvm.data.api.ApiHelper
import com.heshmat.reddittoppostsmvvm.data.model.RedditPosts
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {


    fun getRedditPosts(): Single<RedditPosts> {
        return apiHelper.getRedditPosts()
    }

}