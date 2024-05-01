package com.anse.easyQrPay.core.models

data class BankAccountValue(
    val bankName: String,
    val accountHolderName: String,
    val accountNumber: String,
)

fun BankAccountValue.accountNumberNoDashOrSpace(): String {
    return accountNumber.replace("-", "").replace(" ", "")
}