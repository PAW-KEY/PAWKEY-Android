package com.paw.key.data.di

import com.paw.key.data.service.DummyService
import com.paw.key.data.service.RegionService
import com.paw.key.data.service.walkcourse.WalkCourseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesDummyService(retrofit: Retrofit ): DummyService =
        retrofit.create()

    @Provides
    @Singleton
    fun providesRegionService(retrofit: Retrofit ): RegionService =
        retrofit.create()

    @Provides
    @Singleton
    fun providesWalkCourseService(retrofit: Retrofit ): WalkCourseService =
        retrofit.create()

}