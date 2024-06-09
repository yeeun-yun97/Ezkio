package kr.yeeun0411.data.converters

import kr.yeeun0411.data.model.ProductModel
import kr.yeeun0411.database.model.ProductEntity

internal fun ProductModel.convertToEntity(): ProductEntity {
    return ProductEntity(
        productCode = this.productCode,
        name = this.name,
        price = this.price,
        image = this.image,
        stock = this.stock,
        categoryCode = this.categoryCode,
        stopped = this.stopped
    )
}