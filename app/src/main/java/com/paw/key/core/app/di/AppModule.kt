package com.paw.key.core.app.di

import com.paw.key.core.app.AppRestarter
import com.paw.key.core.app.AppRestarterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindAppRestarter(impl: AppRestarterImpl): AppRestarter
}
