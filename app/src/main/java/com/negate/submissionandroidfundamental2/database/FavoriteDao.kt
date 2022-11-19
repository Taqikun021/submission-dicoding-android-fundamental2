package com.negate.submissionandroidfundamental2.database

import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    suspend fun getFavoriteList(): List<Favorite>

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username)")
    suspend fun isUserFavorite(username: String): Boolean
}