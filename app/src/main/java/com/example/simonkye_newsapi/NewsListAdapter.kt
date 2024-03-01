package com.example.simonkye_newsapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simonkye_newsapi.databinding.ListItemNewsBinding

class ArticleHolder (
    private val binding: ListItemNewsBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(news: Article) {
        binding.newsTitle.text = news.title
    }
}

class NewsListAdapter(
    private val news: List<Article>
) : RecyclerView.Adapter<ArticleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewsBinding.inflate(inflater, parent, false)
        return ArticleHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val new = news[position]
        holder.bind(new)
    }

    override fun getItemCount(): Int {
        return news.size
    }
}