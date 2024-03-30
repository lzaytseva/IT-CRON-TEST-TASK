package com.github.lzaytseva.it_cron_test_task.domain.usecase

import com.github.lzaytseva.it_cron_test_task.domain.model.UserDetails
import com.github.lzaytseva.it_cron_test_task.domain.model.UsersListResult
import com.github.lzaytseva.it_cron_test_task.domain.repository.UserRepository
import com.github.lzaytseva.it_cron_test_task.util.Resource
import io.reactivex.Single

class GetUserDetailsUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(username: String): Single<Resource<UserDetails>> {
        return repository.getUserDetails(username = username)
    }
}