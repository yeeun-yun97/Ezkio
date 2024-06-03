package com.anse.easyQrPay

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anse.easyQrPay.models.product.Category
import com.anse.easyQrPay.models.product.Product
import com.anse.easyQrPay.models.product.Sale
import com.anse.easyQrPay.models.product.SaleProduct

@Database(
    version = 1,
    entities = [
        Product::class,
        Category::class,
        Sale::class,
        SaleProduct::class
    ]
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}