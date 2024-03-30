package com.github.lzaytseva.it_cron_test_task.data.network.dto.response

import com.github.lzaytseva.it_cron_test_task.data.network.dto.UserDto

data class UserDetailsResponse(
    val user: UserDto
) : Response()