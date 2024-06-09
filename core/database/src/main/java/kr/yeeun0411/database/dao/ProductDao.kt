package kr.yeeun0411.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kr.yeeun0411.database.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM Products")
    fun getAllFlow(): Flow<List<ProductEntity>>

    @Upsert
    fun upsertProduct(productValue: ProductEntity)

}