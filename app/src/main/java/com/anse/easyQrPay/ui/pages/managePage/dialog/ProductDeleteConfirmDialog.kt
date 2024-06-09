package com.anse.easyQrPay.ui.pages.managePage.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.component.button.CircleCancelTextButton
import com.anse.easyQrPay.ui.component.button.CircleSaveTextButton
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme

@Composable
@Preview
private fun ProductDeleteConfirmDialogPreview() {
    EasyQrPayTheme {
        ProductDeleteConfirmDialog(
            onDismissRequest = {},
            productCode = "123456",
            onDelete = {},
        )
    }
}

@Composable
fun ProductDeleteConfirmDialog(
    onDismissRequest: () -> Unit,
    productCode: String,
    onDelete: (String) -> Unit,
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                Modifier
                    .padding(top = 37.dp, start = 30.dp, end = 30.dp, bottom = 28.dp)
            ) {
                Text(
                    stringResource(
                        R.string.manage_page_product_delete_confirm_dialog_title
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.height(23.dp))
                Text(
                    stringResource(
                        R.string.manage_page_product_delete_confirm_dialog_message
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = DarkGray
                )
                Spacer(Modifier.height(35.dp))
                Row(
                    Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleCancelTextButton(
                        modifier = Modifier.weight(1f, false),
                        onClick = { if (it) onDismissRequest() }
                    )
                    Spacer(Modifier.width(14.dp))
                    CircleSaveTextButton(
                        modifier = Modifier.weight(1f, false),
                        onClick = {
                            if (it) onDelete(productCode)
                        }
                    )
                }
            }
        }
    }

}