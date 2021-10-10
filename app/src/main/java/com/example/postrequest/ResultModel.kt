package com.example.postrequest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultModel {
    var data: List<ResultValue>? = null
    class ResultValue {
        @SerializedName("name")
        var name: String? = null

        @SerializedName("location")
        var location: String? = null

        constructor(name: String?, location: String?) {
            this.name = name
            this.location = location
        }
    }
}



//class Users {
//
//    var data: List<UserDetails>? = null
//
//    class UserDetails {
//
//        @SerializedName("name")
//        var name: String? = null
//
//        @SerializedName("location")
//        var location: String? = null
//
//        constructor(name: String?, location: String?) {
//            this.name = name
//            this.location = location
//        }
//    }