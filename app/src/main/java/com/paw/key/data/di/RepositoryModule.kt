package com.paw.key.data.di

import com.paw.key.domain.repository.localstorage.LocalStorageRepository
import com.paw.key.data.repositoryimpl.localstorage.LocalStorageRepositoryImpl
import com.paw.key.data.remote.datasource.datasourceimpl.AuthRemoteDataSourceImpl
import com.paw.key.data.remote.datasource.datasourceimpl.GoogleAuthDataSourceImpl
import com.paw.key.data.remote.datasource.datasourceimpl.KakaoAuthDataSourceImpl
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.remote.datasource.login.GoogleAuthDataSource
import com.paw.key.data.remote.datasource.login.KakaoAuthDataSource
import com.paw.key.data.repositoryimpl.ArchivedListRepositoryImpl
import com.paw.key.data.repositoryimpl.LikeRepositoryImpl
import com.paw.key.data.repositoryimpl.PetProfileRepositoryImpl
import com.paw.key.data.repositoryimpl.RegionRepositoryImpl
import com.paw.key.data.repositoryimpl.SavedListRepositoryImpl
import com.paw.key.data.repositoryimpl.UserProfileRepositoryImpl
import com.paw.key.data.repositoryimpl.WalkCourseRepositoryImpl
import com.paw.key.data.repositoryimpl.WalkSharedResultRepositoryImpl
import com.paw.key.data.repositoryimpl.filter.FilterOptionRepositoryImpl
import com.paw.key.data.repositoryimpl.home.HomeRegionRepositoryImpl
import com.paw.key.data.repositoryimpl.home.RegionCurrentRepositoryImpl
import com.paw.key.data.repositoryimpl.image.ImageRepositoryImpl
import com.paw.key.data.repositoryimpl.list.PostsListRepositoryImpl
import com.paw.key.data.repositoryimpl.login.AuthRepositoryImpl
import com.paw.key.data.repositoryimpl.sharedwalk.SharedWalkRepositoryImpl
import com.paw.key.data.repositoryimpl.user.UserRepositoryImpl
import com.paw.key.data.repositoryimpl.walklist.WalkListDetailRepositoryImpl
import com.paw.key.data.repositoryimpl.walkreview.WalkReviewRepositoryImpl
import com.paw.key.domain.repository.ArchivedListRepository
import com.paw.key.domain.repository.LikeRepository
import com.paw.key.domain.repository.RegionRepository
import com.paw.key.domain.repository.SavedListRepository
import com.paw.key.domain.repository.WalkSharedResultRepository
import com.paw.key.domain.repository.filter.FilterOptionRepository
import com.paw.key.domain.repository.home.HomeRegionRepository
import com.paw.key.domain.repository.home.RegionCurrentRepository
import com.paw.key.domain.repository.image.ImageRepository
import com.paw.key.domain.repository.list.PostsListRepository
import com.paw.key.domain.repository.login.AuthRepository
import com.paw.key.domain.repository.petprofile.PetProfileRepository
import com.paw.key.domain.repository.sharedwalk.SharedWalkRepository
import com.paw.key.domain.repository.user.UserRepository
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
    @Singleton
    fun bindAuthRemoteDataSource(
        impl: AuthRemoteDataSourceImpl,
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    fun bindGoogleAuthDataSource(
        impl: GoogleAuthDataSourceImpl,
    ): GoogleAuthDataSource

    @Binds
    abstract fun bindKakaoAuthDataSource(
        impl: KakaoAuthDataSourceImpl
    ): KakaoAuthDataSource

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
    fun bindsUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

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

    @Binds
    @Singleton
    fun bindLoginRepository(
        impl: AuthRepositoryImpl
    ) : AuthRepository

    @Binds
    @Singleton
    fun bindImageRepository(
        impl: ImageRepositoryImpl
    ) : ImageRepository

    @Binds
    @Singleton
    abstract fun bindLocalStorageRepository(
        impl: LocalStorageRepositoryImpl
    ): LocalStorageRepository
}