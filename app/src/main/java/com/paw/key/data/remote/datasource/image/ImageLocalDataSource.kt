package com.paw.key.data.remote.datasource.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import kotlin.math.max

class ImageLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getOptimizedFile(uriString: String): File {
        val uri = uriString.toUri()
        val dir = getDirectory()

        return compressToWebP(uri, dir)
    }

    fun clearCache() {
        getDirectory().listFiles()?.forEach { it.delete() }
    }

    private fun getDirectory(): File {
        return File(context.cacheDir, DIRECTORY).apply {
            if (!exists()) mkdirs()
        }
    }

    private fun compressToWebP(uri: Uri, dir: File): File {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.isMutableRequired = true

            val size = info.size
            val targetSize = calculateTargetSize(size.width, size.height)
            decoder.setTargetSize(targetSize.first, targetSize.second)
        }


        val format = if (Build.VERSION.SDK_INT >= 30) {
            Bitmap.CompressFormat.WEBP_LOSSY
        } else {
            Bitmap.CompressFormat.WEBP
        }

        val byteArray = ByteArrayOutputStream().use { stream ->
            bitmap.compress(format, WEBP_QUALITY, stream)
            bitmap.recycle()
            stream.toByteArray()
        }

        val tempFile = File(dir, "${UUID.randomUUID()}.webp")
        FileOutputStream(tempFile).use { it.write(byteArray) }

        return tempFile
    }

    private fun calculateTargetSize(width: Int, height: Int): Pair<Int, Int> {
        if (width <= MAX_SIZE && height <= MAX_SIZE) return width to height

        val ratio = max(width.toFloat() / MAX_SIZE, height.toFloat() / MAX_SIZE)
        return (width / ratio).toInt() to (height / ratio).toInt()
    }
    
    fun getImageSize(file: File): Pair<Int, Int> {
         val options = android.graphics.BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        android.graphics.BitmapFactory.decodeFile(file.absolutePath, options)
        return options.outWidth to options.outHeight
    }

    companion object {
        private const val DIRECTORY = "image_cache"
        private const val MAX_SIZE = 1024
        private const val WEBP_QUALITY = 80
    }
}