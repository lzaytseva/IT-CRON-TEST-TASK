package com.github.lzaytseva.it_cron_test_task.data.network.mapper

import com.github.lzaytseva.it_cron_test_task.data.network.dto.UserDto
import com.github.lzaytseva.it_cron_test_task.data.network.dto.UserListItemDto
import com.github.lzaytseva.it_cron_test_task.domain.model.UserDetails
import com.github.lzaytseva.it_cron_test_task.domain.model.UserListItem

class UserMapper {
    fun mapUserToDomain(userDto: UserDto): UserDetails {
        return with (userDto) {
            UserDetails(
                avatarUrl = avatarUrl,
                createdAt = createdAt,
                email = email,
                followers = followers,
                following = following,
                name = name,
                organizationsUrl = organizationsUrl
            )
        }
    }

    fun mapUserListItemToDomain(userListItemDto: UserListItemDto): UserListItem {
        return with (userListItemDto) {
            UserListItem(
                avatarUrl = avatarUrl,
                id = id,
                login = login
            )
        }
    }
}