package com.paw.key.data.di

import com.paw.key.data.repositoryimpl.DummyRepositoryImpl
import com.paw.key.data.repositoryimpl.onboarding.OnboardingInfoRepositoryImpl
import com.paw.key.data.repositoryimpl.onboarding.OnboardingRegionRepositoryImpl
import com.paw.key.data.repositoryimpl.onboarding.OnboardingRepositoryImpl
import com.paw.key.data.repositoryimpl.RegionRepositoryImpl
import com.paw.key.data.repositoryimpl.WalkCourseRepositoryImpl
import com.paw.key.data.repositoryimpl.WalkSharedResultRepositoryImpl
import com.paw.key.data.repositoryimpl.home.HomeRegionRepositoryImpl
import com.paw.key.domain.repository.DummyRepository
import com.paw.key.domain.repository.onboarding.OnboardingInfoRepository
import com.paw.key.domain.repository.onboarding.OnboardingRegionRepository
import com.paw.key.domain.repository.onboarding.OnboardingRepository
import com.paw.key.domain.repository.RegionRepository
import com.paw.key.domain.repository.WalkSharedResultRepository
import com.paw.key.domain.repository.home.HomeRegionRepository
import com.paw.key.domain.repository.walkcourse.WalkCourseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsDummyRepository(
        dummyRepositoryImpl: DummyRepositoryImpl
    ): DummyRepository

    @Binds
    @Singleton
    fun bindsSharedWalkResultRepository(
        walkSharedResultRepositoryImpl: WalkSharedResultRepositoryImpl
    ): WalkSharedResultRepository

    /*Home*/
    @Binds
    @Singleton
    fun bindsRegionRepository(
        regionRepositoryImpl: RegionRepositoryImpl
    ): RegionRepository

    @Binds
    @Singleton
    fun bindsWalkCourseRepository(
        walkCourseRepositoryImpl: WalkCourseRepositoryImpl
    ): WalkCourseRepository
  
    @Binds
    @Singleton
    fun bindOnboardingRepository(
        impl: OnboardingRepositoryImpl
    ): OnboardingRepository

    @Binds
    @Singleton
    fun bindOnboardingRegionRepository(
        impl: OnboardingRegionRepositoryImpl
    ): OnboardingRegionRepository

    @Binds
    @Singleton
    fun bindOnboardingInfoRepository(
        impl: OnboardingInfoRepositoryImpl
    ): OnboardingInfoRepository

    @Binds
    @Singleton
    fun bindHomeRegionRepository(
        impl: HomeRegionRepositoryImpl
    ): HomeRegionRepository
}