package com.anse.easyQrPay.ui.pages.managePage.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.component.button.CircleCancelTextButton
import com.anse.easyQrPay.ui.component.button.CircleSaveTextButton
import com.anse.easyQrPay.ui.component.switch.EzkioSwitch
import com.anse.easyQrPay.ui.component.textField.EzkioTextField
import com.anse.easyQrPay.ui.component.textField.ezkioTextFieldStyle
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.LightGray
import com.anse.easyQrPay.utils.image.stringToBitmap
import kr.yeeun0411.database.model.model.ProductModel
import java.util.UUID

@Preview
@Composable
private fun ProductManageStatusDialogPreview() {
    ProductManageStatusDialog(
        onDismissRequest = {},
        product = ProductModel(
            productCode = UUID.randomUUID().toString(),
            name = "상품 이름",
            price = 10000,
            stock = 100,
            stopped = false,
            image = "sdfsdf"
        ),
        saveData = {}
    )
}

@Composable
fun ProductManageStatusDialog(
    onDismissRequest: () -> Unit,
    product: ProductModel,
    saveData: (ProductModel) -> Unit,
) {
    val stopped = rememberSaveable { mutableStateOf(product.stopped) }
    val useStock = rememberSaveable { mutableStateOf(product.stock != null) }
    val stock = rememberSaveable { mutableStateOf<Int?>(product.stock) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .heightIn(max = 600.dp)
                .width(550.dp),
            color = Color.White,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 37.dp, start = 30.dp, end = 30.dp, bottom = 28.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        stringResource(
                            R.string.manage_page_product_manage_status_dialog_title
                        ),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(30.dp))
                    AnimatedVisibility(
                        visible = !stopped.value,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(bottom = 33.dp)
                    ) {
                        //상품 미리보기
                        Box(
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(5.dp))
                                .size(155.dp)
                        ) {
                            Image(
                                painter = stringToBitmap(product.image)?.let { BitmapPainter(image = it) } ?: painterResource(R.drawable.ic_launcher_background),
                                contentDescription = "product_image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.aspectRatio(1f, matchHeightConstraintsFirst = false),
                            )
                            if (useStock.value) {
                                (stock.value ?: 0).let { stock ->
                                    Surface(
                                        shape = CircleShape,
                                        color = Color.White,
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .align(Alignment.TopEnd)
                                    ) {
                                        Row(
                                            modifier = Modifier.heightIn(min = 30.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Spacer(Modifier.width(6.dp))
                                            Image(
                                                painterResource(id = R.drawable.manage_page_stock_icon),
                                                modifier = Modifier.size(24.dp),
                                                contentDescription = "stock_icon",
                                            )
                                            Spacer(Modifier.width(3.dp))
                                            Text(
                                                stock.toString(),
                                                color = DarkGray,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.ExtraLight
                                            )
                                            Spacer(Modifier.width(12.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.manage_page_product_manage_status_dialog_stop_selling),
                            modifier = Modifier.widthIn(min = 80.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.width(44.dp))
                        EzkioSwitch(
                            modifier = Modifier.widthIn(min = 300.dp),
                            isOn = !stopped.value,
                            onClick = {
                                stopped.value = !it
                            }
                        ) { isOn ->
                            Spacer(Modifier.width(10.dp))
                            Text(
                                stringResource(
                                    if (isOn)
                                        R.string.manage_page_product_manage_status_dialog_stop_selling_switch_on
                                    else
                                        R.string.manage_page_product_manage_status_dialog_stop_selling_switch_off
                                ),
                                fontSize = 14.sp,
                                color = DarkGray,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.manage_page_product_manage_status_dialog_infinite_stock),
                            modifier = Modifier.widthIn(min = 80.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.width(44.dp))
                        EzkioSwitch(
                            modifier = Modifier.widthIn(min = 300.dp),
                            isOn = useStock.value,
                            onClick = {
                                useStock.value = it
                            }
                        ) { isOn ->
                            Spacer(Modifier.width(10.dp))
                            Text(
                                stringResource(
                                    if (isOn)
                                        R.string.manage_page_product_manage_status_dialog_infinite_stock_switch_on
                                    else
                                        R.string.manage_page_product_manage_status_dialog_infinite_stock_switch_off
                                ),
                                fontSize = 14.sp,
                                color = DarkGray,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    AnimatedVisibility(visible = useStock.value, modifier = Modifier.padding(vertical = 10.dp)) {
                        Column(Modifier.fillMaxWidth()) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                border = BorderStroke(1.dp, LightGray),
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White
                            ) {
                                EzkioTextField(
                                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 12.dp),
                                    value = stock.value?.toString() ?: "",
                                    onValueChange = { stock.value = it.toIntOrNull() },
                                    textStyle = ezkioTextFieldStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Start,
                                        color = Color.Black
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
                                ) {
                                    Text(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = DarkGray,
                                        text = stringResource(R.string.manage_page_product_manage_status_dialog_stock_count)
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))
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
                            if (it) saveData(
                                product.copy(
                                    stopped = stopped.value,
                                    stock = useStock.value.let { useStock ->
                                        if (!useStock) null
                                        else stock.value ?: 0
                                    }
                                )
                            )
                        }
                    )
                }
            }
        }
    }

}