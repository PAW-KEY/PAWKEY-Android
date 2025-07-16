package com.paw.key.data.di

import com.paw.key.data.repositoryimpl.ArchivedListRepositoryImpl
import com.paw.key.data.repositoryimpl.DummyRepositoryImpl
import com.paw.key.data.repositoryimpl.LikeRepositoryImpl
import com.paw.key.data.repositoryimpl.PetProfileRepositoryImpl
import com.paw.key.data.repositoryimpl.onboarding.OnboardingInfoRepositoryImpl
import com.paw.key.data.repositoryimpl.onboarding.OnboardingRegionRepositoryImpl
import com.paw.key.data.repositoryimpl.onboarding.OnboardingRepositoryImpl
import com.paw.key.data.repositoryimpl.RegionRepositoryImpl
import com.paw.key.data.repositoryimpl.SavedListRepositoryImpl
import com.paw.key.data.repositoryimpl.UserProfileRepositoryImpl
import com.paw.key.data.repositoryimpl.WalkCourseRepositoryImpl
import com.paw.key.data.repositoryimpl.WalkSharedResultRepositoryImpl
import com.paw.key.data.repositoryimpl.filter.FilterOptionRepositoryImpl
import com.paw.key.data.repositoryimpl.sharedwalk.SharedWalkRepositoryImpl
import com.paw.key.data.repositoryimpl.home.HomeRegionRepositoryImpl
import com.paw.key.data.repositoryimpl.home.RegionCurrentRepositoryImpl
import com.paw.key.data.repositoryimpl.list.PostsListRepositoryImpl
import com.paw.key.data.repositoryimpl.walklist.WalkListDetailRepositoryImpl
import com.paw.key.data.repositoryimpl.walkreview.WalkReviewRepositoryImpl
import com.paw.key.domain.repository.ArchivedListRepository
import com.paw.key.domain.repository.DummyRepository
import com.paw.key.domain.repository.LikeRepository
import com.paw.key.domain.repository.onboarding.OnboardingInfoRepository
import com.paw.key.domain.repository.onboarding.OnboardingRegionRepository
import com.paw.key.domain.repository.onboarding.OnboardingRepository
import com.paw.key.domain.repository.RegionRepository
import com.paw.key.domain.repository.SavedListRepository
import com.paw.key.domain.repository.WalkSharedResultRepository
import com.paw.key.domain.repository.filter.FilterOptionRepository
import com.paw.key.domain.repository.sharedwalk.SharedWalkRepository
import com.paw.key.domain.repository.home.HomeRegionRepository
import com.paw.key.domain.repository.home.RegionCurrentRepository
import com.paw.key.domain.repository.list.PostsListRepository
import com.paw.key.domain.repository.petprofile.PetProfileRepository
import com.paw.key.domain.repository.userprofile.UserProfileRepository
import com.paw.key.domain.repository.walkcourse.WalkCourseRepository
import com.paw.key.domain.repository.walklist.WalkListRepository
import com.paw.key.domain.repository.walkreview.WalkReviewRepository
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

    /*공유 코스*/
    @Binds
    @Singleton
    fun bindsSharedWalkRepository(
        impl: SharedWalkRepositoryImpl
    ) : SharedWalkRepository

    @Binds
    @Singleton
    fun bindHomeRegionRepository(
        impl: HomeRegionRepositoryImpl
    ): HomeRegionRepository

    //마이페이지
    @Binds
    @Singleton
    fun bindUserProfileRepository(
        impl: UserProfileRepositoryImpl
    ): UserProfileRepository

    @Binds
    @Singleton
    fun bindPetProfileRepository(
        impl: PetProfileRepositoryImpl
    ): PetProfileRepository

    @Binds
    @Singleton
    fun bindSavedListRepository(
        impl: SavedListRepositoryImpl
    ): SavedListRepository

    @Binds
    @Singleton
    fun bindArchivedListRepository(
        impl: ArchivedListRepositoryImpl
    ): ArchivedListRepository

    @Binds
    @Singleton
    fun bindLikeRepository(
        impl: LikeRepositoryImpl
    ): LikeRepository

    @Binds
    @Singleton
    fun bindWalkReviewRepository(
        impl: WalkReviewRepositoryImpl
    ) : WalkReviewRepository

    // 리뷰
    @Binds
    @Singleton
    fun bindWalkListDetailRepository(
        impl: WalkListDetailRepositoryImpl
    ) : WalkListRepository

    @Binds
    @Singleton
    fun bindFilterOptionRepository(
        impl: FilterOptionRepositoryImpl
    ) : FilterOptionRepository

    //게시물 리스트
    @Binds
    @Singleton
    fun bindPostsListRepository(
        impl: PostsListRepositoryImpl
    ) : PostsListRepository

    @Binds
    @Singleton
    fun bindRegionCurrentRepository(
        impl: RegionCurrentRepositoryImpl
    ) : RegionCurrentRepository
}