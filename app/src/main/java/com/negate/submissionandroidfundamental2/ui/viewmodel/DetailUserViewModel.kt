package com.negate.submissionandroidfundamental2.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.negate.submissionandroidfundamental2.BuildConfig
import com.negate.submissionandroidfundamental2.database.Favorite
import com.negate.submissionandroidfundamental2.model.FollowModel
import com.negate.submissionandroidfundamental2.model.UserModel
import com.negate.submissionandroidfundamental2.network.ApiConfig
import com.negate.submissionandroidfundamental2.repository.FavRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailUserViewModel(private val favRepository: FavRepository) : ViewModel() {

    private val token = "Bearer ${BuildConfig.API_KEY}"

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

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

    fun initialize(username: String) {
        getDetailData(username)
        getFollower(username)
        getFollowing(username)
        isFavorited(username)
    }

    fun changeStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = makeData()
            if (_isFavorite.value == true) {
                try {
                    favRepository.delete(data)
                } catch (e: IOException){
                    Log.e(TAG, "changeStatus: ${e.message}")
                    return@launch
                }
            } else {
                try {
                    favRepository.insert(data)
                } catch (e: IOException){
                    Log.e(TAG, "changeStatus: ${e.message}")
                    return@launch
                }
            }
            isFavorited(data.username)
        }
    }

    private fun makeData(): Favorite {
        return Favorite(
            username = userDetail.value?.login.toString(),
            profileUrl = userDetail.value?.htmlUrl,
            avatar = userDetail.value?.avatarUrl
        )
    }

    private fun isFavorited(username: String) {
        viewModelScope.launch {
            _isFavorite.value = favRepository.isUserFavorite(username)
        }
    }

    private fun getDetailData(username: String) {
        _userLoading.value = true
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

    private fun getFollower(username: String) {
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

    private fun getFollowing(username: String) {
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