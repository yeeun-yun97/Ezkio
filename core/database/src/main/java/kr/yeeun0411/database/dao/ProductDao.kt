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

    @Query("SELECT * FROM Products WHERE category_code = :categoryCode")
    fun getProductsByCategoryCode(categoryCode: String?): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Upsert
    fun upsertProduct(product: ProductEntity)

    @Query("DELETE FROM Products WHERE product_code = :productCode")
    fun deleteProductByProductCode(productCode: String)

    @Query("UPDATE sales_products SET product_code = NULL WHERE product_code = :productCode")
    fun updateSaleProductDeletedProductCode(productCode: String)

    @Upsert
    fun upsertCategory(category: CategoryEntity)

    @Query("DELETE FROM Categories WHERE category_code = :categoryCode")
    fun deleteCategoryByCategoryCode(categoryCode: String)

    @Query("UPDATE Products SET category_code = NULL WHERE category_code = :categoryCode")
    fun updateProductDeletedCategoryCode(categoryCode: String)

}