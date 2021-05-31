package com.priambudi19.core.data.source.local.room

import androidx.room.*
import com.priambudi19.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(userEntity: UserEntity)

    @Delete
    fun deleteFavorite(vararg user: UserEntity)

    @Query("SELECT * FROM tb_user")
    fun getListFavorite(): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM tb_user WHERE id=:id")
    fun checkFavorite (id:Int): Flow<Int>
}