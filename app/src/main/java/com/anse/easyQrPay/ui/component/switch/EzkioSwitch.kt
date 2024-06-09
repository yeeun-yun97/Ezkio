package com.anse.easyQrPay.ui.component.switch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.anse.easyQrPay.ui.theme.Gray
import com.anse.easyQrPay.ui.theme.Primary
import com.anse.uikit.components.button.AnseButtonNoStyle

@Composable
fun EzkioSwitch(
    modifier: Modifier,
    isOn: Boolean,
    onClick: (Boolean) -> Unit,
    content: @Composable RowScope.(Boolean) -> Unit,
) {
    AnseButtonNoStyle(
        modifier = Modifier.then(modifier),
        onClick = { if (it) onClick(!isOn) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                color = if (isOn) Primary else Gray,
                shape = CircleShape,
                modifier = Modifier
                    .size(width = 52.dp, height = 30.dp)
            ) {
                Box(Modifier.padding(vertical = 4.dp, horizontal = 3.dp)) {
                    Surface(
                        color = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .size(22.dp)
                            .align(if (isOn) Alignment.CenterEnd else Alignment.CenterStart)
                    ) {}
                }
            }
            content(isOn)
        }
    }

}