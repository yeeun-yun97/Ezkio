package com.anse.easyQrPay.ui.pages.shopPage.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.component.button.CircleConfirmTextButton
import com.anse.easyQrPay.ui.theme.DarkGray

@Composable
fun FinishOrderDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier.width(380.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 37.dp, start = 30.dp, end = 30.dp, bottom = 28.dp)
            ) {
                Text(
                    stringResource(
                        R.string.shop_page_finish_order_dialog_title
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.height(23.dp))
                Text(
                    stringResource(
                        R.string.shop_page_finish_order_dialog_message,
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = DarkGray
                )
                Spacer(Modifier.height(35.dp))
                CircleConfirmTextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (it) onConfirm()
                    }
                )
            }
        }
    }
}