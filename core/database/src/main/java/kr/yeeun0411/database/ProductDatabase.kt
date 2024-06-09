package kr.yeeun0411.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.yeeun0411.database.model.CategoryEntity
import kr.yeeun0411.database.model.ProductEntity
import kr.yeeun0411.database.model.SaleEntity
import kr.yeeun0411.database.model.SaleProductEntity

@Database(
    version = 1,
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        SaleEntity::class,
        SaleProductEntity::class
    ]
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): kr.yeeun0411.database.dao.ProductDao
}