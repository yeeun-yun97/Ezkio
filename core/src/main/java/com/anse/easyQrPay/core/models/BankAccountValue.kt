package com.anse.easyQrPay.core.models

import java.net.URLEncoder

data class BankAccountValue(
    val bankName: String,
    val accountHolderName: String,
    val accountNumber: String,
)

fun BankAccountValue.accountNumberNoDashOrSpace(): String {
    return accountNumber.replace("-", "").replace(" ", "")
}

fun BankAccountValue.bankNameUrlEncoded(): String {
    return URLEncoder.encode(bankName, "UTF-8")
}