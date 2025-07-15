package com.paw.key.core.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class PhotoUtils {
    companion object {
        private const val MAX_WIDTH = 800
        private const val MAX_HEIGHT = 600
        private const val DEFAULT_QUALITY = 80

        private fun resizeBitmapIfNeeded(bitmap: Bitmap): Bitmap {
            val width = bitmap.width
            val height = bitmap.height

            // 이미 적절한 크기인 경우 원본 반환
            if (width <= MAX_WIDTH && height <= MAX_HEIGHT) {
                return bitmap
            }

            // 비율 계산
            val aspectRatio = width.toFloat() / height.toFloat()
            val (newWidth, newHeight) = if (aspectRatio > 1) {
                // 가로가 더 긴 경우
                val calculatedHeight = (MAX_WIDTH / aspectRatio).toInt()
                MAX_WIDTH to minOf(calculatedHeight, MAX_HEIGHT)
            } else {
                // 세로가 더 긴 경우
                val calculatedWidth = (MAX_HEIGHT * aspectRatio).toInt()
                minOf(calculatedWidth, MAX_WIDTH) to MAX_HEIGHT
            }

            return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        }

        fun createBitmapMultipart(
            bitmap: Bitmap,
            partName: String = "image",
            format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
            quality: Int = DEFAULT_QUALITY
        ): MultipartBody.Part? {
            return try {
                val bos = ByteArrayOutputStream()

                // Bitmap 최적화
                val optimizedBitmap = resizeBitmapIfNeeded(bitmap)

                // 압축
                val compressedSuccessfully = optimizedBitmap.compress(format, quality, bos)
                if (!compressedSuccessfully) {
                    Log.e("PhotoUtils", "Bitmap compression failed")
                    return null
                }

                val byteArray = bos.toByteArray()
                if (byteArray.isEmpty()) {
                    Log.e("PhotoUtils", "Compressed image data is empty")
                    return null
                }

                // MIME 타입 결정
                val mimeType = when (format) {
                    Bitmap.CompressFormat.PNG -> "image/png"
                    else -> "image/jpeg"
                }

                // 파일 확장자 결정
                val extension = when (format) {
                    Bitmap.CompressFormat.PNG -> "png"
                    else -> "jpg"
                }

                val requestBody = byteArray.toRequestBody(mimeType.toMediaTypeOrNull())

                // 원본과 다른 경우에만 리사이클
                if (optimizedBitmap != bitmap) {
                    optimizedBitmap.recycle()
                }

                bos.close()

                MultipartBody.Part.createFormData(
                    name = partName,
                    filename = "image_${System.currentTimeMillis()}.${extension}",
                    body = requestBody
                )

            } catch (e: Exception) {
                Log.e("PhotoUtils", "createBitmapMultipart - ${e.message}")
                null
            }
        }

        fun uriListToMultipartParts(
            uris: List<Uri>,
            contentResolver: ContentResolver
        ): List<MultipartBody.Part> {
            return uris.mapIndexed { index, uri ->
                val inputStream = contentResolver.openInputStream(uri) ?: throw IllegalArgumentException("Can't open URI: $uri")
                val fileName = "image_$index.jpg" // 혹은 uri에서 파일명 추출
                val requestBody = inputStream.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("images", fileName, requestBody)
            }
        }
    }
}