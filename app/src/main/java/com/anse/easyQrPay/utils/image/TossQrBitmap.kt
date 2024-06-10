package com.anse.easyQrPay.utils.image

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.anse.easyQrPay.models.accountNumberNoDashOrSpace
import com.anse.easyQrPay.models.bankNameUrlEncoded

fun getTossQrBitmap(
    bankName: String,
    accountNumber: String,
    price: Int? = null,
): ImageBitmap {
    val url = "supertoss://send?${price?.let { "amount = ${it}&" }}bank=${bankName.bankNameUrlEncoded()}&accountNo=${accountNumber.accountNumberNoDashOrSpace()}&origin=qr"
    return net.glxn.qrgen.android.QRCode
        .from(url)
        .withSize(300, 300)
        .bitmap()
        .asImageBitmap()
}