package com.anse.easyQrPay.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.R
import com.anse.easyQrPay.models.product.Product
import com.anse.easyQrPay.ui.pages.shopPage.StringToBitmap
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    cartItemCount: Int,
) {
    val enabled = rememberUpdatedState(newValue = if (product.stock == null) true else (product.stock - cartItemCount) > 0)
    Box {
        AnseButton(
            enabled = enabled.value,
            modifier = Modifier.fillMaxWidth(),
            onClick = { if (it) onClick() },
            buttonStyle = AnseButtonStyle.newStyle(
                shape = RoundedCornerShape(20.dp),
                colors = AnseButtonColors(
                    contentColor = Color.Black,
                    containerColor = Color.White,
                ),
                contentPadding = PaddingValues(12.dp),
            )
        ) {
            Column(Modifier.fillMaxWidth()) {
                Image(
                    painter = StringToBitmap(product.image)?.let {
                        BitmapPainter(image = it)
                    } ?: painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "product_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(1f, matchHeightConstraintsFirst = false),
                    alpha = if (enabled.value) 1f else 0.2f
                )
                Spacer(Modifier.height(28.dp))
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = product.price.toString(),
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(5.dp))
            }
        }

        if (!enabled.value) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.align(BiasAlignment(verticalBias = -0.4f, horizontalBias = 0f))
            ) {
                Text(
                    text = "완판!!\n감사합니다:)",
                    modifier = Modifier.padding(25.dp),
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }

    }
}
