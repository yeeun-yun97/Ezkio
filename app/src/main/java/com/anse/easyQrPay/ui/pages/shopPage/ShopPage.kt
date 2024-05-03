package com.anse.easyQrPay.ui.pages.shopPage

import androidx.activity.compose.BackHandler
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
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
import com.anse.easyQrPay.R
import com.anse.easyQrPay.models.product.ProductCategoryValue
import com.anse.easyQrPay.models.product.ProductValue
import com.anse.easyQrPay.ui.pages.qrPage.QRPage
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle
import kotlin.math.min


enum class ProductCategoryValueImpl(
    @StringRes override val nameRes: Int,
) : ProductCategoryValue {
    Badge(
        nameRes = R.string.shop_page_category_badge
    ),
    PhotoCard(
        nameRes = R.string.shop_page_category_photo_card
    ),
    POSTCARD(
        nameRes = R.string.shop_page_category_post_card
    ),
    SCROLL(
        nameRes = R.string.shop_page_category_scroll
    );

    companion object {
        fun fromName(name: String): ProductCategoryValue {
            return values().find { it.name == name } ?: throw IllegalArgumentException("No such category: $name")
        }
    }
}


@Composable
fun ShopPage(
    categoryList: List<ProductCategoryValue> = ProductCategoryValueImpl.entries,
    productList: List<ProductValue>,
//    navigateToQRPage: () -> Unit,
) {
    val selectedCategory = rememberSaveable { mutableStateOf<ProductCategoryValue?>(null) }
    val shoppingList = remember { mutableStateMapOf<ProductValue, Int>() }
    val shoppingPrice = remember {
        derivedStateOf {
            shoppingList.entries.map { (product, amount) -> product.price * amount }.sum()
        }
    }

    val selectedProductList = rememberSaveable { mutableStateOf(productList) }
    LaunchedEffect(key1 = selectedCategory.value) {
        selectedCategory.value.let { selectedCategory ->
            if (selectedCategory == null) selectedProductList.value = productList
            else selectedProductList.value = productList.filter { ProductCategoryValueImpl.fromName(it.category) == selectedCategory }
        }
    }

    val isCalculating = rememberSaveable { mutableStateOf(false) }


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
                        itemsIndexed(categoryList) { index, item ->
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
                        itemsIndexed(selectedProductList.value) { index, item ->
                            ProductItem(
                                product = item,
                                onClick = {
                                    shoppingList[item] = min((shoppingList[item] ?: 0) + 1, item.stock)
                                },
                                cartItemCount = shoppingList[item] ?: 0
                            )
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
                Text(stringResource(R.string.shop_page_shopping_list_title), fontSize = 32.sp, lineHeight = 38.sp)
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
                                    else shoppingList[item.first] = min(it, item.first.stock)
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
fun CategoryItem(
    category: ProductCategoryValue?,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    AnseButton(
        onClick = { if (it) onClick() },
        modifier = Modifier
            .widthIn(min = 160.dp)
            .heightIn(min = 40.dp),
        buttonStyle = AnseButtonStyle.newStyle(
            shape = CircleShape,
            colors = AnseButtonColors(
                contentColor = if (isSelected) Color.Black else Color.DarkGray,
                containerColor = if (isSelected) Color(0xFFABDE82) else Color.White,
            ),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
        )
    ) {
        Text(
            text = stringResource(category?.nameRes ?: R.string.shop_page_category_all),
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = it,
        )
    }
}

@Composable
fun ProductItem(
    product: ProductValue,
    onClick: () -> Unit,
    cartItemCount: Int,
) {
    val enabled = rememberUpdatedState(newValue = (product.stock - cartItemCount) > 0)
    Box {
        AnseButton(
            enabled = enabled.value,
            modifier = Modifier.fillMaxWidth(),
            onClick = { if (it) onClick() },
            buttonStyle = AnseButtonStyle.newStyle(
                shape = RoundedCornerShape(20.dp),
                colors = AnseButtonColors(
                    contentColor = Color.Black,
                    containerColor = Color.White,
                ),
                contentPadding = PaddingValues(12.dp),
            )
        ) {
            Column(Modifier.fillMaxWidth()) {
                Image(
                    painter = BitmapPainter(image = StringToBitmap(product.image)!!),
                    contentDescription = "product_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(1f, matchHeightConstraintsFirst = false),
                    alpha = if (enabled.value) 1f else 0.2f
                )
                Spacer(Modifier.height(28.dp))
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = product.price.toString(),
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(5.dp))
            }
        }

        if (!enabled.value) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.align(BiasAlignment(verticalBias = -0.4f, horizontalBias = 0f))
            ) {
                Text(
                    text = "완판!!\n감사합니다:)",
                    modifier = Modifier.padding(25.dp),
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }

    }
}


@Composable
fun ShoppingItem(
    product: ProductValue,
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
