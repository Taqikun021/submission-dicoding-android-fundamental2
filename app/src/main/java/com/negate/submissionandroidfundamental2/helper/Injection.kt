package com.negate.submissionandroidfundamental2.helper

import android.content.Context
import com.negate.submissionandroidfundamental2.database.FavoriteRoomDatabase
import com.negate.submissionandroidfundamental2.repository.FavRepository

object Injection {
    fun provideRepository(context: Context): FavRepository {
        val database = FavoriteRoomDatabase.getDatabase(context)
        val dao = database.favDao()
        return FavRepository.getInstance(dao)
    }
}