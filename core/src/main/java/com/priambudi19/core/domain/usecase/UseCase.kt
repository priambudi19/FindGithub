package com.priambudi19.core.domain.usecase

import com.priambudi19.core.data.Resource
import com.priambudi19.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UseCase {
    suspend fun searchUser(query: String): Flow<Resource<List<User>>>
    suspend fun getDetailUser(username: String): Flow<Resource<User>>
    fun getListFavorite(): Flow<List<User>>
    fun setUserFavorite(user: User)
    fun deleteUserFavorite(user: User)
    fun checkFavorite(id:Int):Flow<Int>
}