package com.anse.easyQrPay.utils.string

import java.net.URLEncoder

fun String.bankNameUrlEncoded(): String {
    return URLEncoder.encode(this, "UTF-8")
}