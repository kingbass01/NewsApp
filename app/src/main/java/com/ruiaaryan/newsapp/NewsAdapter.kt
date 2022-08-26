package com.ruiaaryan.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(
    private val items: List<Article?>?,
    private val listener: MainActivity)
    : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onClicked(viewHolder.absoluteAdapterPosition)
        }
        viewHolder.btn.setOnClickListener{
            listener.share(viewHolder.absoluteAdapterPosition)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = items?.get(position)
        if (current != null) {
            if (current.title != null) {
                holder.title.text = current.title
                if(current.urlToImage != null) {
                    Picasso.get().load(current.urlToImage).into(holder.img)
                }
                else {
                    Picasso.get().load(R.drawable.img).into(holder.img)
                }
                if(current.source?.name != null) {
                    holder.author.text = current.source.name
                }
                else{
                    holder.author.text = ""
                }
                if(current.content != null)
                {
                    holder.content.text = current.content
                }
                else{
                    holder.content.text =""
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.textView)
    val img : ImageView = itemView.findViewById(R.id.imageView)
    val author:TextView = itemView.findViewById(R.id.author)
    val btn:ImageButton = itemView.findViewById(R.id.share)
    val content:TextView= itemView.findViewById(R.id.content)
}