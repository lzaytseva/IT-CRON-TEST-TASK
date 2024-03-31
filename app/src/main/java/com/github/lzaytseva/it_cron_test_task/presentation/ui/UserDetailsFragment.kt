package com.github.lzaytseva.it_cron_test_task.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.github.lzaytseva.it_cron_test_task.R
import com.github.lzaytseva.it_cron_test_task.databinding.FragmentUserDetailsBinding
import com.github.lzaytseva.it_cron_test_task.domain.model.UserDetails
import com.github.lzaytseva.it_cron_test_task.domain.model.UserListItem
import com.github.lzaytseva.it_cron_test_task.presentation.state.UserDetailsScreenState
import com.github.lzaytseva.it_cron_test_task.presentation.state.UsersScreenState
import com.github.lzaytseva.it_cron_test_task.presentation.ui.util.BindingFragment
import com.github.lzaytseva.it_cron_test_task.presentation.viewmodel.UserDetailsViewModel
import com.github.lzaytseva.it_cron_test_task.util.ErrorType
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment : BindingFragment<FragmentUserDetailsBinding>() {

    private val viewModel: UserDetailsViewModel by viewModel()
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserDetailsBinding {
        return FragmentUserDetailsBinding.inflate(inflater, container, false)
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
        }
    }

    private fun showContent(userDetails: UserDetails) {
        with (binding) {
            errorLayout.isVisible = false
            card.isVisible = true
            detailsLayout.isVisible = true

            with(userDetails) {
                tvName.text = name
                tvEmail.text = email
                tvFollowers.text = followers.toString()
                tvFollowing.text = following.toString()
                orgLayout.isVisible = organizationsUrl == null
                organizationsUrl?.let {
                    tvOrganizarion.text = it
                }
            }
        }

    }

    private fun showError(error: ErrorType) {
        binding.errorLayout.isVisible = true
        binding.card.isVisible = false
        binding.detailsLayout.isVisible = false

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


    private fun initViews() {
        binding.btnRetry.setOnClickListener {
            viewModel.loadDetails()
        }
    }
}