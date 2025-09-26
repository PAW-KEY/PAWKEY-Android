package com.paw.key.core.designsystem.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// brand/iris
val Green50 = Color(0xFFEDFCE9)
val Green100 = Color(0xFFD7F7D0)
val Green200 = Color(0xFFB2F0A6)
val Green300 = Color(0xFF83E472)
val Green400 = Color(0xFF4CD137)
val Green500 = Color(0xFF39BA28)
val Green600 = Color(0xFF28941C)
val Green700 = Color(0xFF22711A)
val Green800 = Color(0xFF1F5A1A)
val Green900 = Color(0xFF1D4D1A)
val Green950 = Color(0xFF0A2A09)

// Gray Scale
val Gray5 = Color(0xFFFCFCFC)
val Gray25 = Color(0xFFF8F8F8)
val Gray50 = Color(0xFFEBEBEB)
val Gray100 = Color(0xFFDCDCDC)
val Gray200 = Color(0xFFBDBDBD)
val Gray300 = Color(0xFF989898)
val Gray400 = Color(0xFF7C7C7C)
val Gray500 = Color(0xFF656565)
val Gray600 = Color(0xFF525252)
val Gray700 = Color(0xFF464646)
val Gray800 = Color(0xFF3D3D3D)
val Gray900 = Color(0xFF292929)
val Gray950 = Color(0xFF161616)

//BW
val White1 = Color(0xFFFFFFFF)
val White2 = Color(0xFFF5F5F5)
val Black = Color(0xFF1E1E1E)

//System Color
val System_red = Color(0xFFEB210F)
val System_green = Color(0xFF40C927)


/*------------------------------------------------*/
// Brand
@SuppressLint("InvalidColorHexValue")
val Opacity5Primary = Color(0x0D00D281)
val Opacity25Primary = Color(0x4000D281)

val PrimaryGra1 = Color(0xFFE6FBF2)
val PrimaryGra2 = Color(0xFFCFF8E5)
val PrimaryGra3 = Color(0xFFB6F2D0)
val PrimaryGra4 = Color(0xFF97ECB8)
val PrimaryGra5 = Color(0xFF7BEFB4)
val Primary = Color(0xFF00D281)
val PrimaryGra6 = Color(0xFF00A86B)
val PrimaryGra7 = Color(0xFF007A50)

// contents
val Contents = Color(0xFF171717)

// background
val Background = Color(0xFFFFFFFF)

// default
val DefaultButton = Color(0xFFEEEEEE)
val DefaultMiddle = Color(0xFF9C9C9C)
val DefaultDark = Color(0xFF555555)



