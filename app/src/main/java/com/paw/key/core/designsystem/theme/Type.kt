package com.paw.key.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.paw.key.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))

// Todo : 네이밍과 함께 나중에 lineHeight 등 변경 예정
@Stable
class PawKeyTypography internal constructor(
    head24B: TextStyle,
    head24Sb: TextStyle,
    head22B: TextStyle,
    head22Sb: TextStyle,
    head20B1: TextStyle,
    head20B2: TextStyle,
    head20Sb: TextStyle,
    head18Sb: TextStyle,
    body16Sb: TextStyle,
    body16M: TextStyle,
    body14Sb: TextStyle,
    body14M: TextStyle,
    body14R: TextStyle,
    caption12Sb1: TextStyle,
    caption12Sb2: TextStyle,
    caption12M: TextStyle,
    caption12R: TextStyle,
    /*--- 이 위에는 전체 디자인 확정 시 전부 삭제 예정 지금은 오류 방지용으로 남김*/
    header1: TextStyle,
    header2: TextStyle,
    header3: TextStyle,
    subTitle: TextStyle,
    bodyDefault: TextStyle,
    bodyActive: TextStyle,
    bodySmall: TextStyle,
    bodyBold: TextStyle,
    mainButtonDefault: TextStyle,
    mainButtonActive: TextStyle,
    subButtonDefault: TextStyle,
    subButtonActive: TextStyle,
    buttonSmall: TextStyle,
    buttonLink: TextStyle,
) {
    var head24B: TextStyle by mutableStateOf(head24B)
        private set
    var head24Sb: TextStyle by mutableStateOf(head24B)
        private set
    var head22B: TextStyle by mutableStateOf(head22B)
        private set
    var head22Sb: TextStyle by mutableStateOf(head22Sb)
        private set
    var head20B1: TextStyle by mutableStateOf(head20B1)
        private set
    var head20B2: TextStyle by mutableStateOf(head20B2)
        private set
    var head20Sb: TextStyle by mutableStateOf(head20Sb)
        private set
    var head18Sb: TextStyle by mutableStateOf(head18Sb)
        private set
    var body16Sb: TextStyle by mutableStateOf(body16Sb)
        private set
    var body16M: TextStyle by mutableStateOf(body16M)
        private set
    var body14Sb: TextStyle by mutableStateOf(body14Sb)
        private set
    var body14M: TextStyle by mutableStateOf(body14M)
        private set
    var body14R: TextStyle by mutableStateOf(body14R)
        private set
    var caption12Sb1: TextStyle by mutableStateOf(caption12Sb1)
        private set
    var caption12Sb2: TextStyle by mutableStateOf(caption12Sb2)
        private set
    var caption12M: TextStyle by mutableStateOf(caption12M)
        private set
    var caption12R: TextStyle by mutableStateOf(caption12R)
        private set

    /*------------------------------------------------------------*/
    var header1: TextStyle by mutableStateOf(header1)
        private set
    var header2: TextStyle by mutableStateOf(header2)
        private set
    var header3: TextStyle by mutableStateOf(header3)
        private set
    var subTitle: TextStyle by mutableStateOf(subTitle)
        private set
    var bodyDefault: TextStyle by mutableStateOf(bodyDefault)
        private set
    var bodyActive: TextStyle by mutableStateOf(bodyActive)
        private set
    var bodySmall: TextStyle by mutableStateOf(bodySmall)
        private set
    var bodyBold: TextStyle by mutableStateOf(bodyBold)
        private set
    var mainButtonDefault: TextStyle by mutableStateOf(mainButtonDefault)
        private set
    var mainButtonActive: TextStyle by mutableStateOf(mainButtonActive)
        private set
    var subButtonDefault: TextStyle by mutableStateOf(subButtonDefault)
        private set
    var subButtonActive: TextStyle by mutableStateOf(subButtonActive)
        private set
    var buttonSmall: TextStyle by mutableStateOf(buttonSmall)
        private set
    var buttonLink: TextStyle by mutableStateOf(buttonLink)
        private set

    fun copy(
        head24B: TextStyle = this.head24B,
        head24Sb: TextStyle = this.head24Sb,
        head22B: TextStyle = this.head22B,
        head22Sb: TextStyle = this.head22Sb,
        head20B1: TextStyle = this.head20B1,
        head20B2: TextStyle = this.head20B2,
        head20Sb: TextStyle = this.head20Sb,
        head18Sb: TextStyle = this.head18Sb,
        body16Sb: TextStyle = this.body16Sb,
        body16M: TextStyle = this.body16M,
        body14Sb: TextStyle = this.body14Sb,
        body14M: TextStyle = this.body14M,
        body14R: TextStyle = this.body14R,
        caption12Sb1: TextStyle = this.caption12Sb1,
        caption12Sb2: TextStyle = this.caption12Sb2,
        caption12M: TextStyle = this.caption12M,
        caption12R: TextStyle = this.caption12R,
        /*----------------------------------*/
        header1: TextStyle = this.header1,
        header2: TextStyle = this.header2,
        header3: TextStyle = this.header3,
        subTitle: TextStyle = this.subTitle,
        bodyDefault: TextStyle = this.bodyDefault,
        bodyActive: TextStyle = this.bodyActive,
        bodySmall: TextStyle = this.bodySmall,
        bodyBold: TextStyle = this.bodyBold,
        mainButtonDefault: TextStyle = this.mainButtonDefault,
        mainButtonActive: TextStyle = this.mainButtonActive,
        subButtonDefault: TextStyle = this.subButtonDefault,
        subButtonActive: TextStyle = this.subButtonActive,
        buttonSmall: TextStyle = this.buttonSmall,
        buttonLink: TextStyle = this.buttonLink,

        ): PawKeyTypography = PawKeyTypography(
        head24B,
        head24Sb,
        head22B,
        head22Sb,
        head20B1,
        head20B2,
        head20Sb,
        head18Sb,
        body16Sb,
        body16M,
        body14Sb,
        body14M,
        body14R,
        caption12Sb1,
        caption12Sb2,
        caption12M,
        caption12R,
        /*----------------------------------*/
        header1,
        header2,
        header3,
        subTitle,
        bodyDefault,
        bodyActive,
        bodySmall,
        mainButtonDefault,
        mainButtonActive,
        subButtonDefault,
        subButtonActive,
        buttonSmall,
        buttonLink,
            bodyBold
    )

    fun update(other: PawKeyTypography) {
        head24B = other.head24B
        head24Sb = other.head24Sb
        head22B = other.head22B
        head22Sb = other.head22Sb
        head20B1 = other.head20B1
        head20B2 = other.head20B2
        head20Sb = other.head20Sb
        head18Sb = other.head18Sb
        body16Sb = other.body16Sb
        body16M = other.body16M
        body14Sb = other.body14Sb
        body14M = other.body14M
        body14R = other.body14R
        caption12Sb1 = other.caption12Sb1
        caption12Sb2 = other.caption12Sb2
        caption12M = other.caption12M
        caption12R = other.caption12R
        /*----------------------------------*/
        header1 = other.header1
        header2 = other.header2
        header3 = other.header3
        subTitle = other.subTitle
        bodyDefault = other.bodyDefault
        bodyActive = other.bodyActive
        bodySmall = other.bodySmall
        mainButtonDefault = other.mainButtonDefault
        mainButtonActive = other.mainButtonActive
        subButtonDefault = other.subButtonDefault
        subButtonActive = other.subButtonActive
        buttonSmall = other.buttonSmall
        buttonLink = other.buttonLink
        bodyBold = other.bodyBold
    }
}

