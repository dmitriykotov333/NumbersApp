package com.kotdev.numbersapp.core_ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GridContent(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val rows = 30
        val columns = 30
        val cellWidth = width / columns
        val cellHeight = height / rows
        for (i in 0..rows) {
            drawLine(
                start = Offset(0f, i * cellHeight),
                end = Offset(width, i * cellHeight),
                color = Color(0xFF072C56),
                strokeWidth = 1.dp.toPx()
            )
        }
        for (i in 0..columns) {
            drawLine(
                start = Offset(i * cellWidth, 0f),
                end = Offset(i * cellWidth, height),
                color = Color(0xFF072C56),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}