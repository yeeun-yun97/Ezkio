package com.anse.easyQrPay.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.R
import com.anse.easyQrPay.utils.image.stringToBitmap
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle
import kr.yeeun0411.data.model.ProductModel

@Composable
fun ProductModel.ProductItem(
    modifier: Modifier,
    onClick: () -> Unit,
    overlayView: @Composable BoxScope.() -> Unit = {},
) {
    ProductItem(
        modifier = modifier,
        onClick = onClick,

        stock = stock,
        image = image,
        name = name,
        price = price,

        overlayView = overlayView,
    )
}


@Composable
fun ProductItem(
    modifier: Modifier,
    onClick: () -> Unit,

    image: String?,
    stock: Int?,
    price: Int,
    name: String,

    overlayView: @Composable BoxScope.() -> Unit = {},
) {
    AnseButton(
        modifier = Modifier.then(modifier),
        onClick = { if (it) onClick() },
        buttonStyle = AnseButtonStyle.newStyle(
            shape = RoundedCornerShape(7.dp),
            colors = AnseButtonColors(
                contentColor = Color.Black,
                containerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            contentPadding = PaddingValues(),
        ),
        shadowElevation = 2.dp
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 17.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
        ) {
            Box(Modifier.clip(RoundedCornerShape(5.dp))) {
                Image(
                    painter = stringToBitmap(image)?.let { BitmapPainter(image = it) } ?: painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "product_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(1f, matchHeightConstraintsFirst = false),
                )
                stock?.let { stock ->
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
                                color = it,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraLight
                            )
                            Spacer(Modifier.width(12.dp))
                        }
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = name,
                color = it,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = price.toString(),
                fontSize = 18.sp,
                color = it,
                fontWeight = FontWeight.Bold,
            )
        }
        overlayView()
    }
}
