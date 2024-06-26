package com.github.lzaytseva.it_cron_test_task.data.network.api


import com.github.lzaytseva.it_cron_test_task.data.network.dto.UserDto
import com.github.lzaytseva.it_cron_test_task.data.network.dto.UserListItemDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("/users")
    fun getUsers(@Query("since") since: String? = null): Single<Response<List<UserListItemDto>>>

    @GET("/users/{username}")
    fun getUserDetails(@Path("username") username: String): Single<Response<UserDto>>
}