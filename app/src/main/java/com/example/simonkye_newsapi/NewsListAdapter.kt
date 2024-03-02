package com.example.simonkye_newsapi

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simonkye_newsapi.databinding.ListItemNewsBinding
import java.util.UUID

class ArticleHolder (
    private val binding: ListItemNewsBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(news: Article, onNewsClicked: (newsId: String) -> Unit) {
        Log.d("ArticleHolder", "Binding article with ID: ${news.title}")

        binding.newsTitle.text = news.title
        binding.root.setOnClickListener{
            onNewsClicked(news.title)
        }
    }
}

class NewsListAdapter(
    private val news: List<Article>,
    private val onNewsClicked: (newsId: String) -> Unit
) : RecyclerView.Adapter<ArticleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewsBinding.inflate(inflater, parent, false)
        return ArticleHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val new = news[position]
        holder.bind(new, onNewsClicked)
    }

    override fun getItemCount(): Int {
        return news.size
    }
}