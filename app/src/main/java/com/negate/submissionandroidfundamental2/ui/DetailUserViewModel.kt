package com.negate.submissionandroidfundamental2.ui

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

    private var _userLoading = MutableLiveData<Boolean>()
    val userLoading: LiveData<Boolean> = _userLoading

    fun getDetailData(token: String, username: String) {
        _userLoading.value = true
        viewModelScope.launch {
            val response = try {
                ApiConfig.getApiService().getDetailUser(token, username)
            } catch (e: IOException) {
                Log.e(TAG, "getDetailData: Connection Problem")
                _userLoading.value = false
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "getDetailData: ${e.message()}")
                _userLoading.value = false
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _userDetail.value = response.body()
                _userLoading.value = false
            }
        }
    }

    companion object {
        const val TAG = "DetailUserViewModel"
    }
}