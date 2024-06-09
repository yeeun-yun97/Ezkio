package com.anse.uikit.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface AnseButtonStyle {
    val shape: Shape
    val border: BorderStroke?
    val colors: AnseButtonColors
    val contentPadding: PaddingValues

    companion object {
        fun newStyle(
            shape: Shape = RectangleShape,
            border: BorderStroke? = null,
            colors: AnseButtonColors,
            contentPadding: PaddingValues,
        ): AnseButtonStyle {
            return object : AnseButtonStyle {
                override val shape: Shape = shape
                override val border: BorderStroke? = border
                override val colors: AnseButtonColors = colors
                override val contentPadding: PaddingValues = contentPadding
            }
        }
    }
}


data class AnseButtonColors(
    private val contentColor: Color,
    private val containerColor: Color = Color.Unspecified,
    private val disabledContainerColor: Color = containerColor.copy(alpha = 0.4f),
    private val disabledContentColor: Color = contentColor.copy(alpha = 0.4f),
) {
    @Composable
    fun containerColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(newValue = if (enabled) containerColor else disabledContainerColor)
    }

    @Composable
    fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(newValue = if (enabled) contentColor else disabledContentColor)
    }
}

/**
 * @param enabled null if no click action, true if enabled, false if disabled
 */
@Composable
fun AnseButton(
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean? = true,
    buttonStyle: AnseButtonStyle,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shadowElevation: Dp = 0.dp,
    content: @Composable BoxScope.(Color) -> Unit,
) {
    val containerColor = buttonStyle.colors.containerColor(enabled = enabled == true)
    val contentColor = buttonStyle.colors.contentColor(enabled = enabled == true)

    Surface(
        onClick = { onClick(enabled == true) },
        modifier = Modifier
            .then(modifier)
            .semantics { role = Role.Button },
        enabled = enabled != null,
        shape = buttonStyle.shape,
        border = buttonStyle.border,
        color = containerColor.value,
        interactionSource = interactionSource,
        shadowElevation = shadowElevation
    ) {
        Box(
            modifier = Modifier
                .padding(buttonStyle.contentPadding)
        ) {
            content(contentColor.value)
        }
    }
}

@Composable
fun AnseButtonNoStyle(
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .clickable { onClick(enabled) }
            .semantics { role = Role.Button },
    ) {
        content()
    }
}