@Stable
class PawKeyColors(
    green50: Color,
    green100: Color,
    green200: Color,
    green300: Color,
    green400: Color,
    green500: Color,
    green600: Color,
    green700: Color,
    green800: Color,
    green900: Color,
    green950: Color,

    gray5: Color,
    gray25: Color,
    gray50: Color,
    gray100: Color,
    gray200: Color,
    gray300: Color,
    gray400: Color,
    gray500: Color,
    gray600: Color,
    gray700: Color,
    gray800: Color,
    gray900: Color,
    gray950: Color,

    white1: Color,
    white2: Color,
    black: Color,

    system_red: Color,
    system_green: Color,

    /*--------------------*/
    opacity5Primary: Color,
    opacity25Primary: Color,
    primaryGra1: Color,
    primaryGra2: Color,
    primaryGra3: Color,
    primaryGra4: Color,
    primaryGra5: Color,
    primaryGra6: Color,
    primaryGra7: Color,
    primary: Color,
    contents: Color,
    background: Color,
    defaultButton: Color,
    defaultMiddle: Color,
    defaultDark: Color,
) {
    var green50: Color by mutableStateOf(green50)
        private set
    var green100: Color by mutableStateOf(green100)
        private set
    var green200: Color by mutableStateOf(green200)
        private set
    var green300: Color by mutableStateOf(green300)
        private set
    var green400: Color by mutableStateOf(green400)
        private set
    var green500: Color by mutableStateOf(green500)
        private set
    var green600: Color by mutableStateOf(green600)
        private set
    var green700: Color by mutableStateOf(green700)
        private set
    var green800: Color by mutableStateOf(green800)
        private set
    var green900: Color by mutableStateOf(green900)
        private set
    var green950: Color by mutableStateOf(green950)
        private set


    var gray5: Color by mutableStateOf(gray5)
        private set
    var gray25: Color by mutableStateOf(gray25)
        private set
    var gray50: Color by mutableStateOf(gray50)
        private set
    var gray100: Color by mutableStateOf(gray100)
        private set
    var gray200: Color by mutableStateOf(gray200)
        private set
    var gray300: Color by mutableStateOf(gray300)
        private set
    var gray400: Color by mutableStateOf(gray400)
        private set
    var gray500: Color by mutableStateOf(gray500)
        private set
    var gray600: Color by mutableStateOf(gray600)
        private set
    var gray700: Color by mutableStateOf(gray700)
        private set
    var gray800: Color by mutableStateOf(gray800)
        private set
    var gray900: Color by mutableStateOf(gray900)
        private set
    var gray950: Color by mutableStateOf(gray950)
        private set
    var white1: Color by mutableStateOf(white1)
        private set
    var white2: Color by mutableStateOf(white2)
        private set
    var black: Color by mutableStateOf(black)
        private set
    var system_red: Color by mutableStateOf(system_red)
        private set
    var system_green: Color by mutableStateOf(system_green)
        private set

    /*----------------------------*/
    var opacity5Primary: Color by mutableStateOf(opacity5Primary)
        private set
    var opacity25Primary: Color by mutableStateOf(opacity25Primary)
        private set
    var primaryGra1: Color by mutableStateOf(primaryGra1)
        private set
    var primaryGra2: Color by mutableStateOf(primaryGra2)
        private set
    var primaryGra3: Color by mutableStateOf(primaryGra3)
        private set
    var primaryGra4: Color by mutableStateOf(primaryGra4)
        private set
    var primaryGra5: Color by mutableStateOf(primaryGra5)
        private set
    var primaryGra6: Color by mutableStateOf(primaryGra6)
        private set
    var primaryGra7: Color by mutableStateOf(primaryGra7)
        private set
    var primary: Color by mutableStateOf(primary)
        private set
    var contents: Color by mutableStateOf(contents)
        private set
    var background: Color by mutableStateOf(background)
        private set
    var defaultButton: Color by mutableStateOf(defaultButton)
        private set
    var defaultMiddle: Color by mutableStateOf(defaultMiddle)
        private set
    var defaultDark: Color by mutableStateOf(defaultDark)
        private set

    fun copy(
        gray5: Color = this.gray5,
        gray25: Color = this.gray25,
        gray50: Color = this.gray50,
        gray100: Color = this.gray100,
        gray200: Color = this.gray200,
        gray300: Color = this.gray300,
        gray400: Color = this.gray400,
        gray500: Color = this.gray500,
        gray600: Color = this.gray600,
        gray700: Color = this.gray700,
        gray800: Color = this.gray800,
        gray900: Color = this.gray900,
        gray950: Color = this.gray950,

        white1: Color = this.white1,
        white2: Color = this.white1,
        black: Color = this.black,

        system_red: Color = this.system_red,
        system_green: Color = this.system_green,

        /*----------------------------------*/
        opacity5Primary: Color = this.opacity5Primary,
        opacity25Primary: Color = this.opacity25Primary,
        primaryGra1: Color = this.primaryGra1,
        primaryGra2: Color = this.primaryGra2,
        primaryGra3: Color = this.primaryGra3,
        primaryGra4: Color = this.primaryGra4,
        primaryGra5: Color = this.primaryGra5,
        primaryGra6: Color = this.primaryGra6,
        primaryGra7: Color = this.primaryGra7,
        primary: Color = this.primary,
        contents: Color = this.contents,
        background: Color = this.background,
        defaultButton: Color = this.defaultButton,
        defaultMiddle: Color = this.defaultMiddle,
        defaultDark: Color = this.defaultDark,

        ): PawKeyColors = PawKeyColors(

        green50 = green50,
        green100 = green100,
        green200 = green200,
        green300 = green300,
        green400 = green400,
        green500 = green500,
        green600 = green600,
        green700 = green700,
        green800 = green800,
        green900 = green900,
        green950 = green950,

        gray5 = gray5,
        gray25 = gray25,
        gray50 = gray50,
        gray100 = gray100,
        gray200 = gray200,
        gray300 = gray300,
        gray400 = gray400,
        gray500 = gray500,
        gray600 = gray600,
        gray700 = gray700,
        gray800 = gray800,
        gray900 = gray900,
        gray950 = gray950,

        white1 = white1,
        white2 = white2,
        black = black,

        system_red = system_red,
        system_green = system_green,

        /*-------------------------------*/
        opacity5Primary = opacity5Primary,
        opacity25Primary = opacity25Primary,
        primaryGra1 = primaryGra1,
        primaryGra2 = primaryGra2,
        primaryGra3 = primaryGra3,
        primaryGra4 = primaryGra4,
        primaryGra5 = primaryGra5,
        primaryGra6 = primaryGra6,
        primaryGra7 = primaryGra7,
        primary = primary,
        contents = contents,
        background = background,
        defaultButton = defaultButton,
        defaultMiddle = defaultMiddle,
        defaultDark = defaultDark,
    )

    fun update(other: PawKeyColors) {
        green50 = other.green50
        green100 = other.green100
        green200 = other.green200
        green300 = other.green300
        green400 = other.green400
        green500 = other.green500
        green600 = other.green600
        green700 = other.green700
        green800 = other.green800
        green900 = other.green900

        gray5 = other.gray5
        gray25 = other.gray25
        gray50 = other.gray50
        gray100 = other.gray100
        gray200 = other.gray200
        gray300 = other.gray300
        gray400 = other.gray400
        gray500 = other.gray500
        gray600 = other.gray600
        gray700 = other.gray700
        gray800 = other.gray800
        gray900 = other.gray900
        gray950 = other.gray950

        white1 = other.white1
        white2 = other.white2
        black = other.black

        system_red = other.system_red
        system_green = other.system_green

        /*-------------------------------*/
        opacity5Primary = other.opacity5Primary
        opacity25Primary = other.opacity25Primary
        primaryGra1 = other.primaryGra1
        primaryGra2 = other.primaryGra2
        primaryGra3 = other.primaryGra3
        primaryGra4 = other.primaryGra4
        primaryGra5 = other.primaryGra5
        primaryGra6 = other.primaryGra6
        primaryGra7 = other.primaryGra7
        primary = other.primary
        contents = other.contents
        background = other.background
        defaultButton = other.defaultButton
        defaultMiddle = other.defaultMiddle
        defaultDark = other.defaultDark
    }
}

