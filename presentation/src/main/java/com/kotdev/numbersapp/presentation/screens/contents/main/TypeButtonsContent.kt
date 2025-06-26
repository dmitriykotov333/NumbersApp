package com.kotdev.numbersapp.presentation.screens.contents.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.presentation.viewmodels.main.MainEvent
import kotlin.enums.EnumEntries
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.key
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotdev.numbersapp.core_ui.enums.color
import com.kotdev.numbersapp.core_ui.modifiers.bounceClick
import com.kotdev.numbersapp.core_ui.modifiers.noRippleClickable
import com.kotdev.numbersapp.core_ui.theme.GOTHIC

@Composable
internal fun TypeButtonsContent(
    enums: EnumEntries<TypeRequest>,
    eventHandler: (MainEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        enums.forEachIndexed { index, item ->
            key(item.name) {
                val backgroundColor = item.color()
                Text(
                    modifier = Modifier
                        .bounceClick(from = .96f)
                        .fillMaxWidth()
                        .weight(1f)
                        .background(item.color(), RoundedCornerShape(8.dp))
                        .drawBehind {
                            val corner = CornerRadius(10.dp.toPx())
                            val strokeWidth = 2.dp.toPx()
                            drawRoundRect(
                                color = backgroundColor,
                                size = size,
                                topLeft = Offset.Zero,
                                cornerRadius = corner
                            )
                            drawRoundRect(
                                color = Color(0xFF0E1A5D),
                                size = size,
                                topLeft = Offset.Zero,
                                cornerRadius = corner,
                                style = Stroke(width = strokeWidth)
                            )
                        }
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                        .noRippleClickable {
                            eventHandler(MainEvent.SelectedType(item.name))
                        },
                    text = item.name,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = GOTHIC,
                        fontWeight = FontWeight.Bold
                    )
                )
                if (index != TypeRequest.entries.size - 1) {
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }
}