package com.anse.easyQrPay.ui.pages.shopPage.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.anse.easyQrPay.ui.item.QrCodeItem
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme
import com.anse.easyQrPay.utils.image.getTossQrBitmap
import kr.yeeun0411.data.model.BankAccountModel

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun AccountInfoViewPreview() {
    EasyQrPayTheme {
        AccountInfoView(
            price = 1000,
            bankAccount = BankAccountModel(
                accountHolder = "홍길동",
                bankName = "국민은행",
                accountNumber = "1234567890"
            ),
        )
    }
}

@Composable
fun AccountInfoView(
    price: Int,
    bankAccount: BankAccountModel,
) {
    val bitmapToss: ImageBitmap? = remember(price, bankAccount) {
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
                    accountHolderName = bankAccount.accountHolder,
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
    bitmap: ImageBitmap?,
    size: Dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QrCodeItem(size = size, bitmap = bitmap)
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

