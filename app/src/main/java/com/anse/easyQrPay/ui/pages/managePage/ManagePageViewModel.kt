package com.anse.easyQrPay.ui.pages.managePage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anse.easyQrPay.MainActivity
import com.anse.easyQrPay.models.product.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ManagePageViewModel() : ViewModel() {

    fun addCategory() {

    }

    fun upsertProduct(it: Product, appContext: Context) = viewModelScope.launch(Dispatchers.IO) {
        val dao = MainActivity.getDao(appContext = appContext)
        dao.insertProduct(it)
    }

}