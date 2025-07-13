package com.paw.key.data.di

import com.paw.key.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("kakao.native.key")
    fun provideKakaoNativeKey(): String {
        return BuildConfig.KAKAO_NATIVE_KEY
    }
}