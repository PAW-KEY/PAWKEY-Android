package com.paw.key

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidApp
class PawKeyApplication : Application() {
    @Inject
    @Named("kakao.native.key")
    lateinit var kakaoNativeKey: String

    override fun onCreate() {
        super.onCreate()

        setTimber()
        setDarkMode()

        KakaoMapSdk.init(this, kakaoNativeKey)
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}