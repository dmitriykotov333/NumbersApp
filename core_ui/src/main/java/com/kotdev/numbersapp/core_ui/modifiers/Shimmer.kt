package com.kotdev.numbersapp.core_ui.modifiers

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

enum class ShimmerColor {
    WHITE,
    BLUE
}

fun Modifier.shimmer(
    value: Boolean,
    color: ShimmerColor,
    blendMode: BlendMode = BlendMode.DstIn
): Modifier = composed {
    if (value) {
        val shimmer = rememberShimmer(
            shimmerBounds = ShimmerBounds.View,
            theme = ShimmerTheme(
                shaderColors = when (color) {
                    ShimmerColor.WHITE -> {
                        com.valentinilk.shimmer.LocalShimmerTheme.current.shaderColors
                    }

                    ShimmerColor.BLUE -> {
                        listOf(
                            Color(0xFF74C1FF).copy(alpha = 0.0f),
                            Color(0xFF74C1FF).copy(alpha = 0.3f),
                            Color(0xFF74C1FF).copy(alpha = 0.0f),
                        )
                    }
                },

                shaderColorStops = listOf(0f, 0.5f, 1f),
                blendMode = blendMode,
                rotation = com.valentinilk.shimmer.LocalShimmerTheme.current.rotation,
                shimmerWidth = com.valentinilk.shimmer.LocalShimmerTheme.current.shimmerWidth,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1200,
                        easing = LinearEasing,
                        delayMillis = 50
                    )
                )
            )
        )
        shimmer(shimmer)
    } else {
        this
    }
}
