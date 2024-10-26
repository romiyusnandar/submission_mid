package com.ryu.dicodingevents.di

import android.content.Context
import com.ryu.dicodingevents.data.retrofit.ApiClient
import com.ryu.dicodingevents.data.retrofit.ApiService
import com.ryu.dicodingevents.utils.ThemePreferences
import com.ryu.dicodingevents.utils.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = ApiClient.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl: String): ApiService =
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideThemePreferences(@ApplicationContext context: Context): ThemePreferences {
        return ThemePreferences(context.dataStore)
    }
}