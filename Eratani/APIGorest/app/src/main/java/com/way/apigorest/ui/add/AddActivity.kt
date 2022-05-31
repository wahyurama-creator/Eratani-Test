package com.way.apigorest.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.way.apigorest.databinding.ActivityAddBinding
import com.way.apigorest.ui.MainActivity
import com.way.apigorest.ui.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            validateField()
        }
    }

    private fun validateField() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val gender = binding.editTextGender.text.toString()
        val status = binding.editTextStatus.text.toString()

        if (name.isEmpty()) {
            binding.inputLayoutName.error = "Field cannot be blank!"
            binding.inputLayoutName.requestFocus()
        } else if (email.isEmpty()) {
            binding.inputLayoutEmail.error = "Field cannot be blank!"
            binding.inputLayoutEmail.requestFocus()
        } else if (gender.isEmpty()) {
            binding.inputLayoutGender.error = "Field cannot be blank!"
            binding.inputLayoutGender.requestFocus()
        } else if (status.isEmpty()) {
            binding.inputLayoutStatus.error = "Field cannot be blank!"
            binding.inputLayoutStatus.requestFocus()
        } else {
            showLoadingView()
            MainScope().launch(Dispatchers.IO) {
                delay(1000)
                mainViewModel.registerUser(name, email, gender, status)
                startActivity(Intent(this@AddActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun showLoadingView() {
        binding.progressBar2.visibility = View.VISIBLE
    }

}