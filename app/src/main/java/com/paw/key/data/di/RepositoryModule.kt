package com.paw.key.data.di

import com.paw.key.data.repositoryimpl.DummyRepositoryImpl
import com.paw.key.data.repositoryimpl.WalkSharedResultRepositoryImpl
import com.paw.key.domain.repository.DummyRepository
import com.paw.key.domain.repository.WalkSharedResultRepository
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

}