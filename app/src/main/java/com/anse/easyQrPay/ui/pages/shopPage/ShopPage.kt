package com.anse.easyQrPay.ui.pages.shopPage

import androidx.activity.compose.BackHandler
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LiveData
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.item.CategoryItem
import com.anse.easyQrPay.ui.item.ProductItem
import com.anse.easyQrPay.ui.pages.qrPage.QRPage
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle
import kr.yeeun0411.database.model.model.CategoryModel
import kr.yeeun0411.database.model.model.ProductModel
import kotlin.math.min


@Composable
fun ShopPage(
    categoryList: State<List<CategoryModel>>,
    productList: State<List<ProductModel>>,
    finishOrder: (Map<ProductModel, Int>, () -> Unit) -> Unit,
//    navigateToQRPage: () -> Unit,
) {
    val selectedCategory = rememberSaveable { mutableStateOf<CategoryModel?>(null) }
    val shoppingList = remember { mutableStateMapOf<ProductModel, Int>() }
    val shoppingPrice = remember {
        derivedStateOf {
            shoppingList.entries.map { (product, amount) -> product.price * amount }.sum()
        }
    }

    val isCalculating = rememberSaveable { mutableStateOf(false) }

    val showFinishSellingDialog = rememberSaveable { mutableStateOf(false) }
    if (showFinishSellingDialog.value) {
        Dialog(onDismissRequest = { showFinishSellingDialog.value = false }) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(Modifier.padding(vertical = 20.dp, horizontal = 27.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        stringResource(R.string.shop_page_finish_selling_dialog_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.shop_page_finish_selling_dialog_message),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(15.dp))
                    AnseButton(
                        onClick = {
                            finishOrder(
                                shoppingList
                            ) {
                                shoppingList.clear()
                                isCalculating.value = false
                                showFinishSellingDialog.value = false
                                selectedCategory.value = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        buttonStyle = AnseButtonStyle.newStyle(
                            shape = RoundedCornerShape(20.dp),
                            colors = AnseButtonColors(
                                contentColor = Color.Black,
                                containerColor = Color(0xFFABDE82),
                            ),
                            contentPadding = PaddingValues(vertical = 20.dp),
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.shop_page_order_finish),
                            modifier = Modifier.align(Alignment.Center),
                            color = it,
                            fontSize = 28.sp,
                            lineHeight = 28.sp,
                        )
                    }
                }
            }
        }
    }


    BackHandler {
        isCalculating.value = false
    }

    Row() {
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            color = Color(0xFFE9E9E9)
        ) {
            Column {
                if (!isCalculating.value) {
                    Spacer(Modifier.height(20.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.Start),
                        contentPadding = PaddingValues(horizontal = 60.dp, vertical = 10.dp),
                    ) {
                        item {
                            CategoryItem(
                                category = null,
                                isSelected = selectedCategory.value == null,
                                onClick = { selectedCategory.value = null },
                            )
                        }
                        itemsIndexed(categoryList.value) { index, item ->
                            CategoryItem(
                                category = item,
                                isSelected = selectedCategory.value == item,
                                onClick = { selectedCategory.value = item },
                            )
                        }
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        contentPadding = PaddingValues(horizontal = 60.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        productList.value?.let {
                            itemsIndexed(it) { index, item ->
                                item.ProductItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        if (if (item.stopped) false
                                            else item.stock.let {
                                                if (it == null) true
                                                else (it - (shoppingList[item] ?: 0)) > 0
                                            }
                                        )
                                            shoppingList[item] = min((shoppingList[item] ?: 0) + 1, item.stock ?: Int.MAX_VALUE)
                                    },
                                )
                            }
                        }
                    }
                } else {
                    QRPage(
                        price = shoppingPrice.value
                    )
                }
            }
        }
        Surface(
            modifier = Modifier
                .width(400.dp)
                .fillMaxHeight(),
            color = Color.White,
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp), verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(Modifier.height(42.dp))
                Text(stringResource(R.string.shop_page_shopping_list_title), fontSize = 32.sp, lineHeight = 38.sp, modifier = Modifier.clickable {
                    if (isCalculating.value) {
                        showFinishSellingDialog.value = true
                    }
                })
                Spacer(Modifier.height(30.dp))
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(vertical = 20.dp)
                    ) {
                        itemsIndexed(shoppingList.toList()) { index, item ->
                            ShoppingItem(
                                product = item.first,
                                count = item.second,
                                setItemCount = {
                                    if (it <= 0) shoppingList.remove(item.first)
                                    else shoppingList[item.first] = min(it, item.first.stock ?: Int.MAX_VALUE)
                                },
                                editEnabled = !isCalculating.value
                            )
                        }
                    }
                    if (shoppingList.isEmpty()) {
                        Text(
                            stringResource(R.string.shop_page_shopping_list_empty),
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                if (!isCalculating.value && shoppingList.isNotEmpty()) {
                    Text(stringResource(R.string.shop_page_total_price_title), fontSize = 20.sp, lineHeight = 20.sp)
                    Text("${shoppingPrice.value}원", fontSize = 36.sp, lineHeight = 36.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(20.dp))
                    AnseButton(
                        onClick = { if (it) isCalculating.value = true },
                        modifier = Modifier.fillMaxWidth(),
                        buttonStyle = AnseButtonStyle.newStyle(
                            shape = RoundedCornerShape(20.dp),
                            colors = AnseButtonColors(
                                contentColor = Color.Black,
                                containerColor = Color(0xFFABDE82),
                            ),
                            contentPadding = PaddingValues(vertical = 20.dp),
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.shop_page_order_start),
                            modifier = Modifier.align(Alignment.Center),
                            color = it,
                            fontSize = 28.sp,
                            lineHeight = 28.sp,
                        )
                    }
                } else if (isCalculating.value) {
                    AnseButton(
                        onClick = {
                            isCalculating.value = false
                        },
                        buttonStyle = AnseButtonStyle.newStyle(
                            shape = RoundedCornerShape(20.dp),
                            colors = AnseButtonColors(
                                contentColor = Color.Black,
                                containerColor = Color(0xFFA8A8A8),
                            ),
                            contentPadding = PaddingValues(vertical = 20.dp),
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.shop_page_order_cancel),
                            modifier = Modifier.align(Alignment.Center),
                            color = it,
                            fontSize = 28.sp,
                            lineHeight = 28.sp,
                        )
                    }
                }
                Spacer(Modifier.height(27.dp))
            }
        }
    }
}

