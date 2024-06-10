package com.anse.easyQrPay.ui.pages.shopPage.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.anse.easyQrPay.models.BankAccountValue
import com.anse.easyQrPay.models.accountNumberNoDashOrSpace
import com.anse.easyQrPay.models.bankNameUrlEncoded
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun AccountInfoViewPreview() {
    EasyQrPayTheme {
        AccountInfoView(
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

fun getTossQrBitmap(
    bankName: String,
    accountNumber: String,
    price: Int? = null,
): ImageBitmap {
    val url = "supertoss://send?${price?.let { "amount = ${it}&" }}bank=${bankName.bankNameUrlEncoded()}&accountNo=${accountNumber.accountNumberNoDashOrSpace()}&origin=qr"
    return net.glxn.qrgen.android.QRCode
        .from(url)
        .withSize(300, 300)
        .bitmap()
        .asImageBitmap()
}

@Composable
fun AccountInfoView(
    price: Int,
    bankAccount: BankAccountValue = exampleBankAccount,
) {
    val bitmapToss: ImageBitmap = remember(price) {
        getTossQrBitmap(
            bankName = bankAccount.bankName,
            accountNumber = bankAccount.accountNumber,
            price = price
        )
    }

    Surface(color = Color(0xFFE9E9E9)) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.shop_page_account_info_title),
                style = LocalTextStyle.current.copy(
                    fontSize = 30.sp,
                    lineHeight = 36.sp,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                buildAnnotatedString {
                    append(
                        stringResource(id = R.string.shop_page_account_info_price_message_prefix)
                    )
                    pushStyle(style = SpanStyle(fontSize = 80.sp, fontWeight = FontWeight.Bold))
                    append(price.toString())
                    pop()
                    append(
                        stringResource(id = R.string.shop_page_account_info_price_message_suffix)
                    )
                },
                style = LocalTextStyle.current.copy(
                    fontSize = 40.sp,
                    lineHeight = 46.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(Modifier.height(40.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                QRItem(
                    stringResource(id = R.string.shop_page_account_info_qr_title),
                    bitmapToss,
                    size = 300.dp
                )
                Spacer(Modifier.width(36.dp))
                AccountInfoItem(
                    accountHolderName = bankAccount.accountHolderName,
                    bankName = bankAccount.bankName,
                    accountNumber = bankAccount.accountNumber
                )
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
            color = Color.White,
            shape = RoundedCornerShape(8.dp),
        ) {
            Image(
                modifier = Modifier.size(size),
                bitmap = bitmap,
                contentDescription = "qrCode"
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            name,
            modifier = Modifier.width(size),
            style = LocalTextStyle.current.copy(
                fontSize = 30.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun AccountInfoItem(
    accountHolderName: String,
    bankName: String,
    accountNumber: String,
) {
    Column {
        Text(
            stringResource(id = R.string.shop_page_account_info_account_title),
            style = LocalTextStyle.current.copy(
                fontSize = 30.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start
            )
        )
        Spacer(Modifier.height(25.dp))
        Text(
            "${bankName} ${accountHolderName}",
            modifier = Modifier.align(CenterHorizontally),
            style = LocalTextStyle.current.copy(
                fontSize = 36.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        )
        Spacer(Modifier.height(8.dp))
        Text(
            accountNumber,
            modifier = Modifier.align(CenterHorizontally),
            style = LocalTextStyle.current.copy(
                fontSize = 48.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        )
    }
}

