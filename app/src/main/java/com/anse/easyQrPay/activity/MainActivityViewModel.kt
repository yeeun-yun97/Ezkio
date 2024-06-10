package com.anse.easyQrPay.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kr.yeeun0411.data.ProductRepository
import kr.yeeun0411.database.model.model.CategoryModel
import kr.yeeun0411.database.model.model.ProductModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _selectedImage = mutableStateOf<String?>(null)
    val selectedImage: State<String?> = _selectedImage
    val categoryList: Flow<List<CategoryModel>> = repository.getCategories()
    val productList: Flow<List<ProductModel>> get() = repository.getProducts()

    fun getProductList(categoryCode: String?): Flow<List<ProductModel>> = repository.getProductsByCategoryCode(categoryCode)

    fun clearSelectedImage() {

    }

    fun selectImage(base64: String?) {
        _selectedImage.value = base64
    }

    fun finishOrder(orderMap: Map<ProductModel, Int>, onFinished: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            orderMap.entries.forEach { (product, count) ->
                product.stock?.let {
                    repository.purchaseProduct(product, count)
                }
            }
            onFinished()
        }
    }

    fun upsertProduct(
        it: ProductModel,
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.upsertProduct(it)
    }

    fun deleteProduct(
        productCode: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteProduct(productCode)
    }

    fun upsertCategory(
        it: CategoryModel,
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.upsertCategory(it)
    }

    fun deleteCategory(
        categoryCode: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCategory(categoryCode)
    }


}