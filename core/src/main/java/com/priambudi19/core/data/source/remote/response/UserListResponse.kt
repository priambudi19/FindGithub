package com.priambudi19.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("items")
    var items: List<UserResponse> = emptyList(),
    @SerializedName("total_count")
    var totalCount: Int = 0,
)
data class UserResponse(
    @SerializedName("avatar_url")
    var avatarUrl: String="",
    @SerializedName("html_url")
    var htmlUrl: String="",
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("login")
    var login: String="",
)
data class DetailUserResponse(
    @SerializedName("avatar_url")
    var avatarUrl: String="",
    @SerializedName("company")
    var company: String?,
    @SerializedName("followers")
    var followers: Int=0,
    @SerializedName("following")
    var following: Int=0,
    @SerializedName("html_url")
    var htmlUrl: String="",
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("location")
    var location: String?,
    @SerializedName("login")
    var login: String="",
    @SerializedName("name")
    var name: String?="",
    @SerializedName("public_repos")
    var publicRepos: String="",

    )