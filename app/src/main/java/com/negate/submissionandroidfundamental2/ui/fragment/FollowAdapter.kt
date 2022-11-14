package com.negate.submissionandroidfundamental2.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.negate.submissionandroidfundamental2.databinding.UserListBinding
import com.negate.submissionandroidfundamental2.model.FollowModel

class FollowAdapter(private val items: List<FollowModel>?) :
    RecyclerView.Adapter<FollowAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: UserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowModel) {
            binding.apply {
                name.text = item.login
                location.text = item.htmlUrl
                avatar.load(item.avatarUrl) {
                    crossfade(400)
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = items?.size!!
}