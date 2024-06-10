package com.anse.easyQrPay.utils.string

fun String.accountNumberAvailable(): Boolean {
    val availableChars = listOf(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-',
    )
    return this.toCharArray().find { it !in availableChars } == null
}