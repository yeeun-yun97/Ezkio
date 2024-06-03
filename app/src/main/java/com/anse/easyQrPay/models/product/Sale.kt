package com.anse.easyQrPay.models.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sales")
data class Sale(
    @PrimaryKey
    @ColumnInfo(name = "sale_code")
    val saleCode: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "timeStamp")
    val timeStamp: Long,//epochMillis
)
