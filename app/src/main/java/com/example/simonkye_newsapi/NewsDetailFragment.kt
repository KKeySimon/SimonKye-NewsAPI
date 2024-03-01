package com.example.simonkye_newsapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simonkye_newsapi.databinding.FragmentNewsDetailBinding
import java.util.UUID

class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var news: Article

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

        binding.apply {
            articleTitle.apply {
                text = news.title
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}