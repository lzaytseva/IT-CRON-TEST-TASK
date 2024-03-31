package com.github.lzaytseva.it_cron_test_task.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.github.lzaytseva.it_cron_test_task.R
import com.github.lzaytseva.it_cron_test_task.databinding.FragmentUserDetailsBinding
import com.github.lzaytseva.it_cron_test_task.domain.model.UserDetails
import com.github.lzaytseva.it_cron_test_task.presentation.state.UserDetailsScreenState
import com.github.lzaytseva.it_cron_test_task.presentation.ui.util.BindingFragment
import com.github.lzaytseva.it_cron_test_task.presentation.viewmodel.UserDetailsViewModel
import com.github.lzaytseva.it_cron_test_task.util.ErrorType
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailsFragment : BindingFragment<FragmentUserDetailsBinding>() {

    private lateinit var username: String

    private val viewModel: UserDetailsViewModel by viewModel {
        parametersOf(username)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            username = it.getString(ARG_USERNAME)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            renderState(it)
        }
    }

    private fun renderState(state: UserDetailsScreenState) {
        when (state) {
            is UserDetailsScreenState.Content -> showContent(state.userDetails)
            is UserDetailsScreenState.Error -> showError(state.error)
            UserDetailsScreenState.Loading -> showLoading()
        }
    }

    private fun showContent(userDetails: UserDetails) {
        binding.refreshLayout.isRefreshing = false
        with(binding) {
            showViews(isInfoLayoutVisible = true)

            with(userDetails) {
                tvName.text = name
                tvEmail.text = email
                tvFollowers.text = followers.toString()
                tvFollowing.text = following.toString()
                tvCreated.text = createdAt
                orgLayout.isVisible = organizationsUrl == null
                organizationsUrl?.let {
                    tvOrganizarion.text = it
                }

                Glide.with(ivAvatar)
                    .load(avatarUrl)
                    .transform(CircleCrop())
                    .placeholder(R.drawable.ic_no_avatar)
                    .into(binding.ivAvatar)
            }
        }

    }

    private fun showError(error: ErrorType) {
        binding.refreshLayout.isRefreshing = false
        showViews(isErrorLayoutVisible = true)

        var imageResId = 0
        var errorTextResId = 0

        when (error) {
            ErrorType.NO_INTERNET -> {
                errorTextResId = R.string.no_internet
                imageResId = R.drawable.connection_error
            }

            ErrorType.SERVER_ERROR -> {
                errorTextResId = R.string.server_error
                imageResId = R.drawable.server_error
            }
        }

        binding.ivError.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), imageResId)
        )
        binding.tvError.text = getString(errorTextResId)
    }

    private fun showLoading() {
        if (binding.refreshLayout.isRefreshing) return
        showViews(isProgressBarVisible = true)
    }

    private fun showViews(
        isProgressBarVisible: Boolean = false,
        isInfoLayoutVisible: Boolean = false,
        isErrorLayoutVisible: Boolean = false
    ) {
        with(binding) {
            refreshLayout.isVisible = isErrorLayoutVisible
            card.isVisible = isInfoLayoutVisible
            detailsLayout.isVisible = isInfoLayoutVisible
            progressLayout.isVisible = isProgressBarVisible
        }
    }


    private fun initViews() {
        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.loadDetails()
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserDetailsBinding {
        return FragmentUserDetailsBinding.inflate(inflater, container, false)
    }

    companion object {
        private const val ARG_USERNAME = "username"

        fun createArgs(username: String): Bundle {
            return bundleOf(
                ARG_USERNAME to username
            )
        }
    }
}