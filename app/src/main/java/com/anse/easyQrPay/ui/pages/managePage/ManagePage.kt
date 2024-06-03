package com.anse.easyQrPay.ui.pages.managePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.R
import com.anse.easyQrPay.models.product.ProductCategoryValue
import com.anse.easyQrPay.models.product.ProductValue
import com.anse.easyQrPay.ui.item.CategoryItem
import com.anse.easyQrPay.ui.item.ProductItem
import com.anse.easyQrPay.ui.pages.shopPage.ProductCategoryValueImpl
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme
import com.anse.easyQrPay.ui.theme.Gray
import com.anse.easyQrPay.ui.theme.LightGray
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonNoStyle
import com.anse.uikit.components.button.AnseButtonStyle

@Preview
@Composable
fun ManagePagePreview() {
    EasyQrPayTheme {
        ManagePage(
            productList =
                    listOf(
                        ProductValue(
                            productCode = "1",
                            price = 1000,
                            name = "product1",
                            image = "",
                            stock = 10,
                            category = "category1"
                        )
                    )

            ,
            navigateToSetting = {},
            navigateToStatics = {},
            navigateToKiosk = {},
            addCategory = {},
            addProduct = {}
        )
    }
}

@Composable
fun ManagePage(
    categoryList: List<ProductCategoryValue> = ProductCategoryValueImpl.entries,
    productList: List<ProductValue>,
    addCategory: () -> Unit,
    addProduct: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToStatics: () -> Unit,
    navigateToKiosk: () -> Unit,
) {
    val selectedCategory = rememberSaveable { mutableStateOf<ProductCategoryValue?>(null) }
    Column(Modifier.fillMaxSize()) {
        Surface(color = Color.White) {
            Column {
                Spacer(Modifier.height(40.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(30.dp))
                    Image(
                        painterResource(id = R.drawable.manage_page_stock_icon),
                        modifier = Modifier.size(50.dp),
                        contentDescription = "managePageTitleIcon",
                        colorFilter = ColorFilter.tint(DarkGray)
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        stringResource(id = R.string.manage_page_title),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.width(8.dp))
                    AnseButton(
                        onClick = { if (it) navigateToKiosk() },
                        modifier = Modifier,
                        buttonStyle = AnseButtonStyle.newStyle(
                            colors = AnseButtonColors(contentColor = Color.DarkGray),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        )
                    ) {
                        Text(
                            stringResource(id = R.string.manage_page_change_kiosk_button),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = DarkGray
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    AnseButtonNoStyle(
                        onClick = { if (it) navigateToStatics() }
                    ) {
                        Box(Modifier.size(40.dp)) {
                            Image(
                                painterResource(R.drawable.manage_page_stats_icon),
                                modifier = Modifier.align(Alignment.Center),
                                contentDescription = "statsButton",
                                colorFilter = ColorFilter.tint(DarkGray)
                            )
                        }
                    }
                    Spacer(Modifier.width(10.dp))
                    AnseButtonNoStyle(
                        onClick = { if (it) navigateToSetting() }
                    ) {
                        Box(Modifier.size(40.dp)) {
                            Image(
                                painterResource(R.drawable.manage_page_setting_icon),
                                modifier = Modifier.align(Alignment.Center),
                                contentDescription = "statsButton",
                                colorFilter = ColorFilter.tint(DarkGray)
                            )
                        }
                    }
                    Spacer(Modifier.width(30.dp))
                }
                Spacer(Modifier.height(20.dp))
            }
        }
        Divider(
            color = Gray,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Surface(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            color = LightGray
        ) {
            Column {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(horizontal = 30.dp, vertical = 15.dp),
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
                    item {
                        AnseButton(
                            onClick = { if (it) addCategory() },
                            modifier = Modifier
                                .heightIn(min = 40.dp),
                            buttonStyle = AnseButtonStyle.newStyle(
                                shape = CircleShape,
                                colors = AnseButtonColors(
                                    contentColor = DarkGray,
                                ),
                                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp),
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.category_add_button),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Normal,
                                color = it,
                            )
                        }
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(6),
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item {
                        AnseButtonNoStyle(
                            onClick = {
                                if (it) addProduct()
                            }
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = Gray,
                                shape = RoundedCornerShape(7.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(Modifier.height(60.dp))
                                    Spacer(Modifier.weight(1f))
                                    Image(
                                        painterResource(id = R.drawable.manage_page_add_icon),
                                        modifier = Modifier.size(40.dp),
                                        contentDescription = "addProductIcon",
                                        colorFilter = ColorFilter.tint(DarkGray)
                                    )
                                    Spacer(Modifier.height(20.dp))
                                    Text(
                                        stringResource(id = R.string.manage_page_add_product_button),
                                        color = DarkGray,
                                        fontSize = 20.sp
                                    )
                                    Spacer(Modifier.height(60.dp))
                                    Spacer(Modifier.weight(1f))
                                }
                            }
                        }

                    }

                    itemsIndexed(productList) { index, item ->
                        ProductItem(
                            product = item,
                            onClick = {
                                //TODO
                            },
                            cartItemCount = 0
                        )
                    }
                }
            }
        }
    }
}