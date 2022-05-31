package com.way.apigorest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.way.apigorest.databinding.ActivityMainBinding
import com.way.apigorest.ui.add.AddActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        MainScope().launch {
            showLoadingView()
            delay(1000)
            mainViewModel.getUser()
            viewUser()
        }

        initRecyclerView()

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        mainAdapter = MainAdapter()
        binding.rvUser.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun viewUser() {
        mainViewModel.user.observe(this) { response ->
            try {
                hideLoadingView()
                response.body().let {
                    mainAdapter.differ.submitList(it?.toList())
                    Log.e("USER", it?.toList()?.size.toString())
                }
            } catch (e: Exception) {
                hideLoadingView()
                Log.e("USER", e.message.toString())
            }
        }
    }

    private fun showLoadingView() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingView() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}