package com.heshmat.reddittoppostsmvvm.ui.main.viewmodel

import android.util.Log
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
    private val TAG: String = "viewmodeltag";
    private val redditPosts = MutableLiveData<Resource<RedditPosts>>()
    private val compositeDisposable = CompositeDisposable()
    private var firstPageFlag: String = ""
    private var pageAfter: String = ""
    private var pageBefore: String = ""
    private val isFirstPage = MutableLiveData<Boolean>()

    init {
        fetchRedditPosts()

    }

    fun fetchRedditPosts() {
        redditPosts.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getRedditPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ redditPost ->
                    pageAfter = redditPost.data?.after ?: ""
                    firstPageFlag = pageAfter
                    isFirstPage.postValue(true)

                    redditPosts.postValue(Resource.success(redditPost))
                    Log.i(
                        TAG,
                        "fetchRedditPosts ${"flag: $firstPageFlag after: $pageAfter before: $pageBefore"}"
                    );
                }, { throwable ->
                    Log.i(
                        TAG,
                        "fetchRedditPosts error ${"flag: $firstPageFlag after: $pageAfter before: $pageBefore"}"
                    );

                    redditPosts.postValue(throwable.message?.let { Resource.error(it, null) })

                })
        )

    }

    fun fetchPageAfter() {
        redditPosts.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getDataAfter(pageAfter, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ redditPost ->
                    pageAfter = redditPost.data?.after ?: ""
                    if (firstPageFlag.isNullOrEmpty()) {
                        firstPageFlag = pageAfter
                    }
                    isFirstPage.postValue(false)

                    pageBefore = redditPost.data?.children?.get(0)?.data?.name ?: ""
                    redditPosts.postValue(Resource.success(redditPost))
                    Log.i(
                        TAG,
                        "fetchPageAfter ${"flag: $firstPageFlag after: $pageAfter before: $pageBefore"}"
                    );

                }, { throwable ->
                    Log.i(
                        TAG,
                        "fetchPageAfter error ${"flag: $firstPageFlag after: $pageAfter before: $pageBefore"}"
                    );
                    redditPosts.postValue(throwable.message?.let { Resource.error(it, null) })
                })
        )
    }

    fun fetchPageBefore() {
        if (firstPageFlag.isNotEmpty() && firstPageFlag != pageAfter) {

            isFirstPage.postValue(false)

            redditPosts.postValue(Resource.loading(null))
            compositeDisposable.add(
                mainRepository.getDataBefore(pageBefore, 4)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ redditPost ->
                        if (redditPost.data?.children?.isNotEmpty()!!) {
                            var lastPost: Int = (redditPost.data!!.children?.size ?: -1) - 1
                            Log.i(TAG, "fetchPageBefore in-if ${"lastChild: $lastPost"}");
                            pageAfter = redditPost.data?.children?.get(lastPost)?.data?.name ?: ""
                            pageBefore = redditPost.data?.children?.get(0)?.data?.name ?: ""
                            isFirstPage().postValue(firstPageFlag == pageAfter)
                            Log.i(
                                TAG,
                                "fetchPageBefore in-if ${"flag: $firstPageFlag after: $pageAfter before: $pageBefore"}"
                            );
                            redditPosts.postValue(Resource.success(redditPost))
                        }
                    }, { throwable ->
                        Log.i(
                            TAG,
                            "fetchPageBefore error ${"flag: $firstPageFlag after: $pageAfter before: $pageBefore"}"
                        );

                        redditPosts.postValue(throwable.message?.let {
                            Resource.error(
                                it,
                                null
                            )
                        })
                    })
            )
        } else
            isFirstPage.postValue(true)

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getReditPost(): LiveData<Resource<RedditPosts>> {
        return redditPosts
    }

    fun isFirstPage() = isFirstPage

}