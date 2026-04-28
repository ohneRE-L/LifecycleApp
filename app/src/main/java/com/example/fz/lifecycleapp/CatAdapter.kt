package com.example.fz.lifecycleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CatAdapter : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    private var cats = emptyList<Model>()

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pic, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentCat = cats[position]
        Picasso.get()
            .load(currentCat.url)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = cats.size

    fun setCats(newCats: List<Model>) {
        this.cats = newCats
        notifyDataSetChanged()
    }
}
