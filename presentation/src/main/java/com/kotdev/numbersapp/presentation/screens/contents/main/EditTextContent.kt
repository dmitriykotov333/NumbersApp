package com.kotdev.numbersapp.presentation.screens.contents.main

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.core_ui.enums.UiType
import com.kotdev.numbersapp.core_ui.modifiers.bounceClick
import com.kotdev.numbersapp.core_ui.modifiers.noRippleClickable
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import com.kotdev.numbersapp.core_ui.theme.GOTHIC
import com.kotdev.numbersapp.presentation.viewmodels.main.EditTextError
import com.kotdev.numbersapp.presentation.viewmodels.main.MainEvent
import kotlinx.coroutines.delay
import java.net.Authenticator

@Composable
internal fun EditTextContent(
    isSendingRandom: Boolean,
    error: EditTextError,
    number: String,
    numberSecond: String,
    type: UiType,
    eventHandler: (MainEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xB30E1A5D)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = stringResource(id = type.stringId), style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = GOTHIC,
                        fontWeight = FontWeight.Bold
                    )
                )

                TextField(
                    value = number,
                    onValueChange = {
                        eventHandler(MainEvent.ChangeNumber(it))
                    },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = GOTHIC,
                        fontWeight = FontWeight.Normal
                    ),
                    placeholder = {
                        Text(
                            text = if (type == TypeRequest.DATE) stringResource(R.string.placeholder_m) else stringResource(R.string.placeholder),
                            style = TextStyle(
                                textAlign = TextAlign.Start,
                                color = Color.LightGray,
                                fontSize = 15.sp,
                                fontFamily = GOTHIC,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = Color(0xD9061E3A),
                        unfocusedContainerColor = Color(0xD9061E3A),
                        disabledContainerColor = Color(0xD9061E3A),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
                if (type == TypeRequest.DATE) {
                    TextField(
                        value = numberSecond,
                        onValueChange = {
                            eventHandler(MainEvent.ChangeNumberSecond(it))
                        },
                        textStyle = TextStyle(
                            textAlign = TextAlign.Start,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontFamily = GOTHIC,
                            fontWeight = FontWeight.Normal
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.placeholder_d),
                                style = TextStyle(
                                    textAlign = TextAlign.Start,
                                    color = Color.LightGray,
                                    fontSize = 15.sp,
                                    fontFamily = GOTHIC,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color(0xD9061E3A),
                            unfocusedContainerColor = Color(0xD9061E3A),
                            disabledContainerColor = Color(0xD9061E3A),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            if (error.isError) {
                Text(
                    text = error.text,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        color = Color.Red,
                        fontSize = 11.sp,
                        fontFamily = FORMULAR,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        val rotation = remember { Animatable(0f) }

        LaunchedEffect(isSendingRandom) {
            while (isSendingRandom) {
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
                .bounceClick(), onClick = {
                eventHandler(MainEvent.ClickGetFactRandom)
            }) {
            Image(
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = rotation.value
                    },
                contentScale = ContentScale.Inside,
                imageVector = ImageVector.vectorResource(R.drawable.random),
                contentDescription = stringResource(id = R.string.random)
            )
        }
        Spacer(Modifier.width(12.dp))
    }
}