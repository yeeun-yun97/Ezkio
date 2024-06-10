package kr.yeeun0411.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.yeeun0411.data.converters.convertToEntity
import kr.yeeun0411.data.converters.convertToModel
import kr.yeeun0411.database.dao.ProductDao
import kr.yeeun0411.database.model.model.CategoryModel
import kr.yeeun0411.database.model.model.ProductModel
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
) {
    fun getProducts(): Flow<List<ProductModel>> {
        return productDao.getAllProducts().map {
            it.map {
                it.convertToModel()
            }
        }
    }

    fun getCategories(): Flow<List<CategoryModel>> {
        return productDao.getAllCategories().map {
            it.map {
                it.convertToModel()
            }
        }
    }

    fun upsertProduct(
        product: ProductModel,
    ) {
        productDao.upsertProduct(
            product.convertToEntity()
        )
    }

    fun purchaseProduct(
        product: ProductModel,
        amount: Int,
    ) {
        //상품의 재고 수를 줄인다.
        product.stock?.let {
            productDao.upsertProduct(
                product.copy(stock = it - amount).convertToEntity()
            )
        }

        //상품의 판매 정보를 저장한다.

    }

    fun deleteProduct(productCode: String) {
        productDao.deleteProductByProductCode(productCode)
        productDao.updateSaleProductDeletedProductCode(productCode)
    }

    fun upsertCategory(it: CategoryModel) {
        productDao.upsertCategory(it.convertToEntity())
    }

    fun deleteCategory(categoryCode: String) {
        productDao.deleteCategoryByCategoryCode(categoryCode)
        productDao.updateProductDeletedCategoryCode(categoryCode)
    }


}

