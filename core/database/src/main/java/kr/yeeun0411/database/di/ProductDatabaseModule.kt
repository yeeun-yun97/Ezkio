package kr.yeeun0411.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.yeeun0411.database.ProductDatabase
import kr.yeeun0411.database.dao.ProductDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object ProductDatabaseModule {

    @Provides
    @Singleton
    fun providesProductDatabase(
        @ApplicationContext context: Context,
    ): ProductDatabase = Room.databaseBuilder(
        context,
        ProductDatabase::class.java,
        "product-database",
    ).build()

    @Provides
    fun providesProductDao(
        database: ProductDatabase,
    ): ProductDao = database.getProductDao()


}