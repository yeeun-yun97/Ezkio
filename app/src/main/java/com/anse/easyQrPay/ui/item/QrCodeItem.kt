package com.anse.easyQrPay.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.anse.easyQrPay.ui.theme.DarkGray

@Composable
fun QrCodeItem(
    size: Dp,
    bitmap: ImageBitmap?,
    shadowElevation: Dp = 0.dp,
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = shadowElevation
    ) {
        bitmap?.let {
            Image(
                modifier = Modifier.size(size),
                bitmap = bitmap,
                contentDescription = "qrCode"
            )
        } ?: CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .padding(55.dp),
            color = DarkGray
        )
    }
}