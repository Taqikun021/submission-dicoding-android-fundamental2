package com.negate.submissionandroidfundamental2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.negate.submissionandroidfundamental2.model.UserModel
import com.negate.submissionandroidfundamental2.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailUserViewModel : ViewModel() {

    private var _userDetail = MutableLiveData<UserModel>()
    val userDetail: LiveData<UserModel> = _userDetail

    private val _userLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _userLoading

    fun getUserDetail(username: String){
        viewModelScope.launch {
            _userLoading.value = true
            val response = try {
                ApiConfig.getApiService().getDetailUser(username)
            } catch (e: IOException){
                Log.e(TAG, "getUserDetail: Connection Problem")
                _userLoading.value = false
                return@launch
            } catch (e: HttpException){
                Log.e(TAG, "getUserDetail: ${e.message}")
                _userLoading.value = false
                return@launch
            }

            if (response.isSuccessful && response.body() != null){
                _userDetail.value = response.body()
            }
        }
    }

    companion object {
        const val TAG = "DetailUserViewModel"
    }
}