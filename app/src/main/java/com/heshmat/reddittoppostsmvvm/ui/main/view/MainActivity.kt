package com.heshmat.reddittoppostsmvvm.ui.main.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.heshmat.reddittoppostsmvvm.R
import com.heshmat.reddittoppostsmvvm.data.api.ApiHelper
import com.heshmat.reddittoppostsmvvm.data.api.ApiServiceImpl
import com.heshmat.reddittoppostsmvvm.data.model.RedditPosts
import com.heshmat.reddittoppostsmvvm.ui.base.ViewModelFactory
import com.heshmat.reddittoppostsmvvm.ui.main.adapter.RedditPostAdapter
import com.heshmat.reddittoppostsmvvm.ui.main.viewmodel.MainViewModel
import com.heshmat.reddittoppostsmvvm.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: RedditPostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }


    private fun setupUI() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = GridLayoutManager(this, 1)

        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 2)

        }
        adapter = RedditPostAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {


        mainViewModel.getReditPost().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { redditPost -> renderList(redditPost) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(redditPost: RedditPosts) {
        val r: RedditPosts = redditPost
        redditPost.data?.children?.let { adapter.addData(it) }
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}
