package com.anse.easyQrPay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anse.easyQrPay.ui.pages.idlePage.IdlePage
import com.anse.easyQrPay.ui.pages.qrPage.QRPage
import com.anse.easyQrPay.ui.pages.shopPage.ShopPage
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyQrPayTheme {
                val navController = rememberNavController()
                NavHost(startDestination = "shop", navController = navController) {
//                    composable("home") {
//                        IdlePage(
//                            navigateToShopPage = { navController.navigate("shop") }
//                        )
//                    }

                    composable("shop") {
                        ShopPage(
//                            navigateToQRPage = { navController.navigate("qr") }
                        )
                    }

//                    composable("qr") {
//                        QRPage()
//                    }

                }


            }
        }
    }

    private fun composable(s: String, function: () -> Unit) {

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EasyQrPayTheme {
        Greeting("Android")
    }
}