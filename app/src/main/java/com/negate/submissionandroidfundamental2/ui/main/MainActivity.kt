package com.negate.submissionandroidfundamental2.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.negate.submissionandroidfundamental2.R
import com.negate.submissionandroidfundamental2.databinding.ActivityMainBinding
import com.negate.submissionandroidfundamental2.model.Item
import com.negate.submissionandroidfundamental2.model.SearchModel
import com.negate.submissionandroidfundamental2.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getData("repos:>1")
        viewModel.searchData.observe(this) {
            setSearchData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(it: Boolean) {
        binding.loading.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setSearchData(it: SearchModel?) {
        val adapter = UserAdapter(it?.items)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(item: Item) {
                startActivity(
                    Intent(this@MainActivity, DetailUserActivity::class.java).apply {
                        putExtra(INTENT, item)
                    }
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getData(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    companion object {
        const val INTENT = "Data"
    }
}