package com.kotdev.numbersapp.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import com.kotdev.numbersapp.core_ui.R
import com.mutualmobile.composesensors.SensorDelay
import com.mutualmobile.composesensors.rememberMagneticFieldSensorState

@Composable
fun AnimateBackground(
    modifier: Modifier = Modifier.fillMaxSize(),
    scaleX: Float = 1.3f,
    scaleY: Float = 1.1f
) {
    val accelerometerState = rememberMagneticFieldSensorState(sensorDelay = SensorDelay.Game)
    Image(
        modifier = modifier
            .graphicsLayer {
                this.scaleX = scaleX
                this.scaleY = scaleY
                cameraDistance = 1f
            }
            .offset {
                IntOffset(
                    (accelerometerState.xStrength * .7).toInt(),
                    (-accelerometerState.yStrength * 1.4).toInt()
                )
            },
        painter = painterResource(id = R.drawable.background),
        alignment = BiasAlignment(
            (accelerometerState.xStrength * .7).toFloat(),
            -(accelerometerState.xStrength * .7).toFloat()
        ),
        contentDescription = "heyGreek",
        contentScale = ContentScale.FillBounds
    )
}