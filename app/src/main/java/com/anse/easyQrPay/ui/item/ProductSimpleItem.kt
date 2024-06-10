package com.anse.easyQrPay.ui.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.ui.theme.Gray
import com.anse.easyQrPay.ui.theme.LightPrimary
import com.anse.easyQrPay.utils.string.toPriceString
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle

@Composable
fun ProductSimpleItem(
    modifier: Modifier,
    name: String,
    price: Int,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    topView: @Composable ColumnScope.(selected: Boolean) -> Unit,
) {
    val context = LocalContext.current
    AnseButton(
        modifier = Modifier.then(modifier),
        onClick = {
            if (it) onClick?.invoke()
        },
        enabled = onClick?.let { true },
        buttonStyle = AnseButtonStyle.newStyle(
            shape = RoundedCornerShape(7.dp),
            border = if (selected) null else BorderStroke(1.dp, Gray),
            colors = AnseButtonColors(
                containerColor = if (selected) LightPrimary else Color.White,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(22.dp)
        )
    ) {
        Column {
            topView(selected)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                fontSize = 22.sp
            )
            Spacer(Modifier.height(26.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = price.toPriceString(context),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.End
            )
        }
    }
}