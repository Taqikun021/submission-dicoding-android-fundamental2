package com.negate.submissionandroidfundamental2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        val listAdapter = FavoriteAdapter { fav ->
            viewModel.deleteData(fav)
        }

        viewModel.getAllFavorites().observe(viewLifecycleOwner) {
            binding.loading.visibility = View.GONE
            listAdapter.submitList(it)
        }

        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}