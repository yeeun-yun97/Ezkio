package com.anse.easyQrPay.ui.pages.idlePage

import android.view.Surface
import android.widget.Button
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anse.easyQrPay.R
import com.anse.easyQrPay.ui.theme.EasyQrPayTheme
import com.anse.uikit.components.button.AnseButton
import com.anse.uikit.components.button.AnseButtonColors
import com.anse.uikit.components.button.AnseButtonStyle
import kotlinx.coroutines.delay

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun PreviewIdlePage() {
    EasyQrPayTheme {
        IdlePage(
            navigateToShopPage = {}
        )
    }
}

val idleImages = listOf(
    R.drawable.img_idle1,
    R.drawable.img_idle2,
    R.drawable.img_idle3,
    R.drawable.img_idle4,
    R.drawable.img_idle5,
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun IdlePage(
    navigateToShopPage: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        val imageIndex = remember { mutableStateOf(0) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(5000L)
                imageIndex.value = imageIndex.value + 1
            }
        }

        val transition = rememberInfiniteTransition(label = "idle_image_transition")
        val alignment by transition.animateFloat(
            initialValue = 0.25f,
            targetValue = -0.25f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    5000,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "idle_image_transition_alignment"
        )
        AnimatedContent(
            targetState = imageIndex.value,
            modifier = Modifier.fillMaxSize(),
            transitionSpec = {
                if (targetState > initialState) {
                    fadeIn() with fadeOut()
                } else {
                    fadeIn() with fadeOut()
                }.using(SizeTransform(clip = false))
            },
            label = ""
        ) { imageIndex ->
            Image(
                painter = painterResource(idleImages[imageIndex % idleImages.size]),
                contentScale = ContentScale.Crop,
                alignment = BiasAlignment(verticalBias = alignment, horizontalBias = 0f),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "idle image"
            )
        }
        Surface(
            color = Color.Black.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxSize()
        ) {}
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart)
                .padding(horizontal = 30.dp, vertical = 20.dp)
        ) {
            Text(
                "일러스타페스 4회 B-30 양일",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Medium,
            )
            Spacer(Modifier.height(15.dp))
            Text(
                "평범한 개발자이던 내가 AN-94를 그리기 시작한 건에 대하여",
                modifier = Modifier.widthIn(max = 800.dp),
                color = Color.White,
                fontSize = 75.sp,
                lineHeight = 75.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.weight(1f))
            AnseButton(
                onClick = { if (it) navigateToShopPage() },
                buttonStyle = AnseButtonStyle.newStyle(
                    colors = AnseButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color(0xFFABDE82),
                    ),
                    shape = RoundedCornerShape(40.dp),
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 100.dp)
                ),
                modifier = Modifier.align(CenterHorizontally)
            ) {
                Text(
                    "시작하기",
                    modifier = Modifier.align(Center),
                    color = it,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
            Spacer(Modifier.height(50.dp))
        }
    }
}
