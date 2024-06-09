package com.anse.easyQrPay.ui.pages.managePage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.yeeun0411.data.ProductRepository
import kr.yeeun0411.data.model.ProductModel
import javax.inject.Inject


@HiltViewModel
class ManagePageViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _selectedImage = mutableStateOf<String?>(null)
    val selectedImage: State<String?> = _selectedImage
    val productList: List<ProductModel> = listOf()
    fun clearSelectedImage() {

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