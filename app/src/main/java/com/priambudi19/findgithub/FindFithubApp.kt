package com.priambudi19.findgithub

import com.priambudi19.core.CoreApp
import com.priambudi19.findgithub.di.viewModelModule
import org.koin.core.module.Module

class FindFithubApp : CoreApp() {
    override fun getDataModules(): Array<Module> = arrayOf(viewModelModule)

}