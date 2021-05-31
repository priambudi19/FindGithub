package com.priambudi19.findgithub.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.priambudi19.core.domain.usecase.UseCase

class MainViewModel(private val useCase: UseCase) : ViewModel() {
    fun getSearchUser(username: String) =
        liveData { emitSource(useCase.searchUser(username).asLiveData()) }
}