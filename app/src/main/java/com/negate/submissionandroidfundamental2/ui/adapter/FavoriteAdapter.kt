package com.negate.submissionandroidfundamental2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.negate.submissionandroidfundamental2.database.Favorite
import com.negate.submissionandroidfundamental2.databinding.UserFavListBinding
import com.negate.submissionandroidfundamental2.helper.FavDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private val listFavorites = ArrayList<Favorite>()
    fun setListFavorites(listFav: List<Favorite>) {
        val diffCallback = FavDiffCallback(listFavorites, listFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listFavorites.clear()
        listFavorites.addAll(listFav)
        diffResult.dispatchUpdatesTo(this)
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorite: Favorite)
        fun onValueChanged(favorite: Favorite)
    }

    inner class MyViewHolder(private val binding: UserFavListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.apply {
                name.text = favorite.username
                location.text = favorite.profileUrl
                fav.isChecked = true
                avatar.load(favorite.avatar) {
                    crossfade(true)
                    crossfade(400)
                    transformations(CircleCropTransformation())
                }

                binding.fav.setOnCheckedChangeListener { _, _ ->
                    onItemClickCallback?.onValueChanged(favorite)
                }

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(favorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserFavListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount() = listFavorites.size


}