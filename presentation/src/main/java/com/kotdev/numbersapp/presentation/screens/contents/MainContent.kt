package com.kotdev.numbersapp.presentation.screens.contents

import android.content.Context
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.components.AnnotatedText
import com.kotdev.numbersapp.core_ui.components.NumCounter
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.core_ui.enums.UiType
import com.kotdev.numbersapp.core_ui.enums.color
import com.kotdev.numbersapp.core_ui.modifiers.ShimmerColor
import com.kotdev.numbersapp.core_ui.modifiers.bounceClick
import com.kotdev.numbersapp.core_ui.modifiers.noRippleClickable
import com.kotdev.numbersapp.core_ui.modifiers.shimmer
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import com.kotdev.numbersapp.core_ui.theme.GOTHIC
import com.kotdev.numbersapp.core_ui.theme.Theme
import com.kotdev.numbersapp.presentation.screens.contents.main.EditTextContent
import com.kotdev.numbersapp.presentation.screens.contents.main.TypeButtonsContent
import com.kotdev.numbersapp.presentation.viewmodels.main.EditTextError
import com.kotdev.numbersapp.presentation.viewmodels.main.MainEvent
import com.kotdev.numbersapp.presentation.viewmodels.main.MainViewState
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.delay
import kotlin.enums.EnumEntries

@Composable
internal fun MainContent(
    state: MainViewState,
    eventHandler: (MainEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
                .background(Theme.colors.background, RoundedCornerShape(12.dp))
        ) {

            Spacer(Modifier.height(12.dp))
            TypeButtonsContent(enums = TypeRequest.entries, eventHandler = eventHandler)
            Spacer(Modifier.height(6.dp))
            AnnotatedText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xD909305D))
                    .padding(8.dp),
                fullText = "${stringResource(id = R.string.example)} ${state.description}",
                hyperLinks = persistentMapOf(
                    stringResource(id = R.string.example) to SpanStyle(
                        color = Color(0xFFC8E8EA),
                        fontWeight = FontWeight.Bold
                    )
                ),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color(0xFFC8E8EA),
                    fontSize = 13.sp,
                    fontFamily = GOTHIC,
                    fontWeight = FontWeight.Normal
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                action = {

                }
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EditTextContent(
                    error = state.error,
                    number = state.number,
                    numberSecond = state.numberSecond,
                    type = state.selectedRequest,
                    eventHandler = eventHandler
                )
                BouncingIconButton(
                    isRotating = state.isSendingRandom,
                    eventHandler = eventHandler
                )
                Spacer(Modifier.width(12.dp))
            }
            Spacer(Modifier.height(6.dp))
            NumCounter(count = state.count)
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { eventHandler(MainEvent.ClickGetFact) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xD9061E3A),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(horizontal = 21.dp)
                .fillMaxWidth()
                .shimmer(state.isSending, ShimmerColor.WHITE)
                .bounceClick(
                    from = 0.98f
                ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = stringResource(R.string.get_fact),
                modifier = Modifier.padding(vertical = 7.dp),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FORMULAR,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}
