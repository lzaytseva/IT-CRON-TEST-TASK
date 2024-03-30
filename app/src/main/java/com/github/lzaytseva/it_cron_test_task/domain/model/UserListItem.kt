package com.github.lzaytseva.it_cron_test_task.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class UserListItem(
    val avatarUrl: String,
    val id: Int,
    val login: String,
)