fun pawKeyColors(
    green50: Color = Green50,
    green100: Color = Green100,
    green200: Color = Green200,
    green300: Color = Green300,
    green400: Color = Green400,
    green500: Color = Green500,
    green600: Color = Green600,
    green700: Color = Green700,
    green800: Color = Green800,
    green900: Color = Green900,
    green950: Color = Green950,

    gray5: Color = Gray5,
    gray25: Color = Gray25,
    gray50: Color = Gray50,
    gray100: Color = Gray100,
    gray200: Color = Gray200,
    gray300: Color = Gray300,
    gray400: Color = Gray400,
    gray500: Color = Gray500,
    gray600: Color = Gray600,
    gray700: Color = Gray700,
    gray800: Color = Gray800,
    gray900: Color = Gray900,
    gray950: Color = Gray950,

    white1: Color = White1,
    white2: Color = White2,
    black: Color = Black,

    system_red: Color = System_red,
    system_green: Color = System_green,

    /*---------------------------------*/
    opacity5Primary: Color = Opacity5Primary,
    opacity25Primary: Color = Opacity25Primary,
    primaryGra1: Color = PrimaryGra1,
    primaryGra2: Color = PrimaryGra2,
    primaryGra3: Color = PrimaryGra3,
    primaryGra4: Color = PrimaryGra4,
    primaryGra5: Color = PrimaryGra5,
    primaryGra6: Color = PrimaryGra6,
    primaryGra7: Color = PrimaryGra7,
    primary: Color = Primary,
    contents: Color = Contents,
    background: Color = Background,
    defaultButton: Color = DefaultButton,
    defaultMiddle: Color = DefaultMiddle,
    defaultDark: Color = DefaultDark,
) = PawKeyColors(
    green50 = green50,
    green100 = green100,
    green200 = green200,
    green300 = green300,
    green400 = green400,
    green500 = green500,
    green600 = green600,
    green700 = green700,
    green800 = green800,
    green900 = green900,
    green950 = green950,

    gray5 = gray5,
    gray25 = gray25,
    gray50 = gray50,
    gray100 = gray100,
    gray200 = gray200,
    gray300 = gray300,
    gray400 = gray400,
    gray500 = gray500,
    gray600 = gray600,
    gray700 = gray700,
    gray800 = gray800,
    gray900 = gray900,
    gray950 = gray950,

    white1 = white1,
    white2 = white2,
    black = black,

    system_red = system_red,
    system_green = system_green,

    /*----------------------------*/
    opacity5Primary = opacity5Primary,
    opacity25Primary = opacity25Primary,
    primaryGra1 = primaryGra1,
    primaryGra2 = primaryGra2,
    primaryGra3 = primaryGra3,
    primaryGra4 = primaryGra4,
    primaryGra5 = primaryGra5,
    primaryGra6 = primaryGra6,
    primaryGra7 = primaryGra7,
    primary = primary,
    contents = contents,
    background = background,
    defaultButton = defaultButton,
    defaultMiddle = defaultMiddle,
    defaultDark = defaultDark,
)