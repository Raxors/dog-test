package com.raxors.dog_test.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.raxors.dog_test.R
import de.hdodenhof.circleimageview.CircleImageView

class ImageListAdapter(private val context: Context?, arrayList: ArrayList<String>, isFavouritePosition: Set<Int>) :
    RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {
    private var arrayList: ArrayList<String> = ArrayList()

    private var isFavouritePosition: Set<Int>? = null

    private var favouritesClickListener: ImageListAdapter.FavouritesClickListener? = null

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_image_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        @NonNull holder: MyViewHolder,
        position: Int
    ) {
        if (!isFavouritePosition?.contains(position)!!) {
            holder.imageViewFavourite.setImageResource(R.drawable.outline_favorite_border_24)
            holder.imageViewFavourite.colorFilter = null
        } else {
            holder.imageViewFavourite.setImageResource(R.drawable.outline_favorite_24)
            holder.imageViewFavourite.setColorFilter(Color.parseColor("#3700B3"))
        }
        if (context != null) {
            holder.progressBar.visibility = View.VISIBLE
            Glide.with(context).load(arrayList[position]).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

            }).into(holder.imageView)
        }
        holder.imageViewFavourite.setOnClickListener {
            favouritesClickListener?.onClick(position, it)
            if (holder.imageViewFavourite.colorFilter != null) {
                holder.imageViewFavourite.setImageResource(R.drawable.outline_favorite_border_24)
                holder.imageViewFavourite.colorFilter = null
            } else {
                holder.imageViewFavourite.setImageResource(R.drawable.outline_favorite_24)
                holder.imageViewFavourite.setColorFilter(Color.parseColor("#3700B3"))
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class MyViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageViewBreedImage)
        var imageViewFavourite: CircleImageView = itemView.findViewById(R.id.imageViewIsFavourite)
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBarImages)
    }

    init {
        this.arrayList = arrayList
        this.isFavouritePosition = isFavouritePosition
    }

    fun setFavouritesClickListener(favouritesClickListener: FavouritesClickListener?) {
        this.favouritesClickListener = favouritesClickListener
    }

    interface FavouritesClickListener {
        fun onClick(position: Int, view: View?)
    }
}