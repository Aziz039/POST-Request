package com.example.postrequest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultModel {
    var data: List<ResultValue>? = null
    class ResultValue {

        @SerializedName("pk")
        var pk: Int? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("location")
        var location: String? = null

        constructor(pk: Int?, name: String?, location: String?) {
            this.pk = pk
            this.name = name
            this.location = location
        }
    }
}
