package com.github.lzaytseva.it_cron_test_task.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.lzaytseva.it_cron_test_task.R
import com.github.lzaytseva.it_cron_test_task.databinding.FragmentUsersBinding
import com.github.lzaytseva.it_cron_test_task.domain.model.UserListItem
import com.github.lzaytseva.it_cron_test_task.presentation.state.UsersScreenState
import com.github.lzaytseva.it_cron_test_task.presentation.ui.adapter.UserAdapter
import com.github.lzaytseva.it_cron_test_task.presentation.ui.util.BindingFragment
import com.github.lzaytseva.it_cron_test_task.presentation.viewmodel.UsersViewModel
import com.github.lzaytseva.it_cron_test_task.util.ErrorType
import com.github.lzaytseva.it_cron_test_task.util.FeedbackUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BindingFragment<FragmentUsersBinding>() {

    private val viewModel: UsersViewModel by viewModel()
    private val adapter = UserAdapter {

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

    private fun renderState(state: UsersScreenState) {
        when (state) {
            is UsersScreenState.Content -> showContent(state.users)
            is UsersScreenState.Error -> showError(state.error)
            is UsersScreenState.ErrorLoadingNextPage -> {
                showErrorLoadingNextPage(state.error)
                viewModel.setFeedbackWasShown(state)
            }
            UsersScreenState.Loading -> showLoading()
            UsersScreenState.LoadingNextPage -> showLoadingNextPage()
        }
    }

    private fun showContent(users: List<UserListItem>) {
        showViews(isRecyclerViewVisible = true)
        adapter.submitList(users)
    }

    private fun showError(error: ErrorType) {
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

    private fun showErrorLoadingNextPage(error: ErrorType) {
        showViews(isRecyclerViewVisible = true)
        FeedbackUtils.showSnackbar(
            root = requireView(),
            text = when (error) {
                ErrorType.NO_INTERNET -> getString(R.string.no_internet)
                ErrorType.SERVER_ERROR -> getString(R.string.server_error)
            }
        )
    }

    private fun showLoading() {
        showViews(isProgressBarVisible = true)
    }

    private fun showLoadingNextPage() {
        showViews(isProgressBarVisible = true, isRecyclerViewVisible = true)
    }

    private fun initViews() {
        initRecyclerView()
        setBtnRetryClickListener()
    }

    private fun initRecyclerView() {
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        with(binding.rvUsers) {
                            val pos =
                                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                            val itemsCount = adapter!!.itemCount
                            if (pos >= itemsCount - 1) {
                                viewModel.loadNextPage()
                            }
                        }
                    }
                }
            }
        )
    }

    private fun setBtnRetryClickListener() {
        binding.btnRetry.setOnClickListener {
          viewModel.loadFirstPage()
        }
    }

    private fun showViews(
        isProgressBarVisible: Boolean = false,
        isRecyclerViewVisible: Boolean = false,
        isErrorLayoutVisible: Boolean = false
    ) {
        with(binding) {
            errorLayout.isVisible = isErrorLayoutVisible
            rvUsers.isVisible = isRecyclerViewVisible
            progressBar.isVisible = isProgressBarVisible
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUsersBinding {
        return FragmentUsersBinding.inflate(inflater, container, false)
    }
}