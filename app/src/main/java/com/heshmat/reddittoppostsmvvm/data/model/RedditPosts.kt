package com.heshmat.reddittoppostsmvvm.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RedditPosts {
    @Expose
    @SerializedName("data")
    var data: Data? = null
    @Expose
    @SerializedName("kind")
    var kind: String? = null

}