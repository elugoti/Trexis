package com.trexis.test

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    @FormUrlEncoded
    @POST("login")
    fun validateUser(@Field("username") username : String, @Field("password") password: String): Call<Void>

    @GET("accounts")
    fun getAllUsers(): Call<List<UserResp>>

    @GET("transactions")
    fun getTransactionDetails(@Query("accountId") accountId:String): Call<List<UserResp>>

    companion object {

        var retrofitService: RetrofitService? = null

        //Create the RetrofitService instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.29.177:5555/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}