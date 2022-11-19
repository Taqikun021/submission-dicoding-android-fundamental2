package com.negate.submissionandroidfundamental2.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.negate.submissionandroidfundamental2.repository.FavRepository
import com.negate.submissionandroidfundamental2.ui.viewmodel.DetailUserViewModel
import com.negate.submissionandroidfundamental2.ui.viewmodel.FavViewModel

class ViewModelFactory private constructor(private val favRepository: FavRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(favRepository) as T
        } else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(favRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }
}