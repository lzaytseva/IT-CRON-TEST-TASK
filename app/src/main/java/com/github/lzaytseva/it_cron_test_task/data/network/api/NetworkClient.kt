package com.github.lzaytseva.it_cron_test_task.data.network.api

import com.github.lzaytseva.it_cron_test_task.data.network.dto.response.Response
import io.reactivex.Single

interface NetworkClient {

    fun doRequest(dto: Any): Single<Response>
}