package com.paw.key.data.di

import com.paw.key.data.service.DummyService
import com.paw.key.data.service.onboarding.OnboardingInfoService
import com.paw.key.data.service.onboarding.OnboardingPetsService
import com.paw.key.data.service.onboarding.OnboardingRegionService
import com.paw.key.data.service.RegionService
import com.paw.key.data.service.home.HomeRegionService
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

    @Provides
    @Singleton
    fun provideOnboardingPetsService(retrofit: Retrofit): OnboardingPetsService =
        retrofit.create(OnboardingPetsService::class.java)

    @Provides
    @Singleton
    fun provideOnboardingRegionService(retrofit: Retrofit): OnboardingRegionService =
        retrofit.create(OnboardingRegionService::class.java)

    @Provides
    @Singleton
    fun provideOnboardingInfoService(retrofit: Retrofit): OnboardingInfoService =
        retrofit.create(OnboardingInfoService::class.java)

    @Provides
    @Singleton
    fun provideHomeRegionService(retrofit: Retrofit): HomeRegionService =
        retrofit.create(HomeRegionService::class.java)


}