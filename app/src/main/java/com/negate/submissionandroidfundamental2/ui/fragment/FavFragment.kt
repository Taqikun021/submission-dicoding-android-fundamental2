package com.negate.submissionandroidfundamental2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.negate.submissionandroidfundamental2.database.Favorite
import com.negate.submissionandroidfundamental2.databinding.FragmentFavBinding
import com.negate.submissionandroidfundamental2.helper.ViewModelFactory
import com.negate.submissionandroidfundamental2.ui.adapter.FavoriteAdapter
import com.negate.submissionandroidfundamental2.ui.viewmodel.FavViewModel

class FavFragment : Fragment() {

    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FavViewModel by viewModels { factory }
        val listAdapter = FavoriteAdapter()

        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = listAdapter
        }

        viewModel.favList.observe(viewLifecycleOwner) {
            listAdapter.setListFavorites(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loading.visibility = if (it) View.VISIBLE else View.GONE
            binding.rv.visibility = if (it) View.GONE else View.VISIBLE
        }

        listAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favorite: Favorite) {
                val nav = FavFragmentDirections
                    .actionFavFragmentToDetailFragment(favorite.username)
                view.findNavController().navigate(nav)
            }

            override fun onValueChanged(favorite: Favorite) {
                viewModel.deleteData(favorite)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}