package com.example.newsapp.interfaces

import com.example.newsapp.data.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface NewsService {
    @GET("v2/everything")
    fun getNews(@Query("q") query: String,
                @Query("pageSize") pageSize: Int,
                @Query("page") page: Int): Call<News>

}