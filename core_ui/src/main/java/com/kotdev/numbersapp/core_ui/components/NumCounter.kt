package com.kotdev.numbersapp.core_ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotdev.numbersapp.core_ui.extensions.splitToDigits
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NumCounter(
    modifier: Modifier = Modifier,
    count: Int,
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp)
            .background(Color(0xD9061E3A), RoundedCornerShape(12.dp))
            .shadow(10.dp, RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val animatedDigits = remember { mutableStateListOf<Int>() }
        LaunchedEffect(count) {
            val newDigits = count.splitToDigits()
            if (animatedDigits.isEmpty()) {
                repeat(4) { animatedDigits.add(0) }
            }
            if (animatedDigits.size < newDigits.size) {
                val diff = newDigits.size - animatedDigits.size
                repeat(diff) { animatedDigits.add(0, 0) }
            }

            for (i in newDigits.indices) {
                val from = animatedDigits[i]
                val to = newDigits[i]

                if (from != to) {
                    if (to > from) {
                        for (v in from..to step 1) {
                            animatedDigits[i] = v
                            delay(200)
                        }
                    } else {
                        for (v in from downTo to) {
                            animatedDigits[i] = v
                            delay(200)
                        }
                    }
                }
            }
        }

        animatedDigits.forEachIndexed { index, digit ->
            AnimatedContent(
                targetState = digit,
                transitionSpec = {
                    if (digit < count.splitToDigits()[index]) {
                        slideInVertically { it } + fadeIn() with
                                slideOutVertically { -it } + fadeOut()
                    } else {
                        slideInVertically { -it } + fadeIn() with
                                slideOutVertically { it } + fadeOut()
                    }.using(SizeTransform(clip = false))
                }
            ) { char ->
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = char.toString(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FORMULAR,
                        fontWeight = FontWeight.Medium
                    ),
                    softWrap = false
                )
            }
            if (index != animatedDigits.size) {
                Spacer(Modifier
                    .width(1.dp)
                    .height(20.dp)
                    .background(Color(0x9E133693)))
            }
        }
    }

}