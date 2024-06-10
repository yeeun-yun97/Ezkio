package com.anse.easyQrPay.ui.pages.shopPage

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.anse.easyQrPay.R
import com.anse.easyQrPay.activity.MainActivityViewModel
import com.anse.easyQrPay.ui.component.state.rememberUiVisibility
import com.anse.easyQrPay.ui.item.ProductSimpleItem
import com.anse.easyQrPay.ui.pages.shopPage.dialog.ClearShoppingListConfirmDialog
import com.anse.easyQrPay.ui.pages.shopPage.dialog.FinishOrderDialog
import com.anse.easyQrPay.ui.pages.shopPage.view.AccountInfoView
import com.anse.easyQrPay.ui.pages.shopPage.view.ProductGridView
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.Gray
import com.anse.easyQrPay.ui.theme.LightGray
import com.anse.easyQrPay.ui.theme.Primary
import com.anse.easyQrPay.utils.string.toPriceString
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonNoStyle
import com.anse.uikit.components.button.AnseButtonStyle
import kr.yeeun0411.data.model.CategoryModel
import kr.yeeun0411.data.model.ProductModel
import kotlin.math.min


@Composable
fun ShopPage(
    popBackToManagePage: () -> Unit,
    viewModel: MainActivityViewModel,
    categoryList: State<List<CategoryModel>>,
    finishOrder: (Map<ProductModel, Int>, () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val selectedCategory = rememberSaveable { mutableStateOf<CategoryModel?>(null) }
    val selectedProductCode = rememberSaveable { mutableStateOf<String?>(null) }
    val shoppingList = remember { mutableStateMapOf<ProductModel, Int>() }
    val shoppingPrice = remember {
        derivedStateOf {
            shoppingList.entries.map { (product, amount) -> product.price * amount }.sum()
        }
    }
    val productList = remember(selectedCategory.value) {
        selectedCategory.value.let {
            if (it == null) viewModel.productList
            else viewModel.getProductList(it.categoryCode)
        }
    }.collectAsState(initial = emptyList())
    val bankAccount = viewModel.bankAccount.collectAsState(initial = null)

    val setItemCount = { it: Int, item: ProductModel ->
        if (it <= 0) shoppingList.remove(item)
        else shoppingList[item] = min(it, item.stock ?: Int.MAX_VALUE)
        Unit
    }

    val increaseItemCount = { item: ProductModel ->
        if (if (item.stopped) false
            else item.stock.let {
                if (it == null) true
                else (it - (shoppingList[item] ?: 0)) > 0
            }
        )
            shoppingList[item] = min((shoppingList[item] ?: 0) + 1, item.stock ?: Int.MAX_VALUE)
    }

    val isCalculating = rememberSaveable { mutableStateOf(false) }

    val showFinishSellingDialog = rememberSaveable { mutableStateOf(false) }
    if (showFinishSellingDialog.value) {
        FinishOrderDialog(
            onDismissRequest = { showFinishSellingDialog.value = false },
            onConfirm = {
                finishOrder(
                    shoppingList
                ) {
                    shoppingList.clear()
                    isCalculating.value = false
                    showFinishSellingDialog.value = false
                    selectedCategory.value = null
                }
            }
        )
    }

    val (clearShoppingListConfirmDialogVisibility, showClearShoppingListConfirmDialog) = rememberUiVisibility()
    if (clearShoppingListConfirmDialogVisibility.value) {
        ClearShoppingListConfirmDialog(
            onDismissRequest = { showClearShoppingListConfirmDialog(false) },
            onClear = {
                shoppingList.clear()
                showClearShoppingListConfirmDialog(false)
            }
        )
    }


    BackHandler {
        isCalculating.value = false
    }

    Row() {
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            color = LightGray
        ) {
            if (!isCalculating.value) {
                ProductGridView(
                    increaseProductAmount = increaseItemCount,
                    selectedCategory = selectedCategory,
                    setCategory = { selectedCategory.value = it },
                    categoryList = categoryList,
                    productList = productList,
                )
            } else {
                bankAccount.value?.let {
                    AccountInfoView(
                        price = shoppingPrice.value,
                        bankAccount = it
                    )
                }
            }
        }
        Surface(
            modifier = Modifier
                .width(460.dp)
                .fillMaxHeight(),
            color = Color.White,
        ) {
            Column(
                Modifier
                    .fillMaxSize(), verticalArrangement = Arrangement.Bottom
            ) {
                Surface(color = Color.White) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 40.dp, bottom = 20.dp)
                    ) {
                        AnseButton(
                            onClick = {
                                if (isCalculating.value) {
                                    showFinishSellingDialog.value = true
                                } else {
                                    popBackToManagePage()
                                }
                            }, buttonStyle = AnseButtonStyle.newStyle(
                                colors = AnseButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Black
                                ),
                                contentPadding = PaddingValues(0.dp)
                            )
                        ) {
                            Text(
                                stringResource(id = R.string.shop_page_shopping_list_title),
                                fontSize = 28.sp,
                                color = it,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        if (!isCalculating.value) {
                            AnseButtonNoStyle(
                                onClick = {
                                    if (it) {
                                        showClearShoppingListConfirmDialog(true)
                                    }
                                }
                            ) {
                                Box(Modifier.size(40.dp)) {
                                    Image(
                                        painterResource(R.drawable.manage_page_delete_icon),
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(40.dp),
                                        contentDescription = "statsButton",
                                        colorFilter = ColorFilter.tint(DarkGray)
                                    )
                                }
                            }
                        }
                    }
                }
                Divider(
                    color = Gray,
                    thickness = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(vertical = 24.dp)
                    ) {
                        itemsIndexed(shoppingList.toList()) { index, item ->
                            val product = item.first
                            val count = item.second
                            ShoppingItem(
                                product = product,
                                count = count,
                                selected = !isCalculating.value && (selectedProductCode.value == product.productCode),
                                onClick = if (isCalculating.value) null else {
                                    { selectedProductCode.value = product.productCode }
                                },
                                setItemCount = setItemCount,
                            )
                        }
                    }
                    if (shoppingList.isEmpty()) {
                        Text(
                            stringResource(R.string.shop_page_shopping_list_empty),
                            fontSize = 22.sp,
                            lineHeight = 25.sp,
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp)
                ) {
                    if (!isCalculating.value && shoppingList.isNotEmpty()) {
                        Spacer(Modifier.height(15.dp))
                        Text(stringResource(R.string.shop_page_shopping_list_total_price_title), fontSize = 20.sp, lineHeight = 20.sp, fontWeight = FontWeight.Normal, color = Color.Black)
                        Spacer(Modifier.height(8.dp))
                        Text(shoppingPrice.value.toPriceString(context), fontSize = 40.sp, lineHeight = 44.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Spacer(Modifier.height(22.dp))
                        AnseButton(
                            onClick = { if (it) isCalculating.value = true },
                            modifier = Modifier.fillMaxWidth(),
                            buttonStyle = AnseButtonStyle.newStyle(
                                shape = CircleShape,
                                colors = AnseButtonColors(
                                    contentColor = Color.Black,
                                    containerColor = Primary,
                                ),
                                contentPadding = PaddingValues(vertical = 20.dp),
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.shop_page_shopping_list_order_start_button),
                                modifier = Modifier.align(Alignment.Center),
                                color = it,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    } else if (isCalculating.value) {
                        AnseButton(
                            onClick = {
                                isCalculating.value = false
                            },
                            buttonStyle = AnseButtonStyle.newStyle(
                                shape = CircleShape,
                                colors = AnseButtonColors(
                                    contentColor = DarkGray,
                                    containerColor = LightGray,
                                ),
                                contentPadding = PaddingValues(vertical = 20.dp),
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(R.string.shop_page_shopping_list_order_cancel_button),
                                modifier = Modifier.align(Alignment.Center),
                                color = it,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
                Spacer(Modifier.height(33.dp))
            }
        }
    }
}

@Composable
private fun ShoppingItem(
    setItemCount: (Int, ProductModel) -> Unit,
    product: ProductModel,
    count: Int,
    selected: Boolean,
    onClick: (() -> Unit)?,
) {
    ProductSimpleItem(
        modifier = Modifier.fillMaxWidth(),
        name = "${product.name}  *$count",
        price = product.price * count,
        selected = selected,
        onClick = onClick,
        topView = { selected ->
            if (selected) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AnseButtonNoStyle(onClick = {
                        setItemCount(count - 1, product)
                    }) {
                        Image(
                            painterResource(id = R.drawable.shop_page_reduce_amount_icon),
                            contentDescription = "reduce_amount_icon",
                        )
                    }
                    Spacer(Modifier.width(13.dp))
                    AnseButtonNoStyle(onClick = {
                        setItemCount(count + 1, product)
                    }) {
                        Image(
                            painterResource(id = R.drawable.shop_page_add_amount_icon),
                            contentDescription = "increase_add_icon",
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    )

}