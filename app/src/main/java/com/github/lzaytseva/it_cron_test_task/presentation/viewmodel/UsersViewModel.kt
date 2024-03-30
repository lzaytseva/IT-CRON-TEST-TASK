package com.github.lzaytseva.it_cron_test_task.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.github.lzaytseva.it_cron_test_task.domain.usecase.GetUsersUseCase

class UsersViewModel(
    private val usersUseCase: GetUsersUseCase
) : ViewModel() {
}