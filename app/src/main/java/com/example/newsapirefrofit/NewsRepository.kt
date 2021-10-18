package com.example.newsapirefrofit

import com.example.newsapirefrofit.api.NewsAPI
import com.example.newsapirefrofit.api.RetrofitInstance
import com.example.newsapirefrofit.db.ArticleDatabase
import com.example.newsapirefrofit.models.Article

class NewsRepository(
    val db: ArticleDatabase,
) {

    ///get breaking news
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    ///search query
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
            RetrofitInstance.api.searchForNews(searchQuery, pageNumber)


    suspend fun insert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticel(article)
}