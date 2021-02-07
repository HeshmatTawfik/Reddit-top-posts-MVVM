package com.heshmat.reddittoppostsmvvm.data.api


class ApiHelper(private val apiService: ApiService) {

  fun  getRedditPosts()=apiService.getRedditPosts()
  fun  getDataBefore(before:String,limit:Int)=apiService.getDataBefore(before,limit)
  fun  getDataAfter(after:String,limit: Int)=apiService.getDataAfter(after,limit)
}

