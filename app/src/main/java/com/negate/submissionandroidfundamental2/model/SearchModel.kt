package com.negate.submissionandroidfundamental2.model


import com.google.gson.annotations.SerializedName

data class SearchModel(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)