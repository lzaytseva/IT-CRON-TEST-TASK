package com.github.lzaytseva.it_cron_test_task.presentation.state

import com.github.lzaytseva.it_cron_test_task.domain.model.UserDetails
import com.github.lzaytseva.it_cron_test_task.util.ErrorType

sealed interface UserDetailsScreenState {

    data class Content(val userDetails: UserDetails) : UserDetailsScreenState

    data class Error(val error: ErrorType) : UserDetailsScreenState
}