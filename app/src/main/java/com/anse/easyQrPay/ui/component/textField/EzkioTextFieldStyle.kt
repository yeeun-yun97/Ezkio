package com.anse.easyQrPay.ui.component.textField

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.anse.easyQrPay.ui.theme.fontFamily

fun ezkioTextFieldStyle(
    fontSize: TextUnit,
    fontWeight: FontWeight,
    textAlign: TextAlign,
    color: Color,
): TextStyle {
    return TextStyle(
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        color = color,
        fontFamily = fontFamily
    )
}