package com.paw.key.presentation.ui.course.walkcourse.util

import android.graphics.Bitmap
import android.opengl.GLException
import java.nio.IntBuffer
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.microedition.khronos.opengles.GL10

fun cropCenterWithAspectRatio(
    bitmap: Bitmap,
    targetAspectRatio: Float
): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val currentAspectRatio = width.toFloat() / height.toFloat()

    val cropWidth: Int
    val cropHeight: Int

    if (currentAspectRatio > targetAspectRatio) {
        // 현재 이미지가 더 넓음 → 좌우 잘라야 함
        cropHeight = height
        cropWidth = (height * targetAspectRatio).toInt()
    } else {
        // 현재 이미지가 더 높음 → 위아래 잘라야 함
        cropWidth = width
        cropHeight = (width / targetAspectRatio).toInt()
    }

    val startX = ((width - cropWidth) / 2).coerceAtLeast(0)
    val startY = ((height - cropHeight) / 2).coerceAtLeast(0)

    val safeWidth = minOf(cropWidth, width - startX)
    val safeHeight = minOf(cropHeight, height - startY)

    return Bitmap.createBitmap(bitmap, startX, startY, safeWidth, safeHeight)
}


fun createBitmapFromGLSurface(x: Int, y: Int, w: Int, h: Int, gl: GL10): Bitmap? {
    val bitmapBuffer = IntArray(w * h)
    val bitmapSource = IntArray(w * h)
    val intBuffer = IntBuffer.wrap(bitmapBuffer)
    intBuffer.position(0)

    try {
        gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer)
        var offset1: Int
        var offset2: Int

        for (i in 0 until h) {
            offset1 = i * w
            offset2 = (h - i - 1) * w

            for (j in 0 until w) {
                val texturePixel = bitmapBuffer[offset1 + j]
                val blue = (texturePixel shr 16) and 0xff
                val red = (texturePixel shl 16) and 0x00ff0000
                val pixel = (texturePixel and 0xff00ff00.toInt()) or red or blue
                bitmapSource[offset2 + j] = pixel
            }
        }
    } catch (e: GLException) {
        return null
    } catch (e: OutOfMemoryError) {
        return null
    }

    // 전체 비트맵 생성
    val fullBitmap = Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888)

    val targetAspectRatio = 16f / 11f

    var cropWidth: Int
    var cropHeight: Int

    val currentAspectRatio = w.toFloat() / h.toFloat()

    if (currentAspectRatio > targetAspectRatio) {
        cropHeight = h
        cropWidth = (h * targetAspectRatio).toInt()
    } else {
        cropWidth = w
        cropHeight = (w / targetAspectRatio).toInt()
    }

    val startX = ((w - cropWidth) / 2).coerceAtLeast(0)
    val startY = ((h - cropHeight) / 2).coerceAtLeast(0)

    val safeWidth = minOf(cropWidth, w - startX)
    val safeHeight = minOf(cropHeight, h - startY)

    // 잘라낸 비트맵 반환
    return Bitmap.createBitmap(fullBitmap, startX, startY, safeWidth, safeHeight)
}

fun formatTime(millis: Long): String {
    val totalSeconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    //val hours = TimeUnit.SECONDS.toHours(totalSeconds)
    val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60
    val seconds = totalSeconds % 60

    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

fun formatDistance(distance: Float): String {
    val distanceToKm = distance / 1000
    return String.format(Locale.getDefault(), "%.1f km", distanceToKm)
}