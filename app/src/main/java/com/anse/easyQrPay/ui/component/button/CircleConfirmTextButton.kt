package com.anse.easyQrPay.ui.component.button

import androidx.compose.foundation.layout.PaddingValues
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
import com.anse.easyQrPay.ui.theme.Primary
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle

@Composable
fun CircleConfirmTextButton(
    modifier: Modifier,
    enabled: Boolean = true,
    onClick: (Boolean) -> Unit,
) {
    AnseButton(
        modifier = Modifier.then(modifier),
        enabled = enabled,
        onClick = onClick,
        buttonStyle = AnseButtonStyle.newStyle(
            shape = CircleShape,
            colors = AnseButtonColors(
                contentColor = Color.Black,
                containerColor = Primary,
            ),
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp)
        )
    ) {
        Text(
            stringResource(R.string.common_button_confirm),
            modifier = Modifier.align(Alignment.Center),
            color = it,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}