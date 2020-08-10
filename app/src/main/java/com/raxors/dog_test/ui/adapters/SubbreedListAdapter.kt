package com.raxors.dog_test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raxors.dog_test.databinding.ItemSubbreedBinding

class SubbreedListAdapter(private val items: List<String>) : RecyclerView.Adapter<SubbreedListAdapter.ViewHolder>() {

    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSubbreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener{view ->
            itemClickListener?.onClick(position, view)
        }
    }

    inner class ViewHolder(private val binding: ItemSubbreedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemDog: String) {
            binding.run {
                this.dogItem = itemDog
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