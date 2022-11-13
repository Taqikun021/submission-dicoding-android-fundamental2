package com.negate.submissionandroidfundamental2.ui.main.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.negate.submissionandroidfundamental2.R
import com.negate.submissionandroidfundamental2.databinding.FragmentMainBinding
import com.negate.submissionandroidfundamental2.model.Item
import com.negate.submissionandroidfundamental2.model.SearchModel
import com.negate.submissionandroidfundamental2.ui.detail.DetailUserActivity
import com.negate.submissionandroidfundamental2.ui.main.MainActivity
import com.negate.submissionandroidfundamental2.ui.main.MainViewModel
import com.negate.submissionandroidfundamental2.ui.main.UserAdapter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()

        viewModel.getData("repos:>1")
        viewModel.searchData.observe(viewLifecycleOwner) {
            setSearchData(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.loading.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setSearchData(it: SearchModel?) {
        val adapter = UserAdapter(it?.items)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(item: Item) {
                startActivity(
                    Intent(requireContext(), DetailUserActivity::class.java).apply {
                        putExtra(MainActivity.INTENT, item)
                    }
                )
            }
        })
    }

    private fun setupMenu() {
        (activity as MenuHost).addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu)
                searchView = menu.findItem(R.id.search).actionView as SearchView
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
                searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
                searchView.queryHint = getString(R.string.search_hint)
                searchView.setOnQueryTextListener(object : OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.getData(query)
                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?) = false
                })
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}