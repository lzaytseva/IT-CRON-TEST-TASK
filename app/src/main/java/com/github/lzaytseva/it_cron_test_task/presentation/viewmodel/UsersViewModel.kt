package com.github.lzaytseva.it_cron_test_task.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.lzaytseva.it_cron_test_task.domain.model.UserListItem
import com.github.lzaytseva.it_cron_test_task.domain.model.UsersListResult
import com.github.lzaytseva.it_cron_test_task.domain.usecase.GetUsersUseCase
import com.github.lzaytseva.it_cron_test_task.presentation.state.UsersScreenState
import com.github.lzaytseva.it_cron_test_task.util.Resource
import io.reactivex.disposables.Disposable


class UsersViewModel(
    private val usersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UsersScreenState>()
    val uiState: LiveData<UsersScreenState>
        get() = _uiState

    private var isNextPageLoading = false
    private var nextSince: String? = null
    private val users = mutableListOf<UserListItem>()

    private var disposable: Disposable? = null

    init {
        loadFirstPage()
    }

    fun loadFirstPage() {
        disposable = usersUseCase.invoke()
            .doOnSubscribe {
                _uiState.value = UsersScreenState.Loading
            }
            .subscribe { resource ->
                processResult(resource)
            }
    }

    fun loadNextPage() {
        if (isNextPageLoading) return
        disposable = usersUseCase.invoke(since = nextSince)
            .doOnSubscribe {
                _uiState.value = UsersScreenState.LoadingNextPage
                isNextPageLoading = true
            }
            .subscribe { resource ->
                processResult(resource, processNextPage = true)
                isNextPageLoading = false
            }
    }

    private fun processResult(
        resource: Resource<UsersListResult>,
        processNextPage: Boolean = false
    ) {
        when (resource) {
            is Resource.Error -> {
                _uiState.value = if (processNextPage) {
                    UsersScreenState.ErrorLoadingNextPage(
                        error = resource.errorType!!
                    )
                } else {
                    UsersScreenState.Error(
                        error = resource.errorType!!
                    )
                }
            }

            is Resource.Success -> {
                users.addAll(resource.data!!.users)
                nextSince = resource.data.nextSince

                _uiState.value = UsersScreenState.Content(
                    users = users
                )
            }
        }
    }

    fun setFeedbackWasShown(state: UsersScreenState.ErrorLoadingNextPage) {
        _uiState.value = state.copy(feedbackWasShown = true)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}