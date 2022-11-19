package com.negate.submissionandroidfundamental2.repository

import com.negate.submissionandroidfundamental2.database.Favorite
import com.negate.submissionandroidfundamental2.database.FavoriteDao

class FavRepository private constructor(private val favoriteDao: FavoriteDao) {

    suspend fun insert(favorite: Favorite) = favoriteDao.insert(favorite)
    suspend fun delete(favorite: Favorite) = favoriteDao.delete(favorite)
    suspend fun getFavList(): List<Favorite> = favoriteDao.getFavoriteList()
    suspend fun isUserFavorite(username: String): Boolean = favoriteDao.isUserFavorite(username)

    companion object {
        @Volatile
        private var instance: FavRepository? = null
        fun getInstance(dao: FavoriteDao): FavRepository = instance ?: synchronized(this) {
            instance ?: FavRepository(dao)
        }.also { instance = it }
    }
}