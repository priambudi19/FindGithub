@file:Suppress("unused", "unused", "unused")

package com.priambudi19.core.util

import com.priambudi19.core.data.source.local.entity.UserEntity
import com.priambudi19.core.data.source.remote.response.DetailUserResponse
import com.priambudi19.core.data.source.remote.response.UserListResponse
import com.priambudi19.core.data.source.remote.response.UserResponse
import com.priambudi19.core.domain.model.User


fun DetailUserResponse.toUserEntity(): UserEntity {
    return UserEntity(
        id,
        avatarUrl,
        login,
        name?:"No Name",
        company ?: "",
        location ?: "",
        followers,
        following,
        publicRepos,
        htmlUrl
    )
}

fun DetailUserResponse.toUser(): User {

    return User(
        id,
        avatarUrl,
        login,
        name ?: "",
        company ?: "",
        location ?: "",
        followers,
        following,
        publicRepos,
        htmlUrl
    )


}

fun UserListResponse.toListUser(): List<User> {
    val list = arrayListOf<User>()
    this.items.map {
        list.add(
            User(
                id = it.id,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl,
                company = "",
                location = ""
            )
        )
    }
    return list
}

fun UserEntity.toUser(): User {
    return User(
        id,
        avatarUrl,
        login,
        name,
        company,
        location,
        followers,
        following,
        publicRepos,
        htmlUrl
    )
}

fun List<UserEntity>.toListUser(): List<User> {
    val list = arrayListOf<User>()
    this.map {
        list.add(
            User(
                id = it.id,
                avatarUrl = it.avatarUrl,
                name = it.name,
                login = it.login,
                company = it.company,
                location = it.location,
                followers = it.followers,
                following = it.following,
                publicRepos = it.publicRepos,
                htmlUrl = it.htmlUrl
            )
        )
    }
    return list
}

fun List<UserResponse>.toListUserEntity(): List<UserEntity> {
    val list = arrayListOf<UserEntity>()
    this.map {
        list.add(
            UserEntity(id = it.id, avatarUrl = it.avatarUrl, login = it.login, htmlUrl = it.htmlUrl)
        )
    }
    return list
}

fun User.toEntity(): UserEntity = UserEntity(
    id,
    avatarUrl,
    login,
    name ?: "",
    company ?: "",
    location ?: "",
    followers,
    following,
    publicRepos,
    htmlUrl
)
