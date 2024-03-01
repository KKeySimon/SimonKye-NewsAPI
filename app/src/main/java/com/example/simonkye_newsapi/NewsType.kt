package com.example.simonkye_newsapi

data class NewsType(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)