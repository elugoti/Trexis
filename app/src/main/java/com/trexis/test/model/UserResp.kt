package com.c1ctech.mvvmwithnetworksource

import com.google.gson.annotations.SerializedName

data class UserResp(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("title") val title: String,
    @SerializedName("balance") val balance: Float
)
