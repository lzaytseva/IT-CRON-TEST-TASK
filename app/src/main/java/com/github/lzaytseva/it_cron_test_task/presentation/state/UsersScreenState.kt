package com.github.lzaytseva.it_cron_test_task.presentation.state

import com.github.lzaytseva.it_cron_test_task.domain.model.UserListItem
import com.github.lzaytseva.it_cron_test_task.util.ErrorType

sealed interface UsersScreenState {
    data object Loading : UsersScreenState

    data object LoadingNextPage : UsersScreenState

    data class Content(val users: List<UserListItem>) : UsersScreenState

    data class Error(val error: ErrorType) : UsersScreenState

    data object NoMoreContent

}

sealed interface UsersScreenSideEffects {
    data class ErrorLoadingNextPage(val error: ErrorType) : UsersScreenSideEffects
}