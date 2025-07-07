package com.paw.key.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val localPawKeyColors = staticCompositionLocalOf<PawKeyColors> {
    error("No PawKeyColors provided")
}

private val localPawKeyTypography = staticCompositionLocalOf<PawKeyTypography> {
    error("No PawKeyTypography provided")
}

object PawKeyTheme {
    val colors: PawKeyColors
        @Composable
        @ReadOnlyComposable
        get() = localPawKeyColors.current

    val typography: PawKeyTypography
        @Composable
        @ReadOnlyComposable
        get() = localPawKeyTypography.current
}

@Composable
fun ProvidePawKeyColorsAndTypography(
    colors: PawKeyColors,
    typography: PawKeyTypography,
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)

    val provideTypography = remember { typography.copy() }
    provideTypography.update(typography)

    CompositionLocalProvider(
        localPawKeyColors provides provideColors,
        localPawKeyTypography provides provideTypography,
        content = content
    )
}
/*
@Composable
fun DoggyWalkerAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = DoggyWalkerColors()
    val typography = DoggyWalkerTypography()

    ProvideDoggyWalkerColorsAndTypography(colors, typography) {
        MaterialTheme(content = content)
    }
}*/

@Composable
fun PawKeyTheme(
    content: @Composable () -> Unit
) {
    val colors = pawKeyColors()
    val typography = pawKeyTypography()

    ProvidePawKeyColorsAndTypography(colors, typography) {
        MaterialTheme(content = content)
    }
}