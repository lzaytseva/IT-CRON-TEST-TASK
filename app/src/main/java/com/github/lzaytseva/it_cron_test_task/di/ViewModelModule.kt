package com.github.lzaytseva.it_cron_test_task.di


import com.github.lzaytseva.it_cron_test_task.presentation.viewmodel.UserDetailsViewModel
import com.github.lzaytseva.it_cron_test_task.presentation.viewmodel.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { (username: String) ->
        UserDetailsViewModel(
            username = username,
            userDetailsUseCase = get()
        )
    }

    viewModel {
        UsersViewModel(
            usersUseCase = get()
        )
    }
}