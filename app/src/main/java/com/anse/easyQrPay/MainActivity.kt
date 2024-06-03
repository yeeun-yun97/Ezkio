package com.anse.easyQrPay

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.anse.easyQrPay.models.product.Product
import com.anse.easyQrPay.ui.pages.shopPage.ShopPage
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dao = getDao(this.applicationContext)
            val productList = dao.getAllFlow().collectAsState(initial = emptyList())

            val scope = rememberCoroutineScope()
            val finishOrder: (Map<Product, Int>, () -> Unit) -> Unit = { orderMap, onFinished ->
                scope.launch(Dispatchers.IO) {
                    orderMap.entries.forEach { (product, count) ->
                        if (product.stock != null) {
                            dao.insertProduct(product.copy(stock = product.stock - count))
                        }
                    }
                    onFinished()
                }
            }

//            LaunchedEffect(Unit) {
//                launch(Dispatchers.IO) {
//                    dao.insertProduct(
//                        ProductValue(
//                            "BADGE_45",
//                            2000,
//                            getString(R.string.shop_page_product_badge45),
//                            getBase64FromDrawableRes(resources = resources, drawableRes = R.drawable.badge_45),
//                            10,
//                            ProductCategoryValueImpl.Badge.name
//                        )
//                    )
//                    dao.insertProduct(
//                        ProductValue(
//                            "BADGE_9",
//                            2000,
//                            getString(R.string.shop_page_product_badge9),
//                            getBase64FromDrawableRes(resources = resources, drawableRes = R.drawable.badge_9),
//                            10,
//                            ProductCategoryValueImpl.Badge.name
//                        )
//                    )
//                    dao.insertProduct(
//                        ProductValue(
//                            "BADGE_11",
//                            2000,
//                            getString(R.string.shop_page_product_badge11),
//                            getBase64FromDrawableRes(resources = resources, drawableRes = R.drawable.badge_11),
//                            10,
//                            ProductCategoryValueImpl.Badge.name
//                        )
//                    )
//                    dao.insertProduct(
//                        ProductValue(
//                            "BADGE_416",
//                            2000,
//                            getString(R.string.shop_page_product_badge416),
//                            getBase64FromDrawableRes(resources = resources, drawableRes = R.drawable.badge_416),
//                            10,
//                            ProductCategoryValueImpl.Badge.name
//                        )
//                    )
//                    dao.insertProduct(
//                        ProductValue(
//                            "POST_CARD_SET_MINI404",
//                            1000,
//                            getString(R.string.shop_page_product_post_card_set_mini404),
//                            getBase64FromDrawableRes(resources = resources, drawableRes = R.drawable.post_card_set_mini404),
//                            30,
//                            ProductCategoryValueImpl.POSTCARD.name
//                        )
//                    )
//                    dao.insertProduct(
//                        ProductValue(
//                            "POST_CARD_CAFE94",
//                            1000,
//                            getString(R.string.shop_page_product_post_card_cafe94),
//                            getBase64FromDrawableRes(resources = resources, drawableRes = R.drawable.post_card_cafe94),
//                            30,
//                            ProductCategoryValueImpl.POSTCARD.name
//                        )
//                    )
//                    dao.insertProduct(
//                        ProductValue(
//                            "PHOTO_CARD_SET_9415",
//                            2000,
//                            getString(R.string.shop_page_product_photo_card_set_9415),
//                            getBase64FromDrawableRes(resources = resources, drawableRes = R.drawable.photo_card_set_9415),
//                            400,
//                            ProductCategoryValueImpl.PhotoCard.name
//                        )
//                    )
//                    dao.insertProduct(
//                        ProductValue(
//                            "SCROLL_CAFE94",
//                            10000,
//                            getString(R.string.shop_page_product_scroll_cafe94),
//                            getBase64FromDrawableRes(resources = resources, drawableRes = R.drawable.scroll_cafe94),
//                            10,
//                            ProductCategoryValueImpl.SCROLL.name
//                        )
//                    )
//                }
//            }


            EasyQrPayTheme {
                val navController = rememberNavController()
                NavHost(startDestination = "shop", navController = navController) {
                    composable("shop") {
                        ShopPage(
                            productList = productList,
                            finishOrder = finishOrder
                        )
                    }
                }
            }
        }
    }

    companion object {
        lateinit var db: ProductDatabase
        fun getDao(appContext: Context): ProductDao {
            if (!::db.isInitialized) {
                db = Room.databaseBuilder(
                    appContext,
                    ProductDatabase::class.java,
                    "user-database"
                ).build()
            }
            return db.getProductDao()
        }
    }
}

private fun getBase64FromDrawableRes(resources: Resources, drawableRes: Int): String {
    val bitmap = BitmapFactory.decodeResource(resources, drawableRes)
    val byteStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
    val byteArray = byteStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}
