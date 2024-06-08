package com.anse.easyQrPay

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainActivityViewModel() : ViewModel() {


    private val _selectedImage = mutableStateOf<String?>(null)
    val selectedImage: State<String?> = _selectedImage
    fun clearSelectedImage() {

    }

    fun selectImage(base64: String?) {
        _selectedImage.value = base64
    }


}