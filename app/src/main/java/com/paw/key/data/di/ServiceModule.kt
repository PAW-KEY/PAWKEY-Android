package com.paw.key.data.di

import com.paw.key.data.service.DummyService
import com.paw.key.data.service.RegionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesDummyService(retrofit: Retrofit ): DummyService =
        retrofit.create(DummyService::class.java)

    @Provides
    @Singleton
    fun providesRegionService(retrofit: Retrofit ): RegionService =
        retrofit.create(RegionService::class.java)


}