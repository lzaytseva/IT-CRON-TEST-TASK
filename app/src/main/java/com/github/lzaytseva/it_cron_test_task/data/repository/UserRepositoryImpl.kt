package com.github.lzaytseva.it_cron_test_task.data.repository

import com.github.lzaytseva.it_cron_test_task.data.network.api.NetworkClient
import com.github.lzaytseva.it_cron_test_task.data.network.dto.request.GetUserDetailsRequest
import com.github.lzaytseva.it_cron_test_task.data.network.dto.request.GetUsersRequest
import com.github.lzaytseva.it_cron_test_task.data.network.dto.response.UserDetailsResponse
import com.github.lzaytseva.it_cron_test_task.data.network.dto.response.UsersResponse
import com.github.lzaytseva.it_cron_test_task.data.network.impl.RetrofitNetworkClient
import com.github.lzaytseva.it_cron_test_task.data.network.mapper.UserMapper
import com.github.lzaytseva.it_cron_test_task.domain.model.UserDetails
import com.github.lzaytseva.it_cron_test_task.domain.model.UsersListResult
import com.github.lzaytseva.it_cron_test_task.domain.repository.UserRepository
import com.github.lzaytseva.it_cron_test_task.util.ErrorType
import com.github.lzaytseva.it_cron_test_task.util.Resource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserRepositoryImpl(
    private val networkClient: NetworkClient,
    private val mapper: UserMapper
) : UserRepository {

    override fun getUsers(): Single<Resource<UsersListResult>> {
        return networkClient.doRequest(GetUsersRequest())
            .observeOn(Schedulers.io())
            .flatMap { response ->
                when (response.code) {
                    RetrofitNetworkClient.CODE_SUCCESS -> {
                        val usersResponse = response as UsersResponse
                        Single.just(
                            Resource.Success(
                                UsersListResult(
                                    users = usersResponse.users.map {
                                        mapper.mapUserListItemToDomain(it)
                                    },
                                    nextSince = usersResponse.nextSince
                                )
                            )
                        )
                    }

                    RetrofitNetworkClient.CODE_NO_INTERNET -> {
                        Single.just(
                            Resource.Error(ErrorType.NO_INTERNET)
                        )
                    }

                    RetrofitNetworkClient.CODE_WRONG_REQUEST -> {
                        throw RuntimeException("Invalid request")
                    }

                    else -> {
                        Single.just(
                            Resource.Error(ErrorType.SERVER_ERROR)
                        )
                    }
                }
            }
    }

    override fun getUserDetails(username: String): Single<Resource<UserDetails>> {
        return networkClient.doRequest(GetUserDetailsRequest(username = username))
            .observeOn(Schedulers.io())
            .flatMap { response ->
                when (response.code) {
                    RetrofitNetworkClient.CODE_SUCCESS -> {
                        val userDetailsResponse = response as UserDetailsResponse
                        Single.just(
                            Resource.Success(
                                mapper.mapUserToDomain(userDetailsResponse.user)
                            )
                        )
                    }

                    RetrofitNetworkClient.CODE_NO_INTERNET -> {
                        Single.just(
                            Resource.Error(ErrorType.NO_INTERNET)
                        )
                    }

                    RetrofitNetworkClient.CODE_WRONG_REQUEST -> {
                        throw RuntimeException("Invalid request")
                    }

                    else -> {
                        Single.just(
                            Resource.Error(ErrorType.SERVER_ERROR)
                        )
                    }
                }
            }
    }
}