fun pawKeyTextStyle(
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    letterSpacing: TextUnit
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),
)

@Composable
fun pawKeyTypography(): PawKeyTypography {
    return PawKeyTypography(
        head24B = pawKeyTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = (24 * 1.0).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        head24Sb = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = (24 * 1.4).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        head22B = pawKeyTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = (22 * 1.6).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        head22Sb = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = (22 * 1.6).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        head20B1 = pawKeyTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = (20 * 1.5).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        head20B2 = pawKeyTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = (20 * 1.1).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        head20Sb = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = (20 * 1.5).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        head18Sb = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = (18 * 1.6).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        body16Sb = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = (16 * 1.5).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        body16M = pawKeyTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = (16 * 1.5).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        body14Sb = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = (14 * 1.6).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        body14M = pawKeyTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = (14 * 1.6).sp,
            letterSpacing = TextUnit.Unspecified
        ),

        body14R = pawKeyTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = (14 * 1.5).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        caption12Sb1 = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = (12 * 1.5).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        caption12Sb2 = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = (12 * 1.2).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        caption12M = pawKeyTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = (12 * 1.5).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        caption12R = pawKeyTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = (12 * 1.2).sp,
            letterSpacing = TextUnit.Unspecified
        ),
        /*---------------------------------------*/
        header1 = pawKeyTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = (-0.02).em
        ),
        header2 = pawKeyTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 26.sp,
            letterSpacing = (-0.02).em
        ),
        header3 = pawKeyTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.02).em
        ),
        subTitle = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.em
        ),
        bodyDefault = pawKeyTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.em
        ),
        bodyActive = pawKeyTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.em
        ),
        bodySmall = pawKeyTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.em
        ),
        bodyBold = pawKeyTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.em
        ),
        mainButtonDefault = pawKeyTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.em
        ),
        mainButtonActive = pawKeyTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.em
        ),
        subButtonDefault = pawKeyTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.em
        ),
        subButtonActive = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.em
        ),
        buttonSmall = pawKeyTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.em
        ),
        buttonLink = pawKeyTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.em
        ),
    )
}
