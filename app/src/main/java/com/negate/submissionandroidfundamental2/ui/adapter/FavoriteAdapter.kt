package com.negate.submissionandroidfundamental2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.negate.submissionandroidfundamental2.database.Favorite
import com.negate.submissionandroidfundamental2.databinding.UserFavListBinding
import com.negate.submissionandroidfundamental2.ui.fragment.FavFragmentDirections

class FavoriteAdapter(private val onUnLoveClick: (Favorite) -> Unit) :
    ListAdapter<Favorite, FavoriteAdapter.MyViewHolder>(DiffCallback) {

    class MyViewHolder(val binding: UserFavListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.apply {
                name.text = favorite.username
                location.text = favorite.profileUrl
                avatar.load(favorite.avatar) {
                    crossfade(true)
                    crossfade(400)
                    transformations(CircleCropTransformation())
                }

                itemView.setOnClickListener {
                    val nav = FavFragmentDirections.actionFavFragmentToDetailFragment(favorite.username)
                    itemView.findNavController().navigate(nav)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserFavListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))

        val favButton = holder.binding.fav
        favButton.setOnClickListener {
            onUnLoveClick(getItem(position))
        }
    }

    companion object {
        val DiffCallback: DiffUtil.ItemCallback<Favorite> =
            object : DiffUtil.ItemCallback<Favorite>() {
                override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                    return oldItem.username == newItem.username
                }

                override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                    return oldItem == newItem
                }
            }
    }
}