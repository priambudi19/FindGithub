package com.priambudi19.core

import android.app.Application
import com.priambudi19.core.di.networkModule
import com.priambudi19.core.di.repositoryModule
import com.priambudi19.core.di.roomModule
import com.priambudi19.core.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class CoreApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CoreApp)
            modules(listOf(*defaultModules(), *getDataModules()))
        }
    }

    private fun defaultModules(): Array<Module> =
        arrayOf(
            useCaseModule,
            repositoryModule,
            roomModule,
            networkModule,)


    abstract fun getDataModules(): Array<Module>
}