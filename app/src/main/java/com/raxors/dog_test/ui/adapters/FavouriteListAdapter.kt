package com.raxors.dog_test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raxors.dog_test.data.entity.Favourite
import com.raxors.dog_test.databinding.ItemFavouriteBinding

class FavouriteListAdapter(items: List<Favourite>) : RecyclerView.Adapter<FavouriteListAdapter.ViewHolder>() {

    private var itemClickListener: ItemClickListener? = null

    private var items: MutableList<Favourite> = mutableListOf()

    init {
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener{view ->
            itemClickListener?.onClick(position, view)
        }
    }

    inner class ViewHolder(private val binding: ItemFavouriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemFavourite: Favourite) {
            binding.run {
                this.favouriteItem = itemFavourite
                binding.executePendingBindings()
            }
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onClick(position: Int, view: View?)
    }
}