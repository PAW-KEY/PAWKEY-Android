package com.paw.key.data.di

import com.paw.key.data.service.ArchivedListService
import com.paw.key.data.service.LikeService
import com.paw.key.data.service.PetProfileService
import com.paw.key.data.service.region.RegionService
import com.paw.key.data.service.SavedListService
import com.paw.key.data.service.UserProfileService
import com.paw.key.data.service.filter.FilterOptionService
import com.paw.key.data.service.home.HomeRegionService
import com.paw.key.data.service.image.ImageService
import com.paw.key.data.service.list.PostsListService
import com.paw.key.data.service.login.LoginService
import com.paw.key.data.service.sharedwalk.SharedWalkService
import com.paw.key.data.service.user.UserService
import com.paw.key.data.service.walkcourse.WalkCourseService
import com.paw.key.data.service.walklist.WalkListDetailService
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
    fun providesRegionService(retrofit: Retrofit ): RegionService =
        retrofit.create()

    @Provides
    @Singleton
    fun providesWalkCourseService(retrofit: Retrofit ): WalkCourseService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideUserInfoService(retrofit: Retrofit): UserService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideSharedWalkService(retrofit: Retrofit): SharedWalkService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideHomeRegionService(retrofit: Retrofit): HomeRegionService =
        retrofit.create()

    //마이페이지
    @Provides
    @Singleton
    fun provideUserProfileService(retrofit: Retrofit): UserProfileService =
        retrofit.create()

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
    fun provideArchivedListService(retrofit: Retrofit): ArchivedListService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideLikeService(retrofit: Retrofit): LikeService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideWalkReviewService(retrofit: Retrofit): WalkReviewService =
        retrofit.create()

    // 리뷰
    @Provides
    @Singleton
    fun provideWalkListDetailService(retrofit: Retrofit): WalkListDetailService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideFilterOptionService(retrofit: Retrofit): FilterOptionService =
        retrofit.create()

    @Provides
    @Singleton
    fun providePostsListService(retrofit: Retrofit): PostsListService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideImageService(retrofit: Retrofit): ImageService =
        retrofit.create()
}