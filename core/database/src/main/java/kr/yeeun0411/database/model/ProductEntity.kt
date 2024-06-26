package kr.yeeun0411.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_code")
    val productCode: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "stock")
    val stock: Int? = null,//null일 시 무제한
    @ColumnInfo(name = "category_code")
    val categoryCode: String? = null,
    @ColumnInfo(name = "stopped")
    val stopped: Boolean = false,
)