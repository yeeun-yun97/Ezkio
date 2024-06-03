package com.anse.easyQrPay.models.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sales_products")
data class SaleProduct(
    @PrimaryKey
    @ColumnInfo(name = "sale_product_code")
    val saleProductCode: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "sale_code")
    val saleCode: String,
    @ColumnInfo(name = "product_code")
    val productCode: String,
    @ColumnInfo(name = "amount")
    val amount: Int,
    @ColumnInfo(name = "price")
    val price: Int,//가격은 언제든 변동될 수 있기 때문에, 판매 당시의 가격을 저장
)