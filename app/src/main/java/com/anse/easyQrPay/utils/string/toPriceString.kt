package com.anse.easyQrPay.utils.string

import android.content.Context
import com.anse.easyQrPay.R


fun Int.toPriceString(context: Context): String {
    return if (this == 0) context.getString(R.string.common_price_free)
    else String.format(context.getString(R.string.common_price_template), this.toString())
}