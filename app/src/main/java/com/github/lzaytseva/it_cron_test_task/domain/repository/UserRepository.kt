package com.github.lzaytseva.it_cron_test_task.domain.repository

import com.github.lzaytseva.it_cron_test_task.domain.model.UserDetails
import com.github.lzaytseva.it_cron_test_task.domain.model.UsersListResult
import com.github.lzaytseva.it_cron_test_task.util.Resource
import io.reactivex.Single

interface UserRepository {

    fun getUsers(): Single<Resource<UsersListResult>>

    fun getUserDetails(username: String): Single<Resource<UserDetails>>
}