@Composable
fun ShoppingItem(
    product: ProductModel,
    count: Int,
    setItemCount: (Int) -> Unit,
    editEnabled: Boolean,
) {
    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(15.dp),
    ) {
        Row(
            Modifier
                .height(IntrinsicSize.Max)
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f, true)
            ) {
                Image(
                    painter = BitmapPainter(StringToBitmap(product.image)!!),
                    contentDescription = "product_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(1f, matchHeightConstraintsFirst = false),
                )
            }
            Spacer(Modifier.width(15.dp))
            Column(
                Modifier
                    .weight(1f)
            ) {
                Text(
                    product.name,
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    product.price.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(IntrinsicSize.Max)) {
                    Text(
                        text = count.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    if (editEnabled) {
                        Spacer(Modifier.width(10.dp))
                        AnseButton(
                            onClick = { setItemCount(count - 1) },
                            buttonStyle = AnseButtonStyle.newStyle(
                                colors = AnseButtonColors(
                                    contentColor = Color(0xFF7D7D7D),
                                    containerColor = Color(0xFFF5F5F5),
                                ),
                                shape = RoundedCornerShape(10.dp),
                                border = null,
                                contentPadding = PaddingValues(10.dp),
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f, true)
                        ) { Text("-", modifier = Modifier.align(Alignment.Center), color = it) }
                        Spacer(Modifier.width(8.dp))
                        AnseButton(
                            onClick = { setItemCount(count + 1) },
                            buttonStyle = AnseButtonStyle.newStyle(
                                colors = AnseButtonColors(
                                    contentColor = Color(0xFF7D7D7D),
                                    containerColor = Color(0xFFF5F5F5),
                                ),
                                shape = RoundedCornerShape(10.dp),
                                border = null,
                                contentPadding = PaddingValues(10.dp),
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f, true)
                        ) { Text("+", modifier = Modifier.align(Alignment.Center), color = it) }
                    }
                }
            }
            if (editEnabled) {
                Spacer(Modifier.width(10.dp))
                AnseButton(
                    onClick = { setItemCount(0) },
                    modifier = Modifier.fillMaxHeight(),
                    buttonStyle = AnseButtonStyle.newStyle(
                        colors = AnseButtonColors(
                            contentColor = Color(0xFF757575),
                            containerColor = Color(0xFFE8E8E8),
                        ),
                        shape = RectangleShape,
                        border = null,
                        contentPadding = PaddingValues(10.dp),
                    )
                ) {
                    Text("삭제", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

fun StringToBitmap(encodedString: String?): ImageBitmap? {
    return try {
        val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size).asImageBitmap()
    } catch (e: Exception) {
        e.message
        null
    }
}
