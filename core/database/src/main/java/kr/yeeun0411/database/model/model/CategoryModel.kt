package kr.yeeun0411.database.model.model

import java.util.UUID

data class CategoryModel(
    val categoryCode: String = UUID.randomUUID().toString(),
    val name: String,
)