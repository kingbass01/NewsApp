package com.ruiaaryan.newsapp


import com.squareup.moshi.Json

data class APIResponse(
    @Json(name = "articles")
    val articles: MutableList<Article?>?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "totalResults")
    val totalResults: Int?
)