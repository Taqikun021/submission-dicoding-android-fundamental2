package com.negate.submissionandroidfundamental2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.negate.submissionandroidfundamental2.database.Favorite
import com.negate.submissionandroidfundamental2.repository.FavRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(private val favRepository: FavRepository) : ViewModel() {

    init {
        getAllFavorites()
    }

    fun getAllFavorites() = favRepository.getFavList()

    fun deleteData(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            favRepository.delete(favorite)
        }
    }
}