package com.anse.easyQrPay.ui.pages.qrPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.R
import com.anse.easyQrPay.core.models.BankAccountValue
import com.anse.easyQrPay.core.models.accountNumberNoDashOrSpace
import com.anse.easyQrPay.core.models.bankNameUrlEncoded
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun QRPagePreview() {
    EasyQrPayTheme {
        QRPage(
            price = 1000,
            bankAccount = exampleBankAccount,
        )
    }
}

val exampleBankAccount = BankAccountValue(
    bankName = "하나은행",
    accountHolderName = "박하나",
    accountNumber = "176-123456-78901"
)

@Composable
fun QRPage(
    price: Int,
    bankAccount: BankAccountValue = exampleBankAccount,
) {
    val url = "supertoss://send?amount=${price}&bank=${bankAccount.bankNameUrlEncoded()}&accountNo=${bankAccount.accountNumberNoDashOrSpace()}&origin=qr"
    Log.d("QRUrl", "$url")
    val bitmapToss: ImageBitmap = remember(price) {
        net.glxn.qrgen.android.QRCode
            .from(url)
            .withSize(300, 300)
            .bitmap()
            .asImageBitmap()
    }

    Surface(color = Color(0xFFE9E9E9)) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.qr_page_title),
                style = LocalTextStyle.current.copy(
                    fontSize = 50.sp,
                    lineHeight = 64.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                buildAnnotatedString {
                    append(
                        stringResource(id = R.string.qr_page_price_message_prefix)
                    )
                    pushStyle(style = SpanStyle(fontSize = 150.sp))
                    append(price.toString())
                    pop()
                    append(
                        stringResource(id = R.string.qr_page_price_message_suffix)
                    )
                },
                style = LocalTextStyle.current.copy(
                    fontSize = 50.sp,
                    lineHeight = 64.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                QRItem(
                    stringResource(id = R.string.qr_page_toss_qr_title),
                    bitmapToss,
                    size = 300.dp
                )
                Column {
                    Text(
                        stringResource(id = R.string.qr_page_bank_account_title),
                        style = LocalTextStyle.current.copy(
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(Modifier.height(15.dp))
                    Text(
                        "${bankAccount.bankName} ${bankAccount.accountHolderName}",
                        style = LocalTextStyle.current.copy(
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        bankAccount.accountNumber,
                        style = LocalTextStyle.current.copy(
                            fontSize = 70.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun QRItem(
    name: String,
    bitmap: ImageBitmap,
    size: Dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.padding(8.dp),
            color = androidx.compose.ui.graphics.Color.White,
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 10.dp,
        ) {
            Image(
                modifier = Modifier.size(size),
                bitmap = bitmap,
                contentDescription = "qrCode"
            )
        }
        Text(
            name,
            style = LocalTextStyle.current.copy(
                fontSize = 40.sp,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

