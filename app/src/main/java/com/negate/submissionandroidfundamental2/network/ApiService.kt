package com.negate.submissionandroidfundamental2.network

import com.negate.submissionandroidfundamental2.model.SearchModel
import com.negate.submissionandroidfundamental2.model.UserModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    suspend fun getListUser(
        @Header("Authorization") auth: String,
        @Query("q") querySearch: String?
    ): Response<SearchModel>

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Header("Authorization") auth: String,
        @Path("username") username: String
    ): Response<UserModel>
}