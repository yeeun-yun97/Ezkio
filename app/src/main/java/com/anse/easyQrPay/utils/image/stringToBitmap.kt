package com.anse.easyQrPay.utils.image

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun stringToBitmap(encodedString: String?): ImageBitmap? {
    return try {
        val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size).asImageBitmap()
    } catch (e: Exception) {
        e.message
        null
    }
}