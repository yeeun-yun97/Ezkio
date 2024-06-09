package kr.yeeun0411.data

import kr.yeeun0411.data.converters.convertToEntity
import kr.yeeun0411.database.dao.ProductDao
import kr.yeeun0411.data.model.ProductModel
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
) {
    fun getProducts(): List<ProductModel> {
        return listOf()
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


}

