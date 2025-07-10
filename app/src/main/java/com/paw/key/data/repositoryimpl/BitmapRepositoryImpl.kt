package com.paw.key.data.repositoryimpl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paw.key.domain.repository.BitmapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : BitmapRepository {
    private object PreferencesKeys {
        val CAPTURED_BITMAP_BASE64 = stringPreferencesKey("captured_bitmap_base64")
    }

    override suspend fun saveBitmap(bitmap: Bitmap) {
        dataStore.edit { preferences ->
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            val byteArray = outputStream.toByteArray()
            val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)

            preferences[PreferencesKeys.CAPTURED_BITMAP_BASE64] = base64String
        }
    }

    override fun getSavedBitmap(): Flow<Bitmap?> {
        return dataStore.data.map { preferences ->
            val base64String = preferences[PreferencesKeys.CAPTURED_BITMAP_BASE64]

            if (!base64String.isNullOrEmpty()) {
                try {
                    val byteArray = Base64.decode(base64String, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                } catch (e: IllegalArgumentException) {
                    null
                }
            } else {
                null
            }
        }
    }

    override suspend fun clearSavedBitmap() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.CAPTURED_BITMAP_BASE64)
        }
    }
}