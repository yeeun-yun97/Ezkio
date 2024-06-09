package com.anse.easyQrPay.ui.pages.managePage.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
import com.anse.easyQrPay.ui.item.ProductItem
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme
import com.anse.easyQrPay.ui.theme.LightGray
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle
import kr.yeeun0411.data.model.ProductModel

@Preview
@Composable
private fun ProductEditDialogPreview() {
    EasyQrPayTheme {
        ProductEditDialog(
            onDismissRequest = {},
            product = null,
            saveData = {},
            selectImage = {},
            selectedImage = rememberUpdatedState(newValue = null)
        )
    }
}

@Composable
fun ProductEditDialog(
    onDismissRequest: () -> Unit,
    product: ProductModel?,
    saveData: (ProductModel) -> Unit,
    selectedImage: State<String?>,
    selectImage: () -> Unit,
) {
    val name = rememberSaveable { mutableStateOf(product?.name ?: "") }
    val price = rememberSaveable { mutableStateOf(product?.price) }
    val image = remember {
        derivedStateOf {
            selectedImage.value ?: product?.image
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
                        if (product == null) R.string.manage_page_new_product_dialog_title
                        else R.string.manage_page_product_edit_dialog_title
                    ),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.height(23.dp))
                Row {
                    ProductItem(
                        modifier = Modifier.width(250.dp),
                        name = name.value,
                        price = price.value ?: 0,
                        image = image.value,
                        stock = null,
                        onClick = { /*TODO*/ }
                    )
                    Spacer(Modifier.width(20.dp))
                    Column(Modifier.width(400.dp)) {
                        Text(
                            stringResource(R.string.manage_page_product_edit_dialog_product_name),
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
                                    text = stringResource(R.string.manage_page_product_edit_dialog_product_name)
                                )
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(
                            stringResource(R.string.manage_page_product_edit_dialog_product_price),
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
                                value = price.value?.toString() ?: "",
                                onValueChange = { price.value = it.toIntOrNull() },
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
                                    text = stringResource(R.string.manage_page_product_edit_dialog_product_price)
                                )
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(
                            stringResource(R.string.manage_page_product_edit_dialog_product_image),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(10.dp))
                        AnseButton(
                            onClick = { if (it) selectImage() },
                            buttonStyle = AnseButtonStyle.newStyle(
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, LightGray),
                                colors = AnseButtonColors(
                                    containerColor = Color.White,
                                    contentColor = DarkGray
                                ),
                                contentPadding = PaddingValues(9.dp)
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painterResource(id = R.drawable.manage_page_image_icon),
                                    modifier = Modifier.size(30.dp),
                                    contentDescription = "image_icon",
                                    colorFilter = ColorFilter.tint(color = it)
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    stringResource(R.string.manage_page_product_edit_dialog_product_image_hint),
                                    color = it,
                                    fontSize = 14.sp
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
                        enabled = name.value.isNotBlank() && image.value != null,
                        onClick = {
                            if (it) saveData(
                                ProductModel(
                                    name = name.value,
                                    price = price.value ?: 0,
                                    image = image.value!!,
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}