package kr.yeeun0411.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.yeeun0411.data.converters.convertToEntity
import kr.yeeun0411.data.converters.convertToModel
import kr.yeeun0411.database.dao.ProductDao
import kr.yeeun0411.database.model.SaleEntity
import kr.yeeun0411.database.model.SaleProductEntity
import kr.yeeun0411.data.model.CategoryModel
import kr.yeeun0411.data.model.ProductModel
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

    fun getProductsByCategoryCode(categoryCode: String?): Flow<List<ProductModel>> {
        return productDao.getProductsByCategoryCode(categoryCode).map {
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

    fun purchaseProducts(orderMap: Map<ProductModel, Int>) {
        val sale = SaleEntity(
            timeStamp = System.currentTimeMillis(),
        )
        productDao.insertSale(sale)
        orderMap.forEach { (product, amount) ->
            product.stock?.let {
                productDao.upsertProduct(product.copy(stock = it - amount).convertToEntity())
            }
            productDao.insertSaleProduct(
                SaleProductEntity(
                    saleCode = sale.saleCode,
                    productCode = product.productCode,
                    amount = amount,
                    price = product.price
                )
            )
        }
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

