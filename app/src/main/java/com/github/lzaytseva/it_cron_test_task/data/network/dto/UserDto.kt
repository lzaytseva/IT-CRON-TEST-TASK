package com.github.lzaytseva.it_cron_test_task.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("created_at") val createdAt: String,
    val email: String?,
    val followers: Int,
    val following: Int,
    val name: String,
    @SerializedName("organizations_url") val organizationsUrl: String?
)