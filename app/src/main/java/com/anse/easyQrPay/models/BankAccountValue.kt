package com.anse.easyQrPay.models

import java.net.URLEncoder

data class BankAccountValue(
    val bankName: String,
    val accountHolderName: String,
    val accountNumber: String,
)

fun String.accountNumberNoDashOrSpace(): String {
    return this.replace("-", "").replace(" ", "")
}

fun String.bankNameUrlEncoded(): String {
    return URLEncoder.encode(this, "UTF-8")
}