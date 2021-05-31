package com.priambudi19.favorite.di

import com.priambudi19.favorite.ui.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel{ FavoriteViewModel(get()) }
}