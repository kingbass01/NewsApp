package com.ruiaaryan.newsapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class APIService {
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"

        private val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory
                    .create(moshi)
            )
            .build()
    }

    interface APIService {
        @GET("top-headlines")
        fun getTopHeadlines(@Query("country") country:String,@Query("q") search:String,@Query("apiKey") key:String) : Call<APIResponse>
    }

    object APIObject{
        val retrofitService : APIService by lazy {
            retrofit.create(APIService::class.java) }
    }
}