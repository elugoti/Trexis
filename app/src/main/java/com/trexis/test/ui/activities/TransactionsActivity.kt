package com.trexis.test.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.trexis.test.MainAdapter
import com.trexis.test.vmfactory.MyViewModelFactory
import com.trexis.test.network.RetrofitService
import com.trexis.test.UserResp
import com.trexis.test.databinding.ActivityTransactionsBinding
import com.trexis.test.repository.MainRepository
import com.trexis.test.viewmodel.MainViewModel
import com.google.gson.Gson

class TransactionsActivity : AppCompatActivity() {
    private val TAG = "TransactionsActivity"

    private lateinit var binding: ActivityTransactionsBinding
    var userResp: UserResp? = null
    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userResp = Gson().fromJson(intent.extras?.getString("data"), UserResp::class.java)
        userResp?.let {
            Log.d(TAG, "txnList: ${it.id}")
            fetchTransactionDetails()
        }

    }

    private fun fetchTransactionDetails() {
        //get viewmodel instance using ViewModelProvider.Factory
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        //set adapter in recyclerview
        binding.recyclerview.adapter = adapter

        //the observer will only receive events if the owner(activity) is in active state
        //invoked when transactionDetails data changes
        viewModel.transactionDetails.observe(this, Observer {
            Log.d(TAG, "txnList: $it")
            adapter.setDataList(it)
            binding.progressCircular.visibility = View.GONE
        })

        //invoked when a network exception occurred
        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
            binding.progressCircular.visibility = View.GONE
        })

        userResp?.id?.let { viewModel.getTransactionDetails(it) }
    }
}