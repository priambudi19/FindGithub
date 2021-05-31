package com.priambudi19.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class UserEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = 0,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String = "",

    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "company")
    var company: String = "",

    @ColumnInfo(name = "location")
    var location: String = "",

    @ColumnInfo(name = "followers")
    var followers: Int = 0,

    @ColumnInfo(name = "following")
    var following: Int = 0,

    @ColumnInfo(name = "public_repos")
    var publicRepos: String = "",

    @ColumnInfo(name = "html_url")
    var htmlUrl: String = "",

)