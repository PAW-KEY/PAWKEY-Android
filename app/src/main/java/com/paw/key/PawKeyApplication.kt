package com.paw.key

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.kakao.vectormap.KakaoMapSdk
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidApp
class PawKeyApplication : Application(), ImageLoaderFactory {
    @Inject
    @Named("kakao.native.key")
    lateinit var kakaoNativeKey: String // BuildConfig는 컴파일 타임에 생성되는 정적 클래스이기 때문에 Mocking이 불가능 = 테스트 용이성

    override fun onCreate() {
        super.onCreate()

        setTimber()
        setDarkMode()

        KakaoMapSdk.init(this, kakaoNativeKey)
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient(BuildConfig.NAVERMAP_CLIENT_ID)
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(50 * 1024 * 1024)
                    .build()
            }
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }
}