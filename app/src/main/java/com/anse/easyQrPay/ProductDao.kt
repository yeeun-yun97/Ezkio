package com.anse.easyQrPay

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.anse.easyQrPay.models.product.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM Products")
    fun getAllFlow(): Flow<List<Product>>

    @Upsert
    fun insertProduct(productValue: Product)

}