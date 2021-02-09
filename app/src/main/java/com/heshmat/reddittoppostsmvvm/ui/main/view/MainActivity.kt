package com.heshmat.reddittoppostsmvvm.ui.main.view

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.heshmat.reddittoppostsmvvm.utils.ConnectionLiveData
import com.heshmat.reddittoppostsmvvm.utils.Status
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RedditPostAdapter.ImageClickListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: RedditPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
        setupConnectionObserver()
        prevBt.setOnClickListener {
            mainViewModel.fetchPageBefore()
        }
        nextBt.setOnClickListener {
            mainViewModel.fetchPageAfter()
        }
        //check if first page
        mainViewModel.isFirstPage().observe(this, Observer {
            if (it)
                prevBt.visibility = View.GONE
            else
                prevBt.visibility = View.VISIBLE
        })
    }


    private fun setupUI() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = GridLayoutManager(this, 1)

        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 2)

        }
        adapter = RedditPostAdapter(arrayListOf(), this)
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
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun renderList(redditPost: RedditPosts) {
        if (redditPost.data?.children?.isNotEmpty()!!) {
            adapter.clearData()
            redditPost.data?.children?.let { adapter.addData(it) }
            adapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(0)
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun connectionState(isConnected: Boolean) {
        if (!isConnected) {
            noInternetTv.visibility = View.VISIBLE
            nextBt.isEnabled = false
            prevBt.isEnabled = false

        } else {
            if (adapter.itemCount == 0)
                mainViewModel.fetchRedditPosts()
            noInternetTv.visibility = View.GONE
            nextBt.isEnabled = true
            prevBt.isEnabled = true
        }


    }

    private fun setupConnectionObserver() {
        val connectionLiveData = ConnectionLiveData(applicationContext)
        connectionLiveData.observe(this, Observer {
            it?.isConnected?.let { it1 -> connectionState(it1) }
        })

    }

    override fun onImgClick(imgUrl: String?, isImg: Boolean) {
        val intent: Intent
        if (isImg) {
            intent = Intent(this, FullScreenViewActivity::class.java).apply {
                putExtra("IMG_URL", imgUrl)
            }
        } else {
            intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(imgUrl))

        }
        try {
            startActivity(intent)

        } catch (e: Exception) {
        }

    }
}
