package com.example.simonkye_newsapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.UUID

class NewsListViewModel : ViewModel() {
    private val _news = MutableStateFlow<List<Article>>(emptyList())
    val news : StateFlow<List<Article>>
        get() = _news.asStateFlow()
    val url : String = "https://newsapi.org/v2/"
    val API_KEY : String = "9a2d9d75d0dc451b9ef749a1e73c1e23"
    val country : String = "us"
    fun getArticleByTitle(title: String): Article? {
        val foundArticle = _news.value.find { it.title == title }
        Log.d("TAGD", "The size of _news in getArticleByTitle() is " + _news.value.size.toString())
        if (foundArticle != null) {
            Log.d("TAG", "Article with title '$title' found.")
        } else {
            Log.d("TAG", "Article with title '$title' not found.")
        }
        return foundArticle
    }
    suspend fun loadNews(category : String) {
        Log.d("TAG", "loadNews Entered")
        val api = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val response = withContext(Dispatchers.IO) {
            api.getNews(API_KEY, category, country).execute()
        }
        if (response.isSuccessful) {
            response.body()?.let {
                _news.value = it.articles
                Log.d("TAG", "The size of _news in loadNews() is " + _news.value.size.toString())
            }
        } else {
            Log.d("TAG", "onFailure: ${response.message()}")
        }
    }
}