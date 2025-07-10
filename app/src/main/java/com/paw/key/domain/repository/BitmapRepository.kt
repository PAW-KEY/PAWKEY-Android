package com.paw.key.domain.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface BitmapRepository {
    suspend fun saveBitmap(bitmap: Bitmap)
    fun getSavedBitmap(): Flow<Bitmap?>
    suspend fun clearSavedBitmap()
}