package com.priambudi19.core.domain.repository


import com.priambudi19.core.data.Resource
import com.priambudi19.core.data.source.local.LocalDataSource
import com.priambudi19.core.data.source.remote.RemoteDataSource
import com.priambudi19.core.data.source.remote.network.ApiResponse
import com.priambudi19.core.domain.model.User
import com.priambudi19.core.util.toEntity
import com.priambudi19.core.util.toListUser
import com.priambudi19.core.util.toListUserEntity
import com.priambudi19.core.util.toUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : MainRepository {
    override suspend fun searchUser(query: String): Flow<Resource<List<User>>> {
        val data = MutableStateFlow<Resource<List<User>>>(Resource.Loading())
        remoteDataSource.getSearchUser(query).collect {
            when (it) {
                is ApiResponse.Success -> {
                    data.emit(Resource.Success(it.data.toListUserEntity().toListUser()))
                }
                is ApiResponse.Error -> {
                    data.emit(Resource.Error(it.errorMessage))
                }
                is ApiResponse.Empty -> data.emit(Resource.Success(emptyList()))
            }
        }
        return data
    }

    override suspend fun getDetailUser(username: String): Flow<Resource<User>> {
        val data = MutableStateFlow<Resource<User>>(Resource.Loading())
        remoteDataSource.getDetailUser(username).collect {
            when (it) {
                is ApiResponse.Success -> {
                    data.emit(Resource.Success(it.data.toUser()))
                }
                is ApiResponse.Error -> {
                    data.emit(Resource.Error(it.errorMessage))
                }
                is ApiResponse.Empty -> data.emit(Resource.Success(User()))
            }
        }
        return data
    }


    override fun getListFavorite(): Flow<List<User>> =
        localDataSource.getListFavorite().map { it.toListUser() }


    override fun setUserFavorite(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertFavorite(user.toEntity())
        }
    }

    override fun deleteUserFavorite(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.deleteFavorite(user.toEntity())
        }
    }

    override fun checkFavorite(id: Int): Flow<Int> =
        localDataSource.checkFavorite(id)


}