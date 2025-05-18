package com.training.whatnow

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.training.whatnow.databinding.ArticleListItemBinding
import androidx.core.net.toUri

class NewsAdapter(val activity: Activity, val articles: ArrayList<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    // drawing the list item (ViewHolder) for the first time then cashing
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NewsViewHolder {
        val binding =
            ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)

    }

    //Change Data inside each item
    override fun onBindViewHolder(
        holder: NewsViewHolder, position: Int
    ) {
        holder.binding.articleTxt.text = articles[position].title
        Glide.with(holder.binding.articleImg.context)//Activity
            .load(articles[position].urlToImage).error(R.drawable.img_broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleImg)

        val url = articles[position].url

        holder.binding.articleContainer.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, url.toUri())

            activity.startActivity(intent)
        }
        holder.binding.shareFb.setOnClickListener {
            ShareCompat.IntentBuilder(activity).setType("text/plain")
                .setChooserTitle("Share article with:").setText(articles[position].url)
                .startChooser()
        }
    }

    override fun getItemCount() = articles.size

    //Cashing
    class NewsViewHolder(val binding: ArticleListItemBinding) : ViewHolder(binding.root)


}