package com.anse.easyQrPay.ui.item

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.theme.DarkGray
import com.anse.easyQrPay.ui.theme.Primary
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle
import kr.yeeun0411.database.model.model.CategoryModel

@Composable
fun CategoryItem(
    category: CategoryModel?,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    AnseButton(
        onClick = { if (it) onClick() },
        modifier = Modifier
            .widthIn(min = 100.dp)
            .heightIn(min = 40.dp),
        buttonStyle = AnseButtonStyle.newStyle(
            shape = CircleShape,
            colors = AnseButtonColors(
                contentColor = if (isSelected) Color.Black else DarkGray,
                containerColor = if (isSelected) Primary else Color.White,
            ),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
        )
    ) {
        Text(
            text = category?.name ?: stringResource(R.string.category_all_button),
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = if (isSelected) FontWeight.Normal else FontWeight.Light,
            color = it,
        )
    }
}