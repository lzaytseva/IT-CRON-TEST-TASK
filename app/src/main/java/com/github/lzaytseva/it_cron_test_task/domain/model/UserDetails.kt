package com.github.lzaytseva.it_cron_test_task.domain.model

data class UserDetails(
    val avatarUrl: String,
    val createdAt: String,
    val email: String?,
    val followers: Int,
    val following: Int,
    val name: String,
    val organizationsUrl: String?
)