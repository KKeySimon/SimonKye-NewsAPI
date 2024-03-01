package com.example.simonkye_newsapi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.UUID

class NewsListViewModel : ViewModel() {
    private val _news = MutableStateFlow<List<Article>>(emptyList())
    val news : StateFlow<List<Article>>
        get() = _news.asStateFlow()
    val BASE_URL : String = "https://newsapi.org/v2/"
    val API_KEY : String = "9a2d9d75d0dc451b9ef749a1e73c1e23"
    val DATE : String = "2024-02-28"
    val COUNTRY : String = "us"
    init {
        Log.d("TAG", "Before API Function Call")
        viewModelScope.launch {
            loadNews()
        }
        Log.d("TAG", "After API Function Call")
    }
    suspend fun loadNews() {
        Log.d("TAG", "loadNews Entered")
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val response = withContext(Dispatchers.IO) {
            api.getNews(API_KEY, DATE, COUNTRY).execute()
        }
        if (response.isSuccessful) {
            response.body()?.let {
                _news.value = it.articles
            }
        } else {
            Log.d("TAG", "onFailure: ${response.message()}")
        }
    }
}