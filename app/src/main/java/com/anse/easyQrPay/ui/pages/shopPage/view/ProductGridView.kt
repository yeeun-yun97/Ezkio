package com.anse.easyQrPay.ui.pages.shopPage.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anse.easyQrPay.ui.item.CategoryItem
import com.anse.easyQrPay.ui.item.ProductItem
import kr.yeeun0411.database.model.model.CategoryModel
import kr.yeeun0411.database.model.model.ProductModel

@Preview
@Composable
private fun ProductGridViewPreview() {
    ProductGridView(
        setCategory = {},
        increaseProductAmount = {},
        selectedCategory = rememberUpdatedState(null),
        categoryList = rememberUpdatedState(newValue = emptyList()),
        productList = rememberUpdatedState(newValue = emptyList()),
    )
}

@Composable
fun ProductGridView(
    selectedCategory: State<CategoryModel?>,
    setCategory: (CategoryModel?) -> Unit,
    categoryList: State<List<CategoryModel>>,
    productList: State<List<ProductModel>>,
    increaseProductAmount: (ProductModel) -> Unit,
) {
    Column(
        Modifier.padding(
            top = 22.dp,
            start = 20.dp,
            end = 20.dp
        )
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.Start),
            contentPadding = PaddingValues(10.dp),
        ) {
            item {
                CategoryItem(
                    category = null,
                    isSelected = selectedCategory.value == null,
                    onClick = { setCategory(null) },
                )
            }
            itemsIndexed(categoryList.value) { index, item ->
                CategoryItem(
                    category = item,
                    isSelected = selectedCategory.value == item,
                    onClick = { setCategory(item) },
                )
            }
        }
        Spacer(Modifier.height(15.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            productList.value.let {
                itemsIndexed(it) { index, item ->
                    item.ProductItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { increaseProductAmount(item) },
                    )
                }
            }
        }
    }

}