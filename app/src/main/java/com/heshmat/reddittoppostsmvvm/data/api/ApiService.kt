package com.heshmat.reddittoppostsmvvm.data.api

import com.heshmat.reddittoppostsmvvm.data.model.RedditPosts
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("top.json?limit=4")
    fun getRedditPosts(): Single<RedditPosts>

}