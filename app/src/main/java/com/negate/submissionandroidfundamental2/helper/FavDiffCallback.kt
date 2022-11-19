package com.negate.submissionandroidfundamental2.helper

import androidx.recyclerview.widget.DiffUtil
import com.negate.submissionandroidfundamental2.database.Favorite

class FavDiffCallback(
    private val oldFavList: List<Favorite>,
    private val newFavList: List<Favorite>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldFavList.size
    override fun getNewListSize() = newFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].username == newFavList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].avatar == newFavList[newItemPosition].avatar &&
                oldFavList[oldItemPosition].profileUrl == newFavList[newItemPosition].profileUrl
    }
}