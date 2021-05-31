package com.priambudi19.core.data.source.remote.network

import com.priambudi19.core.data.source.remote.response.DetailUserResponse
import com.priambudi19.core.data.source.remote.response.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
    ): UserListResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String,
    ): DetailUserResponse
}

