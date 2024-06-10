package kr.yeeun0411.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kr.yeeun0411.database.model.CategoryEntity
import kr.yeeun0411.database.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM Products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Upsert
    fun upsertProduct(product: ProductEntity)

    @Query("DELETE FROM Products WHERE product_code = :productCode")
    fun deleteProductByProductCode(productCode: String)

    @Query("UPDATE sales_products SET product_code = NULL WHERE product_code = :productCode")
    fun updateSaleProductDeletedProductCode(productCode: String)

    @Upsert
    fun upsertCategory(category: CategoryEntity)

}