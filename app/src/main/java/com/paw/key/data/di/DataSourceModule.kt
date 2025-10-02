package com.paw.key.data.di

import com.paw.key.data.local.datasource.UserLocalDataSource
import com.paw.key.data.local.datasourceimpl.UserLocalDataSourceImpl
import com.paw.key.data.remote.datasource.datasourceimpl.AuthRemoteDataSourceImpl
import com.paw.key.data.remote.datasource.datasourceimpl.GoogleAuthDataSourceImpl
import com.paw.key.data.remote.datasource.login.AuthRemoteDataSource
import com.paw.key.data.remote.datasource.login.GoogleAuthDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsUserDatasource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ) : UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsAuthDatasource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ) : AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsGoogleDataSource(
        googleAuthDataSourceImpl: GoogleAuthDataSourceImpl
    ) : GoogleAuthDataSource
}