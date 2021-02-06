package com.heshmat.reddittoppostsmvvm.data.model

import android.graphics.Bitmap
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


class Data {
    @Expose
    @SerializedName("is_video")
    var isVideo: Boolean
    @Expose
    @SerializedName("children")
    var children: List<Children>? = null
    @Expose
    @SerializedName("thumbnail")
    var thumbnail: String
    @Expose
    @SerializedName("author")
    var author: String=""
    @Expose
    @SerializedName("author_fullname")
    var authorFullname: String
    @Expose
    @SerializedName("num_comments")
    var numComments: Int
    @Expose
    @SerializedName("created_utc")
    var createdUtc: Double
    @Expose
    @SerializedName("created")
    var created: Double
    @Expose
    @SerializedName("title")
    var title: String?
    @Expose
    @SerializedName("permalink")
    var permalink: String
    @Expose
    @SerializedName("name")
    var name: String
    @Expose
    @SerializedName("before")
    var before: String
    @Expose
    @SerializedName("after")
    var after: String
    @Expose
    @SerializedName("url_overridden_by_dest")
    var imgURl: String
    var thumbBitmap: Bitmap? = null

    override fun toString(): String {
        return "Data{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}'
    }



    constructor(
        isVideo: Boolean,
        thumbnail: String?,
        author: String?,
        authorFullname: String?,
        numComments: Int,
        createdUtc: Double,
        created: Double,
        title: String?,
        permalink: String?,
        name: String?,
        before: String?,
        after: String?,
        imgURl: String?
    ) {
        this.isVideo = isVideo
        this.thumbnail = thumbnail.toString()
        this.author = author.toString()
        this.authorFullname = authorFullname.toString()
        this.numComments = numComments
        this.createdUtc = createdUtc
        this.created = created
        this.title = title
        this.permalink = permalink.toString()
        this.name = name.toString()
        this.before = before.toString()
        this.after = after.toString()
        this.imgURl = imgURl.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Data) return false
        val data = o
        return author == data.author && authorFullname == data.authorFullname && title == data.title
    }

    override fun hashCode(): Int {
        return Objects.hash(author, authorFullname, title)
    }



}
