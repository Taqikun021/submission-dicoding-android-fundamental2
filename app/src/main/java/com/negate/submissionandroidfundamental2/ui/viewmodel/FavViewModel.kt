package com.negate.submissionandroidfundamental2.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.negate.submissionandroidfundamental2.database.Favorite
import com.negate.submissionandroidfundamental2.repository.FavRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class FavViewModel(private val favRepository: FavRepository) : ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _favList = MutableLiveData<List<Favorite>>()
    val favList: LiveData<List<Favorite>> = _favList

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() {
        _loading.value = true
        viewModelScope.launch {
            val response = try {
                favRepository.getFavList()
            } catch (e: IOException){
                Log.e(TAG, "getAllFavorites: ${e.message}")
                _loading.value = false
                return@launch
            }

            _favList.value = response
            _loading.value = false
        }
    }

    fun deleteData(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            favRepository.delete(favorite)
        }
    }

    companion object{
        const val TAG = "FavViewModel"
    }
}