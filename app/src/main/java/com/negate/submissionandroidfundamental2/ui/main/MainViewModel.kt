package com.negate.submissionandroidfundamental2.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.negate.submissionandroidfundamental2.model.SearchModel
import com.negate.submissionandroidfundamental2.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {

    private val _searchData = MutableLiveData<SearchModel>()
    val searchData: LiveData<SearchModel> = _searchData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getData(querySearch: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = try {
                ApiConfig.getApiService().getListUser(querySearch)
            } catch (e: IOException) {
                Log.e(TAG, "getData: Connection Problem")
                _isLoading.value = false
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "getData: ${e.message}")
                _isLoading.value = false
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _searchData.value = response.body()
                _isLoading.value = false
            } else {
                _isLoading.value = false
                Log.e(TAG, "getData: Unsuccesful Message")
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}