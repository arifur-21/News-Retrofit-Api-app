package com.example.newsapirefrofit.models


import com.example.newsapirefrofit.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)