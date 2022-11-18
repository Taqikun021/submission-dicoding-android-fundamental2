package com.negate.submissionandroidfundamental2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.negate.submissionandroidfundamental2.BuildConfig
import com.negate.submissionandroidfundamental2.R
import com.negate.submissionandroidfundamental2.databinding.FragmentDetailBinding
import com.negate.submissionandroidfundamental2.model.UserModel
import com.negate.submissionandroidfundamental2.ui.DetailUserViewModel
import com.negate.submissionandroidfundamental2.ui.PagerAdapter

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailUserViewModel by activityViewModels()
    private var state: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPager()

        val token = "Bearer ${BuildConfig.API_KEY}"
        val username = arguments?.getString("username").toString()

        viewModel.getDetailData(token, username)
        viewModel.userDetail.observe(viewLifecycleOwner) {
            showData(it)
        }

        viewModel.userLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.fab.setOnClickListener {
            state = !state
            changeFabIcon()
        }
    }

    private fun changeFabIcon() {
        when {
            state -> binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_favorite_24
                )
            )
            else -> binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    private fun setPager() {
        val rv = listOf(
            FollowerFragment(),
            FollowingFragment()
        )
        binding.viewPager.adapter =
            PagerAdapter(rv, childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Follower"
                else -> tab.text = "Following"
            }
        }.attach()
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