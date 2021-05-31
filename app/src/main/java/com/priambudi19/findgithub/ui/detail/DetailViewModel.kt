package com.priambudi19.findgithub.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.priambudi19.core.domain.model.User
import com.priambudi19.core.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val useCase: UseCase) : ViewModel() {
    suspend fun getDetailUser(username:String) = useCase.getDetailUser(username).asLiveData()

    fun deleteFavorite(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteUserFavorite(user)
        }
    }
    fun addToFavorite(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.setUserFavorite(user)
        }
    }

    fun checkFavorite(id:Int)=
        useCase.checkFavorite(id).asLiveData()

}