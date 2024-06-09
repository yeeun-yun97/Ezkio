package kr.yeeun0411.data.model

import java.util.UUID

data class SaleModel(
    val saleCode: String = UUID.randomUUID().toString(),
    val timeStamp: Long,//epochMillis
)
