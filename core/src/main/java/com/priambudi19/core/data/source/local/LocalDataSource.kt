package com.priambudi19.core.data.source.local

import com.priambudi19.core.data.source.local.entity.UserEntity
import com.priambudi19.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao) {
    fun getListFavorite(): Flow<List<UserEntity>> = userDao.getListFavorite()
    fun insertFavorite(userEntity: UserEntity) = userDao.insertFavorite(userEntity)
    fun deleteFavorite(userEntity: UserEntity) = userDao.deleteFavorite(userEntity)
    fun checkFavorite(id:Int) = userDao.checkFavorite(id)
}