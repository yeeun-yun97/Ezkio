package com.anse.easyQrPay.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.pages.managePage.ManagePage
import com.anse.easyQrPay.ui.pages.managePage.ManagePageViewModel
import com.anse.easyQrPay.ui.pages.shopPage.ShopPage
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme
import dagger.hilt.android.AndroidEntryPoint
import kr.yeeun0411.data.model.ProductModel
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainActivityViewModel = viewModel()
            val managePageViewModel: ManagePageViewModel = viewModel()

            val finishOrder: (Map<ProductModel, Int>, () -> Unit) -> Unit = { orderMap, onFinished ->
                viewModel.finishOrder(orderMap, onFinished)
            }

            val imageLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri: Uri? ->
                    uri?.let { imageURI: Uri ->
                        if (imageURI.toString().startsWith("content")) {
                            val permission =
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                else
                                    Manifest.permission.READ_MEDIA_IMAGES
                            if (ContextCompat.checkSelfPermission(
                                    this,
                                    permission
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                viewModel.selectImage(
                                    imageURI.getImageBase64OrNull(applicationContext)
                                )
                            } else {
                                ActivityCompat.requestPermissions(
                                    this,
                                    arrayOf(permission),
                                    PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                                )
                            }
                        } else {
                            viewModel.selectImage(
                                imageURI.getImageBase64OrNull(applicationContext)
                            )
                        }
                    }
                }
            )
            val openImage = { imageLauncher.launch("image/*") }


            EasyQrPayTheme {
                val navController = rememberNavController()
                NavHost(startDestination = "manage", navController = navController) {
                    composable("shop") {
                        ShopPage(
                            productList = viewModel.productList,
                            finishOrder = finishOrder,
                            categoryList = viewModel.categoryList,
                        )
                    }

                    composable("manage") {
                        ManagePage(
                            viewModel = managePageViewModel,
                            categoryList = listOf(),
                            productList = listOf(),
                            selectImage = openImage,
                            selectedImage = viewModel.selectedImage,
                            clearSelectedImage = { viewModel.clearSelectedImage() },
                            navigateToSetting = { /*navController.navigate("setting")*/ },
                            navigateToStatics = { /*navController.navigate("statics")*/ },
                            navigateToKiosk = { navController.navigate("shop") }
                        )
                    }
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // 권한이 승인된 경우, 작업 수행
                } else {
                    // 권한이 거부된 경우, 사용자에게 알림
                    Toast.makeText(
                        this,
                        "Permission denied to read your External storage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
            // 다른 'when' 브랜치를 처리하려면
            else -> {
                // super 호출
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}

private fun getBase64FromDrawableRes(resources: Resources, drawableRes: Int): String {
    val bitmap = BitmapFactory.decodeResource(resources, drawableRes)
    val byteStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
    val byteArray = byteStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}


private fun Uri.getImageBase64OrNull(context: Context): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(this)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
        val byteArray = byteStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (exception: Exception) {
        Toast.makeText(context, R.string.manage_page_fail_image_import_error_message, Toast.LENGTH_SHORT).show()
        null
    }
}