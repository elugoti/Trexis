package com.c1ctech.mvvmwithnetworksource.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c1ctech.mvvmwithnetworksource.MyViewModelFactory
import com.c1ctech.mvvmwithnetworksource.RetrofitService
import com.c1ctech.mvvmwithnetworksource.databinding.ActivityLoginBinding
import com.c1ctech.mvvmwithnetworksource.repository.MainRepository
import com.c1ctech.mvvmwithnetworksource.viewmodel.MainViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get viewmodel instance using ViewModelProvider.Factory
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        viewModel.isUserLogin.observe(this) {
            binding.progressCircular.visibility = View.GONE
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.errorMessage.observe(this, Observer {
            binding.progressCircular.visibility = View.GONE
            Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_LONG).show()
        })

        binding.btnLogin.setOnClickListener {
            val userName = binding.userName.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (userName.isEmpty()) {
                binding.userName.error = "Enter valid user name"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.password.error = "Enter password"
                return@setOnClickListener
            }
            binding.progressCircular.visibility = View.VISIBLE
            validateUser(userName, password)
        }

    }

    private fun validateUser(userName: String, password: String) {
        viewModel.user.username = userName
        viewModel.user.password = password
        viewModel.validateUser()
    }

    fun validate(userName: String, password: String): String {
        validateUser(userName, password)
        var msg = ""
        viewModel.unitTestMsg.observe(this, Observer {
            msg = it
        })
        return msg
    }

}