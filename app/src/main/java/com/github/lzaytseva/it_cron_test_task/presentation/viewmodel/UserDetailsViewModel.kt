package com.github.lzaytseva.it_cron_test_task.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.github.lzaytseva.it_cron_test_task.domain.usecase.GetUserDetailsUseCase

class UserDetailsViewModel(
    private val username: String,
    private val userDetailsUseCase: GetUserDetailsUseCase
) : ViewModel() {
}