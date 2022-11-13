package com.negate.submissionandroidfundamental2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.negate.submissionandroidfundamental2.R
import com.negate.submissionandroidfundamental2.databinding.FragmentDetailBinding
import com.negate.submissionandroidfundamental2.model.UserModel
import com.negate.submissionandroidfundamental2.ui.DetailUserViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = "Bearer ${getString(R.string.github_token)}"
        val username = arguments?.getString("username").toString()

        viewModel.getDetailData(token, username)
        viewModel.userDetail.observe(viewLifecycleOwner) {
            showData(it)
        }

        viewModel.userLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.loading.visibility = View.VISIBLE
            binding.content.visibility = View.GONE
        } else {
            binding.loading.visibility = View.GONE
            binding.content.visibility = View.VISIBLE
        }
    }

    private fun showData(userModel: UserModel?) {
        binding.apply {
            avatar.load(userModel?.avatarUrl) {
                error(R.drawable.profile___default)
                crossfade(true)
                crossfade(400)
                transformations(CircleCropTransformation())
            }
            name.text = userModel?.name
            username.text = userModel?.login
            repo.text = userModel?.publicRepos.toString()
            follow.text = getString(
                R.string.follow,
                userModel?.followers.toString(),
                userModel?.following.toString()
            )
            if (userModel?.location != null) lokasi.text =
                userModel.location else lokasi.visibility = View.GONE
            if (userModel?.bio != null) bio.text = userModel.bio else bio.visibility = View.GONE
            if (userModel?.twitterUsername != null) twitter.text =
                userModel.twitterUsername else twitter.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}