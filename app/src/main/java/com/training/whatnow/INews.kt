package com.training.whatnow

import retrofit2.Call
import retrofit2.http.GET

//Details of Request
interface INews {
    @GET("/v2/top-headlines?country=us&category=general&apiKey=a894df1ef11f4c25b9951037b52924e4&pageSize=30")
    fun getNews(): Call<News>

}