package com.anse.easyQrPay.models.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "category_code")
    val categoryCode: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name")
    val name: String,
)