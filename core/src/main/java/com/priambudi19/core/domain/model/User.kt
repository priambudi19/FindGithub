package com.priambudi19.core.domain.model

class User (
    var id: Int = 0,
    var avatarUrl: String = "",
    var login: String = "",
    var name: String? = "",
    var company: String?="",
    var location: String?="",
    var followers: Int = 0,
    var following: Int = 0,
    var publicRepos: String = "",
    var htmlUrl: String = "",
)