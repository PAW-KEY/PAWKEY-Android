package com.paw.key.data.di

import com.paw.key.data.service.ArchivedListService
import com.paw.key.data.service.LikeService
import com.paw.key.data.service.SavedListService
import com.paw.key.data.service.auth.ReissueService
import com.paw.key.data.service.home.HomeRegionService
import com.paw.key.data.service.image.ImageService
import com.paw.key.data.service.image.S3Service
import com.paw.key.data.service.login.LoginService
import com.paw.key.data.service.posts.PostsService
import com.paw.key.data.service.region.RegionService
import com.paw.key.data.service.sharedwalk.SharedWalkService
import com.paw.key.data.service.user.UserService
import com.paw.key.data.service.walk.WalkService
import com.paw.key.data.service.walkpreparation.WalkPreparationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
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
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideImageService(retrofit: Retrofit): ImageService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideImageS3Service(@Named("s3") retrofit: Retrofit): S3Service =
        retrofit.create()

    @Provides
    @Singleton
    fun provideWalkPreparationService(retrofit: Retrofit): WalkPreparationService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideWalkService(retrofit: Retrofit): WalkService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideReissueService(@Named("auth") retrofit: Retrofit): ReissueService =
        retrofit.create()

    @Provides
    @Singleton
    fun providePostsService(retrofit: Retrofit): PostsService =
        retrofit.create()
}
