package com.priambudi19.findgithub

import android.app.Application
import com.priambudi19.core.di.networkModule
import com.priambudi19.core.di.repositoryModule
import com.priambudi19.core.di.roomModule
import com.priambudi19.core.di.useCaseModule
import com.priambudi19.findgithub.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FindFithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@FindFithubApp)
            modules(
                listOf(
                    useCaseModule,
                    repositoryModule,
                    roomModule,
                    networkModule,
                    viewModelModule
                )
            )
        }
    }
}