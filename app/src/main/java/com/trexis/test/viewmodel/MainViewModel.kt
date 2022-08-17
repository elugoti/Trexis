package com.trexis.test.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trexis.test.UserResp
import com.trexis.test.model.User
import com.trexis.test.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val usersList = MutableLiveData<List<UserResp>>()
    val transactionDetails = MutableLiveData<List<UserResp>>()
    val errorMessage = MutableLiveData<String>()
    val user = User()
    var isUserLogin = MutableLiveData<Boolean>()
    var unitTestMsg = MutableLiveData<String>()

    fun validateUser() {
        val response = repository.validateUser(user.username!!, user.password!!)
        response.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                Log.d("LoginSuccess", "success")
                isUserLogin.postValue(true)
                unitTestMsg.postValue("Login was successful")
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Log.d("LoginSuccess", "failed1:" + t.message)
                errorMessage.postValue(t.message)
                unitTestMsg.postValue("Login was failure")
            }
        })
    }

    fun getUsersList() {

        val response = repository.getAllUsers()
        response.enqueue(object : Callback<List<UserResp>> {
            override fun onResponse(
                call: Call<List<UserResp>?>,
                response: Response<List<UserResp>?>
            ) {
                usersList.postValue(response.body()!!)
            }

            override fun onFailure(call: Call<List<UserResp>?>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("OnFailedIssue", "faileedddd222: " + t.message)
            }

        })
    }

    fun getTransactionDetails(userId: String) {

        val response = repository.getTransactionDetails(userId)
        response.enqueue(object : Callback<List<UserResp>> {
            override fun onResponse(
                call: Call<List<UserResp>?>,
                response: Response<List<UserResp>?>
            ) {
                transactionDetails.postValue(response.body()!!)
            }

            override fun onFailure(call: Call<List<UserResp>?>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("OnFailedIssue", "faileedddd333: " + t.message)
            }

        })
    }
}