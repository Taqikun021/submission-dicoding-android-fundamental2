package com.negate.submissionandroidfundamental2.network

import com.negate.submissionandroidfundamental2.model.SearchModel
import com.negate.submissionandroidfundamental2.model.UserModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: Bearer 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")
    suspend fun getListUser(
        @Query("q") querySearch: String?
    ): Response<SearchModel>

    @GET("user/{username}")
    @Headers("Authorization: Bearer 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")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): Response<UserModel>
}