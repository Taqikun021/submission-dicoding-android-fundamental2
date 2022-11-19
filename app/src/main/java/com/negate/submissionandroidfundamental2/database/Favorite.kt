package com.negate.submissionandroidfundamental2.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Favorite")
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatar_url")
    var avatar: String?,

    @ColumnInfo(name = "profile_url")
    var profileUrl: String?
) : Parcelable