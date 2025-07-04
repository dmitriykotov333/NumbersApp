package com.kotdev.numbersapp.presentation.screens.contents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun CollapsingLayout(
    modifier: Modifier = Modifier,
    expandedContent: @Composable (Modifier) -> Unit,
    collapsedContent: @Composable (Modifier) -> Unit,
    content: @Composable (Modifier, Boolean) -> Unit
) {
    val localDensity = LocalDensity.current
    var currentHeight by rememberSaveable { mutableFloatStateOf(0f) }
    var maxHeight by rememberSaveable { mutableFloatStateOf(-1f) }
    var minHeight by rememberSaveable { mutableFloatStateOf(-1f) }
    var close by rememberSaveable { mutableStateOf(false) }
    val animationProgress by remember(currentHeight, maxHeight, minHeight) {
        derivedStateOf {
            ((currentHeight - minHeight) / (maxHeight - minHeight)).coerceIn(0f, 1f)
        }
    }

    LaunchedEffect(maxHeight) {
        if (currentHeight == 0f && maxHeight != -1f) {
            currentHeight = maxHeight // Initially expanded
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (currentHeight != minHeight && available.y < 0) {
                    currentHeight = (currentHeight + available.y).coerceAtLeast(minHeight)
                    return available
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (currentHeight != maxHeight && available.y > 0) {
                    currentHeight = (currentHeight + available.y).coerceAtMost(maxHeight)
                    return available
                }
                return Offset.Zero
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .nestedScroll(nestedScrollConnection),
    ) {
        Box(
            modifier = Modifier.then(
                if (currentHeight != 0f) {
                    Modifier
                        .height(with(localDensity) { currentHeight.toDp() })
                        .clipToBounds()
                } else Modifier
            )
        ) {
            expandedContent(
                Modifier
                    .onGloballyPositioned {
                        if (maxHeight == -1f) {
                            maxHeight = it.size.height.toFloat()
                        }
                    }
                    .alpha(animationProgress)
            )

            collapsedContent(
                Modifier
                    .onGloballyPositioned {
                        if (minHeight == -1f) {
                            minHeight = it.size.height.toFloat()
                        }
                    }
                    .alpha(1 - animationProgress)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            content(Modifier.fillMaxSize(), close)

            val showCollapseButton by remember {
                derivedStateOf {
                    currentHeight < 0
                }
            }

            this@Column.AnimatedVisibility(
                visible = showCollapseButton,
                enter = scaleIn(),
                exit = scaleOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {

                IconButton(
                    modifier = Modifier
                        .padding(24.dp)
                        .size(56.dp)
                        .background(Color(0xD9061E3A), CircleShape), onClick = {
                        close = !close
                        currentHeight = 0f
                        maxHeight = -1f
                        minHeight = -1f
                    }) {
                    Icon(
                        modifier = Modifier
                            .size(45.dp),
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}