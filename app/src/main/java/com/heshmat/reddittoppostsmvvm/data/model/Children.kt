package com.heshmat.reddittoppostsmvvm.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


class Children {
    @Expose
    @SerializedName("data")
    var data: Data? = null
    @Expose
    @SerializedName("kind")
    var kind: String? = null

    constructor(data: Data?, kind: String?) {
        this.data = data
        this.kind = kind
    }



    override fun toString(): String {
        return "Children{" +
                "data=" + data +
                ", kind='" + kind + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Children) return false
        val children = o
        return data == children.data && kind == children.kind
    }

    override fun hashCode(): Int {
        return Objects.hash(data, kind)
    }

}
