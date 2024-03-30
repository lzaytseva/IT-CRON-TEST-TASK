package com.github.lzaytseva.it_cron_test_task.data.network.impl

import android.content.Context
import com.github.lzaytseva.it_cron_test_task.data.network.api.GithubApiService
import com.github.lzaytseva.it_cron_test_task.data.network.api.NetworkClient
import com.github.lzaytseva.it_cron_test_task.data.network.dto.request.GetUserDetailsRequest
import com.github.lzaytseva.it_cron_test_task.data.network.dto.request.GetUsersRequest
import com.github.lzaytseva.it_cron_test_task.data.network.dto.response.Response
import com.github.lzaytseva.it_cron_test_task.data.network.dto.response.UserDetailsResponse
import com.github.lzaytseva.it_cron_test_task.data.network.dto.response.UsersResponse
import com.github.lzaytseva.it_cron_test_task.data.util.ConnectionChecker
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RetrofitNetworkClient(
    private val context: Context,
    private val githubApiService: GithubApiService
) : NetworkClient {
    override fun doRequest(dto: Any): Single<Response> {

        if (ConnectionChecker.isConnected(context = context)) {
            return Single.just(Response().apply { CODE_NO_INTERNET })
        }


        return when (dto) {
            is GetUsersRequest -> getUsers(dto.since)
            is GetUserDetailsRequest -> getUserDetails(dto.username)
            else -> Single.just(Response().apply { code = CODE_WRONG_REQUEST })
        }
    }

    private fun getUsers(since: String?): Single<Response> {
        return githubApiService.getUsers(since)
            .flatMap { response ->
                try {
                    if (response.code() == CODE_SUCCESS && response.body() != null) {
                        val nextSince = response.headers()["link"]
                            ?.substringAfter('=')
                            ?.substringBefore('>')
                        Single.just(
                            UsersResponse(
                                users = response.body()!!,
                                nextSince = nextSince
                            ).apply { code = CODE_SUCCESS }
                        )
                    } else {
                        Single.just(
                            Response().apply { code = response.code() }
                        )
                    }
                } catch (t: Throwable) {
                    Single.just(Response().apply { code = SERVER_ERROR })
                }
            }
            .subscribeOn(Schedulers.io())
    }

    private fun getUserDetails(username: String): Single<Response> {
        return githubApiService.getUserDetails(username)
            .flatMap { response ->
                try {
                    Single.just(
                        if (response.code() == CODE_SUCCESS && response.body() != null) {
                            UserDetailsResponse(
                                user = response.body()!!
                            ).apply { code = CODE_SUCCESS }
                        } else {
                            Response().apply { code = response.code() }
                        }
                    )
                } catch (t: Throwable) {
                    Single.just(Response().apply { code = SERVER_ERROR })
                }
            }
            .subscribeOn(Schedulers.io())
    }

    companion object {
        const val CODE_NO_INTERNET = -1
        const val CODE_WRONG_REQUEST = 400
        const val SERVER_ERROR = 500
        const val CODE_SUCCESS = 200
    }
}