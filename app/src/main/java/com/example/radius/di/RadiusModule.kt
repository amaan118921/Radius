package com.example.radius.di

import android.app.Application
import androidx.room.Room
import com.example.radius.data.network.Api
import com.example.radius.data.repositories.RadiusRepository
import com.example.radius.domain.repositories.RadiusRepositoryImpl
import com.example.radius.helpers.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RadiusModule {

    @Provides
    @Singleton
    fun provideRepository(api: Api): RadiusRepository {
        return RadiusRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideApi(): Api {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL).build().create(Api::class.java)
    }



}