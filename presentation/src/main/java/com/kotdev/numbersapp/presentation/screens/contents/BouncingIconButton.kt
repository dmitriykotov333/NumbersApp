package com.kotdev.numbersapp.presentation.screens.contents

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kotdev.numbersapp.core_ui.modifiers.bounceClick
import kotlinx.coroutines.delay
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.presentation.viewmodels.main.MainEvent

@Composable
internal fun BouncingIconButton(
    isRotating: Boolean,
    eventHandler: (MainEvent) -> Unit
) {
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(isRotating) {
        while (isRotating) {
            rotation.animateTo(
                targetValue = rotation.value + 360f,
                animationSpec = tween(durationMillis = 500, easing = LinearEasing)
            )
            delay(200)
        }
    }

    IconButton(
        modifier = Modifier
            .size(56.dp)
            .background(Color(0xD9061E3A), CircleShape)
            .bounceClick(),
        onClick = {
            eventHandler.invoke(MainEvent.ClickGetFactRandom)
        }
    ) {
        Icon(
            modifier = Modifier
                .background(Color(0xD9061E3A))
                .graphicsLayer {
                rotationZ = rotation.value
            },
            tint = Color.White,
            imageVector = ImageVector.vectorResource(R.drawable.random),
            contentDescription = stringResource(id = R.string.random)
        )
    }
}