package com.negate.submissionandroidfundamental2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.negate.submissionandroidfundamental2.databinding.FragmentFollowingBinding
import com.negate.submissionandroidfundamental2.helper.ViewModelFactory
import com.negate.submissionandroidfundamental2.model.FollowModel
import com.negate.submissionandroidfundamental2.ui.adapter.FollowAdapter
import com.negate.submissionandroidfundamental2.ui.viewmodel.DetailUserViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: DetailUserViewModel by activityViewModels { factory }

        viewModel.following.observe(viewLifecycleOwner) {
            setData(it)
        }

        viewModel.followingLoading.observe(viewLifecycleOwner) {
            binding.loading.visibility = if (it) View.VISIBLE else View.GONE
            binding.rv.visibility = if (it) View.GONE else View.VISIBLE
        }
    }

    private fun setData(list: List<FollowModel>) {
        binding.rv.adapter = FollowAdapter(list)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}