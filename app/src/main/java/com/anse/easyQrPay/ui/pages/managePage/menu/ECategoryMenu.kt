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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.R
import com.anse.uikit.components.button.AnseButtonNoStyle

enum class ECategoryMenu {
    EDIT_NAME,
    DELETE,
}

@Composable
private fun ECategoryMenu.MenuItem(
    onClick: () -> Unit,
) {
    val iconRes = when (this) {
        ECategoryMenu.EDIT_NAME -> R.drawable.manage_page_edit_icon
        ECategoryMenu.DELETE -> R.drawable.manage_page_delete_icon
    }

    val textRes = when (this) {
        ECategoryMenu.EDIT_NAME -> R.string.manage_page_category_menu_edit_name
        ECategoryMenu.DELETE -> R.string.manage_page_category_menu_delete
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
fun CategoryDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onClickMenu: (ECategoryMenu) -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ECategoryMenu.values().forEach {
                it.MenuItem(
                    onClick = {
                        onClickMenu(it)
                    }
                )
            }
        }
    }
}