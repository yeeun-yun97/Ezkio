package kr.yeeun0411.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sales")
data class SaleEntity(
    @PrimaryKey
    @ColumnInfo(name = "sale_code")
    val saleCode: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "timeStamp")
    val timeStamp: Long,//epochMillis
)
