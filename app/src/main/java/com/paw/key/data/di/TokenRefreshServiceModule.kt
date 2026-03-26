package com.paw.key.data.di

import com.paw.key.data.network.TokenRefreshService
import com.paw.key.data.network.TokenRefreshServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenRefreshServiceModule {

    @Binds
    @Singleton
    abstract fun bindTokenRefreshService(tokenRefreshServiceImpl: TokenRefreshServiceImpl): TokenRefreshService
}
