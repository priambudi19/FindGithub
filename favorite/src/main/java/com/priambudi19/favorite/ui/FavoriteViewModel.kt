package com.priambudi19.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.priambudi19.core.domain.usecase.UseCase

class FavoriteViewModel(private val usecase : UseCase): ViewModel() {
    fun getListFavorite() = usecase.getListFavorite().asLiveData()
}