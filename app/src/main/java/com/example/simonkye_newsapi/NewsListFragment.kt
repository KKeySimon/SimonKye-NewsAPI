package com.example.simonkye_newsapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simonkye_newsapi.databinding.FragmentNewsListBinding
import kotlinx.coroutines.launch

class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val newListViewModel: NewsListViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            val selectedCategoryId = it.getInt("selectedCategoryId", -1)
            if (selectedCategoryId != -1) {
                binding.categories.check(selectedCategoryId)
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        when (selectedCategoryId) {
                            0 -> newListViewModel.loadNews("business")
                            1 -> newListViewModel.loadNews("entertainment")
                            2 -> newListViewModel.loadNews("general")
                            3 -> newListViewModel.loadNews("health")
                            4 -> newListViewModel.loadNews("science")
                            5 -> newListViewModel.loadNews("sports")
                            6 -> newListViewModel.loadNews("technology")
                        }
                    }
                }
            }
        }
        binding.categories.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            val radioButtonText = radioButton?.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                newListViewModel.loadNews(radioButtonText)

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                newListViewModel.news.collect { news ->
                    binding.newsRecyclerView.adapter =
                        NewsListAdapter(news) { newsId ->
                            findNavController().navigate(
                                NewsListFragmentDirections.showNewsDetail(newsId)
                            )
                        }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (_binding != null) {
            outState.putInt("selectedCategoryId", binding.categories.checkedRadioButtonId)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}