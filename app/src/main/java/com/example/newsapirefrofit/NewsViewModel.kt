package com.example.newsapirefrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapirefrofit.models.Article
import com.example.newsapirefrofit.models.NewsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
val newsRepository: NewsRepository
): ViewModel() {

    ///get breaking news
    val bresingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1
    ///search
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1



    init {
        getBreakingNews("us")
    }

    ///breaking news
    fun getBreakingNews(countyCode: String) = viewModelScope.launch {
        bresingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countyCode, breakingNewsPage)
        bresingNews.postValue(HandleBreakingNewsResponse(response))
    }

    ///search query
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    //breaking news
    private fun HandleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful){
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    //// search query
    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    /////room database

    fun saveArticel(articel: Article)  = viewModelScope.launch {
        newsRepository.insert(articel)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteAricel(articel: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(articel)
    }

}