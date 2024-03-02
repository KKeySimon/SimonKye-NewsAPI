package com.example.simonkye_newsapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.simonkye_newsapi.databinding.FragmentNewsDetailBinding

private const val TAG = "NewsDetailFragment"
class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var news: Article
    private val args: NewsDetailFragmentArgs by navArgs()
    private val viewModel: NewsListViewModel by navGraphViewModels(R.id.nav_graph)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        news = Article(
            author = "",
            content = "",
            description = "",
            publishedAt = "",
            source = Source(
                id = "",
                name = ""
            ),
            title = "",
            url = "",
            urlToImage = ""
        )

        Log.d(TAG, "The News ID IS: ${args.newsId}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val specificNews = viewModel.getArticleByTitle(args.newsId)
        Log.d(TAG, viewModel.getArticleByTitle(args.newsId)?.title ?: "Title not found")
        binding.articleTitle.text = specificNews?.title ?: "Title not found"
        binding.author.text = "By " + (specificNews?.author ?: "Author not found")
        binding.publishedAt.text = "Published: " + (specificNews?.publishedAt ?: "Published date not found")
        binding.content.text = specificNews?.content ?: "Content not available"
        binding.source.text = "Source: " + (specificNews?.source?.name ?: "Source not found")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}