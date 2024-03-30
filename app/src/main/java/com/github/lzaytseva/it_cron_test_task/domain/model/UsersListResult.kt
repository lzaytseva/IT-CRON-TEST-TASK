package com.github.lzaytseva.it_cron_test_task.domain.model

data class UsersListResult(
    val users: List<UserListItem>,
    val nextSince: String?
)