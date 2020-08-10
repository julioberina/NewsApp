package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.News

public class NewsListAdapter() : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private var data: News? = null
    private lateinit var context: Context

    constructor(context: Context, data: News?) : this() {
        this.context = context
        this.data = data
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        public lateinit var imageView: ImageView
        public lateinit var titleText: TextView
        public lateinit var descriptionText: TextView
        public lateinit var newsLayout: ConstraintLayout

        init {
            imageView = itemView.findViewById<ImageView>(R.id.imageView)
            titleText = itemView.findViewById<TextView>(R.id.titleText)
            descriptionText = itemView.findViewById<TextView>(R.id.descriptionText)
            newsLayout = itemView.findViewById<ConstraintLayout>(R.id.newsLayout)
        }

    }

    public fun setData(data: News?) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_article, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return data!!.articles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = data!!.articles.get(position)
        Glide.with(context).load(article?.urlToImage).into(holder.imageView)
        holder.titleText.setText(article?.title)
        holder.descriptionText.setText(article?.description)

        holder.newsLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article?.url))
            context.startActivity(intent)
        }
    }
}