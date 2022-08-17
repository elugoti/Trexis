package com.c1ctech.mvvmwithnetworksource.repository

import com.c1ctech.mvvmwithnetworksource.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun validateUser(username: String, password: String) =
        retrofitService.validateUser(username, password)

    fun getAllUsers() = retrofitService.getAllUsers()

    fun getTransactionDetails(userId: String) = retrofitService.getTransactionDetails(userId)
}