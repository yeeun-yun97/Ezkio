package com.anse.easyQrPay.utils.string

fun String.accountNumberNoDashOrSpace(): String {
    return this.replace("-", "").replace(" ", "")
}