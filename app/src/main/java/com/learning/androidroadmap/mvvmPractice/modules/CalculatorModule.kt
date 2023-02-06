package com.learning.androidroadmap.mvvmPractice.modules

import com.learning.androidroadmap.mvvmPractice.*
import com.learning.androidroadmap.mvvmPractice.databasecomponents.UserDatabase
import com.learning.androidroadmap.mvvmPractice.retrofit.UseRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val calculatorModule = module {
    single { Calculator() }
}

val retrofitModule = module {
    single { UseRetrofit() }
}

val dbModule = module {
    factory { UserDatabase.getDatabase(androidContext()) }
}


val sharedPreferencesModule = module {
    single(named(USER_CRED_PREF)) { getNewEncryptedPreferences(androidContext()) }
}

val getDataFromSharedPreferencesModule = module {
    single(named(LOGIN_PREF)) { savedLoginInfo(androidContext()) }
}

val getRetrofit = module {
    single<Retrofit> {  Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(base_url)
        .build() }
}