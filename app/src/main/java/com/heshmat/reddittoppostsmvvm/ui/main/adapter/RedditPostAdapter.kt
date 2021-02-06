package com.heshmat.reddittoppostsmvvm.ui.main.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.heshmat.reddittoppostsmvvm.R
import com.heshmat.reddittoppostsmvvm.data.model.Children
import kotlinx.android.synthetic.main.post_layout.view.*

class RedditPostAdapter(private val childrenArrList: ArrayList<Children>) :
    RecyclerView.Adapter<RedditPostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.post_layout, parent, false)
        )

    override fun getItemCount(): Int = childrenArrList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(childrenArrList[position])

    fun addData(list: List<Children>) {
        childrenArrList.addAll(list)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Children) {
            itemView.authorNameTv.text = post.data?.author ?: ""
            itemView.titleTv.text = post.data?.title ?: ""
            itemView.commentNumTv.text = post.data?.numComments.toString()
            val createdUtc = post.data?.createdUtc?.toLong()?.times(1000)
            val ago = createdUtc?.let {
                DateUtils.getRelativeTimeSpanString(
                    it,
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS
                )
            }
            itemView.createdAtTv.text = ago
            if (!post.data?.thumbnail.isNullOrBlank()) {
                itemView.thumbIv.visibility = View.VISIBLE
                Glide.with(itemView.thumbIv.context)
                    .load(post.data?.thumbnail)
                    .into(itemView.thumbIv)
            }

        }
    }
}