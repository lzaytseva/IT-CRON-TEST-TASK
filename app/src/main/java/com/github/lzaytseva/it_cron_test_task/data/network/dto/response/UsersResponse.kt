package com.github.lzaytseva.it_cron_test_task.data.network.dto.response

import com.github.lzaytseva.it_cron_test_task.data.network.dto.UserListItemDto

data class UsersResponse(
    val users: List<UserListItemDto>,
    val nextSince: String?
) : Response()
