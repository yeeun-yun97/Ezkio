package com.anse.easyQrPay

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.anse.easyQrPay.models.product.ProductValue
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductValue")
    fun getAllFlow(): Flow<List<ProductValue>>

    @Upsert
    fun insertProduct(productValue: ProductValue)

}