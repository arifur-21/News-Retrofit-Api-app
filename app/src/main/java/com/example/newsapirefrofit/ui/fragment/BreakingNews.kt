package com.example.newsapirefrofit.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapirefrofit.*
import com.example.newsapirefrofit.adapter.NewsAdapter
import com.example.newsapirefrofit.db.ArticleDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_save_news.*


class BreakingNews : Fragment() {
    lateinit var viewMode: NewsViewModel
    lateinit var newsAdapter: NewsAdapter



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_breaking_news, container, false)

        //viewMode = (activity as MainActivity).viewModel

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRepository = NewsRepository(ArticleDatabase(requireContext()))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewMode = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        setupRecyclerView()


        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_breakinNewsId_to_articleFragment, bundle)
            Toast.makeText(context,"toase",Toast.LENGTH_SHORT).show()
        }




        viewMode.bresingNews.observe(viewLifecycleOwner, Observer { response->


            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("breakingNews","An error occured; $message")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })


    }

    private fun hideProgressBar() {
        breakingProgressBarId.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        breakingProgressBarId.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        breakingNewRecycleViewId.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}