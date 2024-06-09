package kr.yeeun0411.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_code")
    val categoryCode: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name")
    val name: String,
)