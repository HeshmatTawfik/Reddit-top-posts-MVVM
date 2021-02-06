package com.heshmat.reddittoppostsmvvm.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heshmat.reddittoppostsmvvm.data.model.RedditPosts
import com.heshmat.reddittoppostsmvvm.data.repository.MainRepository
import com.heshmat.reddittoppostsmvvm.utils.Resource

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val redditPosts=MutableLiveData<Resource<RedditPosts>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchRedditPosts()
    }
    private fun fetchRedditPosts(){
        redditPosts.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getRedditPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({redditPost->
                    redditPosts.postValue(Resource.success(redditPost))
                },{throwable ->
                    redditPosts.postValue(throwable.message?.let { Resource.error(it,null) })
                })
        )

    }




    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getReditPost(): LiveData<Resource<RedditPosts>> {
        return redditPosts
    }


}