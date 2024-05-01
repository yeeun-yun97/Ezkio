package com.anse.easyQrPay.core.models.product

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProductValue(
    val productCode: String,
    @StringRes val nameRes: Int,
    @DrawableRes val imageRes:Int,
    val price: Int,
    val stock: Int,
    val category: ProductCategoryValue,
)