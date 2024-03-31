package com.github.lzaytseva.it_cron_test_task.di

import com.github.lzaytseva.it_cron_test_task.data.network.api.GithubApiService
import com.github.lzaytseva.it_cron_test_task.data.network.api.NetworkClient
import com.github.lzaytseva.it_cron_test_task.data.network.impl.RetrofitNetworkClient
import com.github.lzaytseva.it_cron_test_task.data.network.mapper.UserMapper
import com.github.lzaytseva.it_cron_test_task.data.repository.UserRepositoryImpl
import com.github.lzaytseva.it_cron_test_task.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val dataModule = module {

    factory {
        UserMapper()
    }

    single<GithubApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            context = androidContext(), githubApiService = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(networkClient = get(), mapper = get())
    }
}