package com.heshmat.reddittoppostsmvvm.data.api

import com.heshmat.reddittoppostsmvvm.data.model.RedditPosts
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("top.json?limit=4")
  fun getRedditPosts(): Single<RedditPosts>
  @GET("top.json")
  fun getDataBefore(@Query("before") before: String, @Query("limit") limit: Int): Single<RedditPosts>
  @GET("top.json")
  fun getDataAfter(@Query("after") after: String, @Query("limit") limit: Int): Single<RedditPosts>

}