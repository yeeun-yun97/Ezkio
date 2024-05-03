package com.anse.easyQrPay.models.product

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
data class ProductValue(
    @PrimaryKey
    @ColumnInfo(name = "productCode")
    val productCode: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "stock")
    val stock: Int,
    @ColumnInfo(name = "category")
    val category: String,
)