package com.way.searchword

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.way.searchword.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                wordAdapter.filter.filter(newText)
                return false
            }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(listWord, applicationContext)
        binding.rvWords.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        val listWord = arrayListOf(
            "ability",
            "able",
            "about",
            "above",
            "accept",
            "according",
            "account",
            "across",
            "act",
            "action",
            "activity",
            "actually",
            "add",
            "address",
            "administration",
            "admit",
            "adult",
            "affect",
            "after",
            "again",
            "against",
            "age",
            "agency",
            "agent",
            "ago",
            "agree",
            "agreement",
            "ahead",
            "air",
            "all",
            "allow",
            "almost",
            "alone",
            "along"
        )
    }
}