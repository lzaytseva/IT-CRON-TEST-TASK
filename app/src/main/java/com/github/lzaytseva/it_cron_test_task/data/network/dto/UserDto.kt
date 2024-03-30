package com.github.lzaytseva.it_cron_test_task.data.network.dto

data class UserDto(
    val avatar_url: String,
    val created_at: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val name: String,
    val organizations_url: String?
)