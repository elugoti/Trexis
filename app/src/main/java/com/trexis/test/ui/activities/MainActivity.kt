package com.trexis.test.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.trexis.test.MainAdapter
import com.trexis.test.vmfactory.MyViewModelFactory
import com.trexis.test.R
import com.trexis.test.network.RetrofitService
import com.trexis.test.databinding.ActivityMainBinding
import com.trexis.test.repository.MainRepository
import com.trexis.test.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get viewmodel instance using ViewModelProvider.Factory
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        //set adapter in recyclerview
        binding.recyclerview.adapter = adapter

        //the observer will only receive events if the owner(activity) is in active state
        //invoked when userlist data changes
        viewModel.usersList.observe(this, Observer {
            Log.d(TAG, "userlist: $it")
            adapter.setDataList(it)
            binding.progressCircular.visibility = View.GONE
        })

        //invoked when a network exception occurred
        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
            binding.progressCircular.visibility = View.GONE
        })

        viewModel.getUsersList()

        val navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation_view)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }
}