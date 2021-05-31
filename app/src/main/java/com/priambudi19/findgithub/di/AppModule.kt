package com.priambudi19.findgithub.di


import com.priambudi19.findgithub.ui.detail.DetailViewModel
import com.priambudi19.findgithub.ui.search.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DetailViewModel(get()) }
    viewModel { MainViewModel(get()) }
}
