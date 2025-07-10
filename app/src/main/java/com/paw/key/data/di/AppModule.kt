package com.paw.key.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.paw.key.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

val Context.bitmapDataStore: DataStore<Preferences> by preferencesDataStore(name = "captured_bitmap_prefs")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("kakao.native.key")
    fun provideKakaoNativeKey(): String {
        return BuildConfig.KAKAO_NATIVE_KEY
    }

    @Singleton
    @Provides
    fun provideBitmapDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.bitmapDataStore
    }
}