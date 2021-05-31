package com.priambudi19.core.data.source.remote

import com.priambudi19.core.data.source.remote.network.ApiResponse
import com.priambudi19.core.data.source.remote.network.ApiService
import com.priambudi19.core.data.source.remote.response.DetailUserResponse
import com.priambudi19.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun getSearchUser(query: String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.searchUser(query)
                val listUser = response.items
                if (listUser.isNotEmpty()) {
                    emit(ApiResponse.Success(listUser))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message))
            }

        }.flowOn(Dispatchers.IO)
    }

    fun getDetailUser(username: String): Flow<ApiResponse<DetailUserResponse>> {
        return flow {
            try {
                val detail = apiService.getUserDetail(username)
                emit(ApiResponse.Success(detail))
            }catch (e: Exception) {
                emit(ApiResponse.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

}
