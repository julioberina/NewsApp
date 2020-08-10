package com.example.newsapp.interfaces

import com.example.newsapp.data.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

public interface NewsService {
    @GET("v2/everything")
    suspend fun getNews(@HeaderMap headers: Map<String, String>,
                        @Query("q") query: String,
                        @Query("pageSize") pageSize: Int,
                        @Query("page") page: Int): News

}