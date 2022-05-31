package com.way.searchword

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.way.searchword.databinding.ItemWordBinding
import java.util.*

class WordAdapter(private val itemList: ArrayList<String>, private val context: Context) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>(), Filterable {

    var wordFilterList = ArrayList<String>()
    private var isAvailable: Boolean = false

    init {
        wordFilterList = itemList
    }

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.tvWords.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordFilterList[position])
    }

    override fun getItemCount(): Int = wordFilterList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    wordFilterList = itemList
                } else {
                    val resultList = ArrayList<String>()
                    for (row in itemList) {
                        if (row.contains(charSearch)) {
                            isAvailable = true
                            if (row.lowercase(Locale.ROOT)
                                    .contains(charSearch.lowercase(Locale.ROOT))
                            ) {
                                resultList.add(row)
                            }
                        } else {
                            isAvailable = false
                        }
                    }
                    wordFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = wordFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                wordFilterList = results?.values as ArrayList<String>
                showStatus()
                notifyDataSetChanged()
            }

        }
    }

    private fun showStatus() {
        if (isAvailable) {
            Toast.makeText(context, "Word available", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Word unavailable", Toast.LENGTH_SHORT).show()
        }
    }
}