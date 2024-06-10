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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.component.button.CircleCancelTextButton
import com.anse.easyQrPay.ui.component.button.CircleSaveTextButton
import com.anse.easyQrPay.ui.component.textField.EzkioTextField
import com.anse.easyQrPay.ui.component.textField.ezkioTextFieldStyle
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.LightGray
import kr.yeeun0411.data.model.CategoryModel

@Composable
@Preview
private fun CategoryEditDialogPreview() {
    CategoryEditDialog(
        onDismissRequest = {},
        category = null,
        saveData = {}
    )
}

@Composable
fun CategoryEditDialog(
    onDismissRequest: () -> Unit,
    category: CategoryModel?,
    saveData: (CategoryModel) -> Unit,
) {
    val context = LocalContext.current
    val name = rememberSaveable {
        mutableStateOf(category?.name ?: "")
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier.width(550.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                Modifier
                    .padding(top = 37.dp, start = 30.dp, end = 30.dp, bottom = 28.dp)
            ) {
                Text(
                    stringResource(
                        if (category == null) R.string.manage_page_category_edit_dialog_create_title
                        else R.string.manage_page_category_edit_dialog_title
                    ),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.height(23.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, LightGray),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White
                ) {
                    EzkioTextField(
                        modifier = Modifier.padding(vertical = 15.dp, horizontal = 12.dp),
                        value = name.value,
                        textStyle = ezkioTextFieldStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        ),
                        onValueChange = { name.value = it }
                    ) {
                        Text(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = DarkGray,
                            text = stringResource(R.string.manage_page_category_edit_dialog_category_name)
                        )
                    }
                }
                Spacer(Modifier.height(23.dp))
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
                        enabled = name.value.isNotBlank(),
                        onClick = {
                            if (it) saveData(
                                category?.copy(
                                    name = name.value
                                ) ?: CategoryModel(
                                    name = name.value
                                )
                            ) else if (name.value.isBlank()) {
                                Toast.makeText(context, R.string.manage_page_category_edit_dialog_category_name_empty_toast, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }

}