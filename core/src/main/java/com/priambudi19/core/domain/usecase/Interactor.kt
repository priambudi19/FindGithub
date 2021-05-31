package com.priambudi19.core.domain.usecase

import com.priambudi19.core.data.Resource
import com.priambudi19.core.domain.model.User
import com.priambudi19.core.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class Interactor(private val repository: MainRepository) : UseCase {
    override suspend fun searchUser(query: String): Flow<Resource<List<User>>> =
        repository.searchUser(query)

    override suspend fun getDetailUser(username: String): Flow<Resource<User>> =
        repository.getDetailUser(username)

    override fun getListFavorite(): Flow<List<User>> = repository.getListFavorite()

    override fun setUserFavorite(user: User) =
        repository.setUserFavorite(user)

    override fun deleteUserFavorite(user: User) {
        repository.deleteUserFavorite(user)
    }

    override fun checkFavorite(id: Int): Flow<Int> =repository.checkFavorite(id)

}