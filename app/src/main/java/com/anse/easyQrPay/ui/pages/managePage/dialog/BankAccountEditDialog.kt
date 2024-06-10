package com.anse.easyQrPay.ui.pages.managePage.dialog

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.anse.easyQrPay.R
import com.anse.easyQrPay.utils.string.accountNumberNoDashOrSpace
import com.anse.easyQrPay.ui.component.button.CircleCancelTextButton
import com.anse.easyQrPay.ui.component.button.CircleSaveTextButton
import com.anse.easyQrPay.ui.component.switch.EzkioSwitch
import com.anse.easyQrPay.ui.component.textField.EzkioTextField
import com.anse.easyQrPay.ui.component.textField.ezkioTextFieldStyle
import com.anse.easyQrPay.ui.item.QrCodeItem
import com.anse.easyQrPay.utils.image.getTossQrBitmap
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.LightGray
import com.anse.easyQrPay.utils.string.accountNumberAvailable
import kr.yeeun0411.data.model.BankAccountModel

@Composable
fun BankAccountEditDialog(
    onDismissRequest: () -> Unit,
    bankAccount: BankAccountModel?,
    saveData: (BankAccountModel) -> Unit,
) {
    val context = LocalContext.current
    val bankName = rememberSaveable { mutableStateOf(bankAccount?.bankName ?: "") }
    val accountNumber = rememberSaveable { mutableStateOf(bankAccount?.accountNumber ?: "") }
    val accountHolder = rememberSaveable { mutableStateOf(bankAccount?.accountHolder ?: "") }
    val useQr = rememberSaveable { mutableStateOf(bankAccount?.useQr ?: true) }
    val qrBitmap = remember(
        bankName.value,
        accountNumber.value,
    ) {
        if (bankName.value.isBlank() || accountNumber.value.isBlank() || accountHolder.value.isBlank()) {
            null
        } else {
            getTossQrBitmap(
                bankName = bankName.value,
                accountNumber = accountNumber.value.accountNumberNoDashOrSpace()
            )
        }
    }

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
                        if (bankAccount == null) R.string.manage_page_bank_account_edit_dialog_create_title
                        else R.string.manage_page_bank_account_edit_dialog_title
                    ),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.height(23.dp))
                Row {
                    QRPreview(
                        bitmap = qrBitmap,
                        useQr = useQr,
                        setUseQr = { useQr.value = it }
                    )
                    Spacer(Modifier.width(28.dp))
                    Column(Modifier.width(400.dp)) {
                        Text(
                            stringResource(R.string.manage_page_bank_account_edit_dialog_account_holder_name),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(10.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, LightGray),
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White
                        ) {
                            EzkioTextField(
                                modifier = Modifier.padding(vertical = 15.dp, horizontal = 12.dp),
                                value = accountHolder.value,
                                maxLines = 1,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                textStyle = ezkioTextFieldStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Start,
                                    color = Color.Black
                                ),
                                onValueChange = { accountHolder.value = it }
                            ) {
                                Text(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = DarkGray,
                                    text = stringResource(R.string.manage_page_bank_account_edit_dialog_account_holder_name)
                                )
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(
                            stringResource(R.string.manage_page_bank_account_edit_dialog_bank_name),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(10.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, LightGray),
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White
                        ) {
                            EzkioTextField(
                                modifier = Modifier.padding(vertical = 15.dp, horizontal = 12.dp),
                                value = bankName.value,
                                maxLines = 1,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                onValueChange = { bankName.value = it },
                                textStyle = ezkioTextFieldStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Start,
                                    color = Color.Black
                                ),
                            ) {
                                Text(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = DarkGray,
                                    text = stringResource(R.string.manage_page_bank_account_edit_dialog_bank_name)
                                )
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(
                            stringResource(R.string.manage_page_bank_account_edit_dialog_account_number),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(10.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, LightGray),
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White
                        ) {
                            EzkioTextField(
                                modifier = Modifier.padding(vertical = 15.dp, horizontal = 12.dp),
                                value = accountNumber.value,
                                maxLines = 1,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                onValueChange = {
                                    if (it.accountNumberAvailable()) {
                                        accountNumber.value = it
                                    }
                                },
                                textStyle = ezkioTextFieldStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Start,
                                    color = Color.Black
                                ),
                            ) {
                                Text(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = DarkGray,
                                    text = stringResource(R.string.manage_page_bank_account_edit_dialog_account_number)
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(110.dp))
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
                        enabled = bankName.value.isNotBlank() && accountHolder.value.isNotBlank() && accountNumber.value.isNotBlank(),
                        onClick = {
                            if (it) saveData(
                                bankAccount?.copy(
                                    bankName = bankName.value,
                                    accountHolder = accountHolder.value,
                                    accountNumber = accountNumber.value,
                                    useQr = useQr.value
                                ) ?: BankAccountModel(
                                    bankName = bankName.value,
                                    accountHolder = accountHolder.value,
                                    accountNumber = accountNumber.value,
                                    useQr = useQr.value
                                )
                            ) else if (bankName.value.isBlank()) {
                                Toast.makeText(context, R.string.manage_page_bank_account_edit_dialog_product_bank_name_empty_toast, Toast.LENGTH_SHORT).show()
                            } else if (accountHolder.value.isBlank()) {
                                Toast.makeText(context, R.string.manage_page_bank_account_edit_dialog_product_account_holder_empty_toast, Toast.LENGTH_SHORT).show()
                            } else if (accountNumber.value.isBlank()) {
                                Toast.makeText(context, R.string.manage_page_bank_account_edit_dialog_product_account_number_empty_toast, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }

}

@Composable
private fun QRPreview(
    bitmap: ImageBitmap?,
    useQr: State<Boolean>,
    setUseQr: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.width(160.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QrCodeItem(
            size = 160.dp,
            bitmap = bitmap,
            shadowElevation = 4.dp
        )
        Spacer(Modifier.height(15.dp))
        EzkioSwitch(
            modifier = Modifier.widthIn(min = 300.dp),
            isOn = useQr.value,
            onClick = {
                setUseQr(it)
            }
        ) { isOn ->
            Spacer(Modifier.width(10.dp))
            Text(
                stringResource(
                    if (isOn)
                        R.string.manage_page_bank_account_edit_dialog_use_qr_switch_on
                    else
                        R.string.manage_page_bank_account_edit_dialog_use_qr_switch_off
                ),
                fontSize = 14.sp,
                color = DarkGray,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }
        Spacer(Modifier.height(8.dp))
        if (useQr.value) {
            Text(
                stringResource(id = R.string.manage_page_bank_account_edit_dialog_qr_code_message),
                fontSize = 12.sp,
                color = DarkGray,
                fontWeight = FontWeight.Normal,
            )
        }

    }
}