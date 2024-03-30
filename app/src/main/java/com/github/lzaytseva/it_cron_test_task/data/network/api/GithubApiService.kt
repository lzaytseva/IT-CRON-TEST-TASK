package com.github.lzaytseva.it_cron_test_task.data.network.api


import com.github.lzaytseva.it_cron_test_task.BuildConfig
import com.github.lzaytseva.it_cron_test_task.data.network.dto.UserDto
import com.github.lzaytseva.it_cron_test_task.data.network.dto.UserListItemDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GithubApiService {

    @GET("/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getUsers(): Single<Response<List<UserListItemDto>>>

    @GET("/users/{username}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getUserDetails(@Path("username") username: String): Single<Response<UserDto>>
}