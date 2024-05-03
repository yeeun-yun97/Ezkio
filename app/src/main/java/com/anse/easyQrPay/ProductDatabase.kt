package com.anse.easyQrPay

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anse.easyQrPay.models.product.ProductValue

@Database(
    version = 1,
    entities = [ProductValue::class]
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}