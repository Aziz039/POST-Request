package com.example.postrequest

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getUser(): Call<List<ResultModel.ResultValue>>

    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUser(@Body userData: ResultModel.ResultValue): Call<ResultModel.ResultValue>

    @Headers("Content-Type: application/json")
    @PUT("/test/{id}")
    fun updateUser(@Path("id") id: Int, @Body userData: ResultModel.ResultValue): Call<ResultModel.ResultValue>

    @Headers("Content-Type: application/json")
    @DELETE("/test/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>
}