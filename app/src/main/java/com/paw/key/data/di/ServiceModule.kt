package com.paw.key.data.di

import com.paw.key.data.service.DummyService
import com.paw.key.data.service.PetProfileService
import com.paw.key.data.service.onboarding.OnboardingInfoService
import com.paw.key.data.service.onboarding.OnboardingPetsService
import com.paw.key.data.service.onboarding.OnboardingRegionService
import com.paw.key.data.service.RegionService
import com.paw.key.data.service.SavedListService
import com.paw.key.data.service.sharedwalk.SharedWalkService
import com.paw.key.data.service.home.HomeRegionService
import com.paw.key.data.service.walkcourse.WalkCourseService
import com.paw.key.data.service.walkreview.WalkReviewService
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
    fun provideSharedWalkService(retrofit: Retrofit): SharedWalkService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideHomeRegionService(retrofit: Retrofit): HomeRegionService =
        retrofit.create(HomeRegionService::class.java)

    //마이페이지
    @Provides
    @Singleton
    fun providePetProfileService(retrofit: Retrofit): PetProfileService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideSavedListService(retrofit: Retrofit): SavedListService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideWalkReviewService(retrofit: Retrofit): WalkReviewService =
        retrofit.create()


}