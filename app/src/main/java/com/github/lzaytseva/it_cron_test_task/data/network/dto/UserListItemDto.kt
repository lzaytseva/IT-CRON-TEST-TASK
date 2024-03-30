package com.github.lzaytseva.it_cron_test_task.data.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserListItemDto(
    @SerializedName("avatar_url") val avatarUrl: String,
    val id: Int,
    val login: String,
)