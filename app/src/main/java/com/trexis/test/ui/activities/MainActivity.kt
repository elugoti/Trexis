package com.c1ctech.mvvmwithnetworksource.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.c1ctech.mvvmwithnetworksource.MainAdapter
import com.c1ctech.mvvmwithnetworksource.MyViewModelFactory
import com.c1ctech.mvvmwithnetworksource.R
import com.c1ctech.mvvmwithnetworksource.RetrofitService
import com.c1ctech.mvvmwithnetworksource.databinding.ActivityMainBinding
import com.c1ctech.mvvmwithnetworksource.repository.MainRepository
import com.c1ctech.mvvmwithnetworksource.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter()

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
        //invoked when movieList data changes
        viewModel.usersList.observe(this, Observer {
            Log.d(TAG, "movieList: $it")
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