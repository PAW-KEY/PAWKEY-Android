package com.paw.key.core.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// brand/iris
val Beige50 = Color(0xFFFAF6F2)
val Beige100 = Color(0xFFF4EBE0)
val Beige200 = Color(0xFFE8D5C0)
val Beige300 = Color(0xFFDDC0A2)
val Beige400 = Color(0xFFC9976E)
val Beige500 = Color(0xFFBE7E51)
val Beige600 = Color(0xFFB06A46)
val Beige700 = Color(0xFF93553B)
val Beige800 = Color(0xFF774635)
val Beige900 = Color(0xFF603B2E)
val Beige950 = Color(0xFF331D17)

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


@Stable
class PawKeyColors(
    beige50: Color,
    beige100: Color,
    beige200: Color,
    beige300: Color,
    beige400: Color,
    beige500: Color,
    beige600: Color,
    beige700: Color,
    beige800: Color,
    beige900: Color,
    beige950: Color,

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
) {
    var beige50: Color by mutableStateOf(beige50)
        private set
    var beige100: Color by mutableStateOf(beige100)
        private set
    var beige200: Color by mutableStateOf(beige200)
        private set
    var beige300: Color by mutableStateOf(beige300)
        private set
    var beige400: Color by mutableStateOf(beige400)
        private set
    var beige500: Color by mutableStateOf(beige500)
        private set
    var beige600: Color by mutableStateOf(beige600)
        private set
    var beige700: Color by mutableStateOf(beige700)
        private set
    var beige800: Color by mutableStateOf(beige800)
        private set
    var beige900: Color by mutableStateOf(beige900)
        private set
    var beige950: Color by mutableStateOf(beige950)
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


    fun copy(
        beige50: Color = this.beige50,
        beige100: Color = this.beige100,
        beige200: Color = this.beige200,
        beige300: Color = this.beige300,
        beige400: Color = this.beige400,
        beige500: Color = this.beige500,
        beige600: Color = this.beige600,
        beige700: Color = this.beige700,
        beige800: Color = this.beige800,
        beige900: Color = this.beige900,
        beige950: Color = this.beige950,

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


        ): PawKeyColors = PawKeyColors(
        beige50 = beige50,
        beige100 = beige100,
        beige200 = beige200,
        beige300 = beige300,
        beige400 = beige400,
        beige500 = beige500,
        beige600 = beige600,
        beige700 = beige700,
        beige800 = beige800,
        beige900 = beige900,
        beige950 = beige950,

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
        system_green = system_green
    )

    fun update(other: PawKeyColors) {
        beige50 = other.beige50
        beige100 = other.beige100
        beige200 = other.beige200
        beige300 = other.beige300
        beige400 = other.beige400
        beige500 = other.beige500
        beige600 = other.beige600
        beige700 = other.beige700
        beige800 = other.beige800
        beige900 = other.beige900

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
    }
}

fun pawKeyColors(
    beige50: Color = Beige50,
    beige100: Color = Beige100,
    beige200: Color = Beige200,
    beige300: Color = Beige300,
    beige400: Color = Beige400,
    beige500: Color = Beige500,
    beige600: Color = Beige600,
    beige700: Color = Beige700,
    beige800: Color = Beige800,
    beige900: Color = Beige900,
    beige950: Color = Beige950,

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


    ) = PawKeyColors(
    beige50 = beige50,
    beige100 = beige100,
    beige200 = beige200,
    beige300 = beige300,
    beige400 = beige400,
    beige500 = beige500,
    beige600 = beige600,
    beige700 = beige700,
    beige800 = beige800,
    beige900 = beige900,
    beige950 = beige950,

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
    system_green = system_green


)