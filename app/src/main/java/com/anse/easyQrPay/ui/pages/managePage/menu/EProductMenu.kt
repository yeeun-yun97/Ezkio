package com.anse.easyQrPay.ui.pages.managePage.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.R
import com.anse.uikit.components.button.AnseButtonNoStyle

enum class EProductMenu {
    EDIT_INFO,
    MANAGE_STOCK,
    DELETE,
}

@Composable
private fun EProductMenu.MenuItem(
    onClick: () -> Unit,
) {
    val iconRes = when (this) {
        EProductMenu.EDIT_INFO -> R.drawable.manage_page_edit_icon
        EProductMenu.MANAGE_STOCK -> R.drawable.manage_page_stock_icon
        EProductMenu.DELETE -> R.drawable.manage_page_delete_icon
    }

    val textRes = when (this) {
        EProductMenu.EDIT_INFO -> R.string.manage_page_product_menu_edit_info
        EProductMenu.MANAGE_STOCK -> R.string.manage_page_product_menu_manage_stock
        EProductMenu.DELETE -> R.string.manage_page_product_menu_delete
    }

    AnseButtonNoStyle(onClick = { if (it) onClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = iconRes),
                contentDescription = "menuIcon",
                modifier = Modifier.size(30.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                stringResource(id = textRes),
                modifier = Modifier.widthIn(min = 100.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun ProductMenu(
    modifier: Modifier = Modifier,
    onClickMenu: (EProductMenu) -> Unit,
) {
    Surface(
        modifier = Modifier.then(modifier),
        color = Color.White,
        shape = RoundedCornerShape(5.dp),
    ) {
        Column(
            Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EProductMenu.values().forEach {
                it.MenuItem(
                    onClick = {
                        onClickMenu(it)
                    }
                )
            }
        }
    }
}