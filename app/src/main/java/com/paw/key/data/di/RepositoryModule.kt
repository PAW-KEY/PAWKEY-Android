package com.paw.key.data.di

import com.paw.key.data.repositoryimpl.BitmapRepositoryImpl
import com.paw.key.data.repositoryimpl.DummyRepositoryImpl
import com.paw.key.domain.repository.BitmapRepository
import com.paw.key.domain.repository.DummyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsDummyRepository(
        dummyRepositoryImpl: DummyRepositoryImpl
    ): DummyRepository

    @Binds
    fun bindsBitmapRepository(
        bitmapRepositoryImpl: BitmapRepositoryImpl
    ): BitmapRepository

}