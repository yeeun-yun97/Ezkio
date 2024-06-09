package kr.yeeun0411.data.converters

import kr.yeeun0411.database.model.ProductEntity
import kr.yeeun0411.database.model.model.ProductModel

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

internal fun ProductEntity.convertToModel(): ProductModel {
    return ProductModel(
        productCode = this.productCode,
        name = this.name,
        price = this.price,
        image = this.image,
        stock = this.stock,
        categoryCode = this.categoryCode,
        stopped = this.stopped
    )
}