package com.github.lzaytseva.it_cron_test_task.di

import com.github.lzaytseva.it_cron_test_task.domain.usecase.GetUserDetailsUseCase
import com.github.lzaytseva.it_cron_test_task.domain.usecase.GetUsersUseCase
import org.koin.dsl.module

val domainModule = module {

    single<GetUserDetailsUseCase> {
        GetUserDetailsUseCase(repository = get())
    }

    single<GetUsersUseCase> {
        GetUsersUseCase(repository = get())
    }
}