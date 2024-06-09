package com.anse.easyQrPay.ui.pages.managePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.component.state.rememberUiVisibility
import com.anse.easyQrPay.ui.component.state.rememberUiVisibilityByNull
import com.anse.easyQrPay.ui.item.CategoryItem
import com.anse.easyQrPay.ui.item.ProductItem
import com.anse.easyQrPay.ui.pages.managePage.dialog.ProductEditDialog
import com.anse.easyQrPay.ui.pages.managePage.menu.EProductMenu
import com.anse.easyQrPay.ui.pages.managePage.menu.ProductMenu
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme
import com.anse.easyQrPay.ui.theme.Gray
import com.anse.easyQrPay.ui.theme.LightGray
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonNoStyle
import com.anse.uikit.components.button.AnseButtonStyle
import kr.yeeun0411.data.model.CategoryModel
import kr.yeeun0411.data.model.ProductModel

val productExample = ProductModel(
    productCode = "1",
    price = 1000,
    name = "product1",
    image = "",
    stock = 10,
    categoryCode = "category1"
)

@Preview
@Composable
fun ManagePagePreview() {
    EasyQrPayTheme {
        ManagePage(
            productList =
            listOf(
                productExample,
                productExample.copy(stock = null),
                productExample.copy(stock = 0),
                productExample.copy(stopped = true)
            ),
            categoryList = listOf(
                CategoryModel(name = "식사"),
                CategoryModel(name = "음료"),
            ),
            selectImage = {},
            navigateToSetting = {},
            navigateToStatics = {},
            navigateToKiosk = {},
            selectedImage = rememberUpdatedState(newValue = null),
            clearSelectedImage = {},
            viewModel = viewModel()
        )
    }
}

@Composable
fun ManagePage(
    viewModel: ManagePageViewModel,
    categoryList: List<CategoryModel>,
    productList: List<ProductModel>,
    selectImage: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToStatics: () -> Unit,
    navigateToKiosk: () -> Unit,
    selectedImage: State<String?>,
    clearSelectedImage: () -> Unit,
) {
    val selectedCategory = rememberSaveable { mutableStateOf<CategoryModel?>(null) }
    val selectedProduct = rememberSaveable { mutableStateOf<ProductModel?>(null) }
    val onClickProductMenu = { menu: EProductMenu ->
        when (menu) {
            EProductMenu.EDIT_INFO -> {
                selectedProduct.value = null
            }

            EProductMenu.MANAGE_STOCK -> {
                selectedProduct.value = null
            }

            EProductMenu.DELETE -> {
                selectedProduct.value = null
            }
        }
    }


    val (addNewProductDialogVisibility, showAddNewProductDialog) = rememberUiVisibility(
        onHide = {
            clearSelectedImage()
            true
        }
    )
    val (editProductDialogVisibility, showEditProductDialog) = rememberUiVisibilityByNull<ProductModel>(
        onHide = {
            clearSelectedImage()
            true
        }
    )

    if (addNewProductDialogVisibility.value) {
        ProductEditDialog(
            onDismissRequest = { showAddNewProductDialog(false) },
            product = null,
            saveData = {
                viewModel.upsertProduct(it)
            },
            selectImage = selectImage,
            selectedImage = selectedImage
        )
    }

    editProductDialogVisibility.value?.let {
        ProductEditDialog(
            onDismissRequest = { showEditProductDialog(null) },
            product = it,
            saveData = {
                viewModel.upsertProduct(it)
            },
            selectImage = selectImage,
            selectedImage = selectedImage
        )
    }






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
                            onClick = { if (it) viewModel.addCategory() },
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
                    contentPadding = PaddingValues(horizontal = 40.dp, vertical = 5.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item {
                        AnseButton(
                            onClick = {
                                if (it) {
                                    showAddNewProductDialog(true)
                                    selectedProduct.value = null
                                }
                            },
                            buttonStyle = AnseButtonStyle.newStyle(
                                shape = RoundedCornerShape(7.dp),
                                colors = AnseButtonColors(
                                    containerColor = Gray,
                                    contentColor = DarkGray,
                                ),
                                contentPadding = PaddingValues(10.dp)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(Modifier.height(60.dp))
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painterResource(id = R.drawable.manage_page_add_icon),
                                    modifier = Modifier.size(40.dp),
                                    contentDescription = "addProductIcon",
                                    colorFilter = ColorFilter.tint(it)
                                )
                                Spacer(Modifier.height(20.dp))
                                Text(
                                    stringResource(id = R.string.manage_page_add_product_button),
                                    color = it,
                                    fontSize = 20.sp
                                )
                                Spacer(Modifier.height(60.dp))
                                Spacer(Modifier.weight(1f))
                            }
                        }

                    }

                    itemsIndexed(productList) { index, item ->
                        item.ProductItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                selectedProduct.value = item
                            },
                        ) {
                            if (selectedProduct.value == item) {
                                Box(
                                    Modifier
                                        .matchParentSize()
                                        .background(DarkGray.copy(alpha = 0.7f))
                                ) {
                                    ProductMenu(
                                        Modifier.align(Alignment.Center),
                                        onClickProductMenu
                                    )
                                }
                            } else if (item.stopped || item.stock ?: Int.MAX_VALUE <= 0) {
                                Surface(
                                    color = Color.White.copy(alpha = 0.7f),
                                    modifier = Modifier.matchParentSize()
                                ) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



