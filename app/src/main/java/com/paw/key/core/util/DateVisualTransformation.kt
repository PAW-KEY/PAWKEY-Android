package com.paw.key.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

// 날짜 텍스트 입력받아서 변환 텍스트 만들기
class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { it.isDigit() }

        val trimmed = if (digitsOnly.length > 8) digitsOnly.substring(0, 8) else digitsOnly

        val formattedText = buildString {
            trimmed.forEachIndexed { index, char ->
                append(char)
                if (index == 3 || index == 5) {
                    append('/')
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset >= 6 -> offset + 2
                    offset >= 4 -> offset + 1
                    else -> offset
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset >= 8 -> offset - 2 // yyyy/MM/dd
                    offset >= 5 -> offset - 1 // yyyy/MM
                    else -> offset
                }
            }
        }

        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }
}