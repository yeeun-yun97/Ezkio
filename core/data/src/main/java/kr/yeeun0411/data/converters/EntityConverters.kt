package kr.yeeun0411.data.converters

import kr.yeeun0411.data.model.BankAccountModel
import kr.yeeun0411.database.model.CategoryEntity
import kr.yeeun0411.database.model.ProductEntity
import kr.yeeun0411.data.model.CategoryModel
import kr.yeeun0411.data.model.ProductModel
import kr.yeeun0411.database.model.BankAccountEntity

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

internal fun CategoryModel.convertToEntity(): CategoryEntity{
    return CategoryEntity(
        categoryCode = this.categoryCode,
        name = this.name
    )
}

internal fun CategoryEntity.convertToModel(): CategoryModel {
    return CategoryModel(
        categoryCode = this.categoryCode,
        name = this.name
    )
}

internal fun BankAccountModel.convertToEntity(): BankAccountEntity {
    return BankAccountEntity(
        bankAccountCode = this.bankAccountCode,
        bankName = this.bankName,
        accountNumber = this.accountNumber,
        accountHolder = this.accountHolder,
        useQr = this.useQr
    )
}

internal fun BankAccountEntity.convertToModel(): BankAccountModel {
    return BankAccountModel(
        bankAccountCode = this.bankAccountCode,
        bankName = this.bankName,
        accountNumber = this.accountNumber,
        accountHolder = this.accountHolder,
        useQr = this.useQr
    )
}