package com.anse.easyQrPay.ui.component.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun rememberUiVisibility(
    initValue: Boolean = false,
    onShow: () -> Boolean = { true },
    onHide: () -> Boolean = { true },
): Pair<State<Boolean>, Function1<Boolean, Unit>> {
    val dialogVisibility = rememberSaveable { mutableStateOf(initValue) }
    return Pair(dialogVisibility) { visibility ->
        if (visibility) {
            onShow()
        } else {
            onHide()
        }
        dialogVisibility.value = visibility
    }
}

@Composable
fun <T> rememberUiVisibilityByNull(
    onShow: (T) -> Boolean = { true },
    onHide: () -> Boolean = { true },
): Pair<State<T?>, Function1<T?, Unit>> {
    val dialogVisibility: MutableState<T?> = rememberSaveable { mutableStateOf(null) }
    return Pair(dialogVisibility) { visibility ->
        if (visibility != null) {
            if (onShow(visibility)) {
                dialogVisibility.value = visibility
            }
        } else {
            if (onHide()) {
                dialogVisibility.value = visibility
            }
        }
    }
}