package com.heshmat.reddittoppostsmvvm.ui.main.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.heshmat.reddittoppostsmvvm.R
import com.heshmat.reddittoppostsmvvm.data.model.Children
import kotlinx.android.synthetic.main.post_layout.view.*

class RedditPostAdapter(
    private val childrenArrList: ArrayList<Children>,
    private val listener: ImageClickListener
) :
    RecyclerView.Adapter<RedditPostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.post_layout, parent, false), this.listener
        )

    override fun getItemCount(): Int = childrenArrList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(childrenArrList[position])

    fun addData(list: List<Children>) {
        childrenArrList.addAll(list)
    }

    fun clearData() {
        childrenArrList.clear()
    }

    class ViewHolder(itemView: View, private val listener: ImageClickListener) :
        RecyclerView.ViewHolder(itemView) {
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
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            p0: GlideException?,
                            p1: Any?,
                            p2: Target<Drawable>?,
                            p3: Boolean
                        ): Boolean {

                                itemView.thumbIv.visibility = View.GONE


                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                        }

                        override fun onResourceReady(
                            p0: Drawable?,
                            p1: Any?,
                            p2: Target<Drawable>?,
                            p3: DataSource?,
                            p4: Boolean
                        ): Boolean {
                            // if the image is video so parameter should be false that the video can be open in browser
                            itemView.thumbIv.setOnClickListener {
                            listener.onImgClick(post.data?.imgURl, !post.data?.isVideo!!)}
                            //do something when picture already loaded
                            return false
                        }
                    })
                    .into(itemView.thumbIv)
            }

        }
    }

    interface ImageClickListener {
        fun onImgClick(imgUrl: String?, isImg: Boolean)
    }
}