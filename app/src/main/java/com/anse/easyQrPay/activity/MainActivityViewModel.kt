package com.anse.easyQrPay.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.yeeun0411.data.ProductRepository
import kr.yeeun0411.data.model.CategoryModel
import kr.yeeun0411.data.model.ProductModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _selectedImage = mutableStateOf<String?>(null)
    val selectedImage: State<String?> = _selectedImage
    val productList: List<ProductModel> = listOf()
    val categoryList: List<CategoryModel> = listOf()
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

    fun addCategory() {

    }

    fun upsertProduct(
        it: ProductModel,
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.upsertProduct(it)
    }


}