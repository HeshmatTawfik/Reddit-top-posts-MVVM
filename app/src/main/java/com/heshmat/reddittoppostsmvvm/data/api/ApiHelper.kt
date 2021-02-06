package com.heshmat.reddittoppostsmvvm.data.api


class ApiHelper(private val apiService: ApiService) {

    fun  getRedditPosts()=apiService.getRedditPosts()
}

