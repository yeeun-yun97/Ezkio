package com.anse.easyQrPay.ui.component.textField

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun EzkioTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textVisibility: Boolean = true,
    enabled: Boolean = true,
    textStyle: TextStyle,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    decorationBox: @Composable (@Composable () -> Unit) -> Unit = { content -> content() },
    hintView: @Composable BoxScope.() -> Unit,
) {
    val visualTransformation = if (textVisibility) VisualTransformation.None
    else PasswordVisualTransformation()
    BasicTextField(
        value = value,
        modifier = Modifier
            .then(modifier),
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = (maxLines == 1),
        maxLines = maxLines,
        decorationBox = { innerTextField ->
            decorationBox {
                @OptIn(ExperimentalMaterial3Api::class)
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    placeholder = {
                        Box { hintView() }
                    },
                    contentPadding = PaddingValues(0.dp),
                    enabled = enabled,
                    interactionSource = interactionSource,
                    singleLine = (maxLines == 1),
                    visualTransformation = visualTransformation,
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                    container = {}
                )
            }
        }
    )
}