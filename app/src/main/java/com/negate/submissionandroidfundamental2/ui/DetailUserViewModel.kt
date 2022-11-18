package com.negate.submissionandroidfundamental2.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.negate.submissionandroidfundamental2.model.FollowModel
import com.negate.submissionandroidfundamental2.model.UserModel
import com.negate.submissionandroidfundamental2.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailUserViewModel : ViewModel() {

    private var _userDetail = MutableLiveData<UserModel>()
    val userDetail: LiveData<UserModel> = _userDetail

    private var _followers = MutableLiveData<List<FollowModel>>()
    val follower: LiveData<List<FollowModel>> = _followers

    private var _following = MutableLiveData<List<FollowModel>>()
    val following: LiveData<List<FollowModel>> = _following

    private var _userLoading = MutableLiveData<Boolean>()
    val userLoading: LiveData<Boolean> = _userLoading

    private var _followerLoading = MutableLiveData<Boolean>()
    val followerLoading: LiveData<Boolean> = _followerLoading

    private var _followingLoading = MutableLiveData<Boolean>()
    val followingLoading: LiveData<Boolean> = _followingLoading

    fun getDetailData(token: String, username:String) {
        _userLoading.value = true
        getFollower(token, username)
        getFollowing(token, username)
        viewModelScope.launch {
            val response = try {
                ApiConfig.getApiService()
                    .getDetailUser(token, username)
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

    private fun getFollower(token: String, username:String) {
        viewModelScope.launch {
            _followerLoading.value = true
            val response = try {
                ApiConfig.getApiService()
                    .getFollowers(token, username, 10)
            } catch (e: IOException) {
                Log.e(TAG, "getFollower: Connection Problem")
                _followerLoading.value = false
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "getFollower: ${e.message}")
                _followerLoading.value = false
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _followers.value = response.body()
                _followerLoading.value = false
            } else {
                _followingLoading.value = false
            }
        }
    }

    private fun getFollowing(token: String, username:String) {
        viewModelScope.launch {
            _followingLoading.value = true
            val response = try {
                ApiConfig.getApiService()
                    .getFollowing(token, username, 10)
            } catch (e: IOException) {
                Log.e(TAG, "getFollowing: Connection Problem")
                _followingLoading.value = false
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "getFollowing: ${e.message}")
                _followingLoading.value = false
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _following.value = response.body()
                _followingLoading.value = false
            } else {
                _followingLoading.value = false
            }
        }
    }

    companion object {
        const val TAG = "DetailUserViewModel"
    }
}