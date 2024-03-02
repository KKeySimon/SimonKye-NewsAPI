package com.example.simonkye_newsapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("top-headlines")
    fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("category") from: String,
        @Query("country") country: String
    ): Call<NewsType>
}