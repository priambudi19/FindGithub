package com.priambudi19.core.di

import androidx.room.Room
import com.priambudi19.core.BuildConfig
import com.priambudi19.core.data.source.local.LocalDataSource
import com.priambudi19.core.data.source.local.room.UserDatabase
import com.priambudi19.core.data.source.remote.RemoteDataSource
import com.priambudi19.core.data.source.remote.network.ApiService
import com.priambudi19.core.domain.repository.MainRepository
import com.priambudi19.core.domain.repository.MainRepositoryImpl
import com.priambudi19.core.domain.usecase.Interactor
import com.priambudi19.core.domain.usecase.UseCase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val roomModule = module {
    val myPassPhrase: ByteArray = SQLiteDatabase.getBytes("ini budi".toCharArray())
    val factory = SupportFactory(myPassPhrase)
    factory { get<UserDatabase>().userDao() }
    single {
        Room.databaseBuilder(androidContext(), UserDatabase::class.java, UserDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val certificatePinner = CertificatePinner.Builder()
            .add(HOST_NAME, "sha256/ORtIOYkm5k6Nf2tgAK/uwftKfNhJB3QS0Hs608SiRmE=")
            .add(HOST_NAME, "sha256/k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=")
            .add(HOST_NAME, "sha256/WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .certificatePinner(certificatePinner)
            .addInterceptor { chain ->
                val o = chain.request()
                val request = o.newBuilder()
                    .header("Authorization", BuildConfig.API_KEY) // build config = token [api_key]
                    .method(o.method, o.body)
                    .build()

                chain.proceed(request)
            }
            .cache(get())
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiService::class.java)
    }
    single {
        Cache(
            directory = File(androidContext().cacheDir, "http_cache"),
            maxSize = 50L * 1024L * 1024L
        )
    }
}

val repositoryModule = module {
    single {
        LocalDataSource(get())
    }
    single {
        RemoteDataSource(get())
    }
    single<MainRepository> {
        MainRepositoryImpl(get(),get())
    }
}
val useCaseModule = module {
    factory<UseCase> { Interactor(get()) }
}

private const val BASE_URL = "https://api.github.com/"
private const val HOST_NAME = "api.github.com"