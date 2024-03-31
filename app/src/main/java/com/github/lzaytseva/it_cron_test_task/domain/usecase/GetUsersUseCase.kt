package com.github.lzaytseva.it_cron_test_task.domain.usecase

import com.github.lzaytseva.it_cron_test_task.domain.model.UsersListResult
import com.github.lzaytseva.it_cron_test_task.domain.repository.UserRepository
import com.github.lzaytseva.it_cron_test_task.util.Resource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class GetUsersUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(since: String? = null): Single<Resource<UsersListResult>> {
        return repository.getUsers(since = since).observeOn(AndroidSchedulers.mainThread())
    }
}