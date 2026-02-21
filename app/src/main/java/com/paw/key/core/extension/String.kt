package com.paw.key.core.extension

fun String.toBirthDateFormat(): String {
    val digits = this.filter { it.isDigit() }.take(8)
    return buildString {
        digits.forEachIndexed { index, c ->
            if (index == 4 || index == 6) append('-')
            append(c)
        }
    }
}