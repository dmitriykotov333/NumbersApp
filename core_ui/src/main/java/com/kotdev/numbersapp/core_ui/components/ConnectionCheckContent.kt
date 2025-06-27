package com.kotdev.numbersapp.core_ui.components

import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import kotlinx.coroutines.delay

enum class ConnectivityStatus {
    Available, Unavailable, Losing, Lost, Retry
}


@Composable
fun ConnectionCheckContent(status: ConnectivityStatus) {

    var connectionLost by remember {
        mutableStateOf(false)
    }
    var connectionSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = status) {
        when (status) {
            ConnectivityStatus.Retry,
            ConnectivityStatus.Available -> {
                if (connectionLost) {
                    connectionLost = false
                    connectionSuccess = true
                    delay(4000)
                }
                connectionLost = false
                connectionSuccess = false
            }
            else -> {
                connectionLost = true
                connectionSuccess = false
            }
        }
    }
   AnimatedVisibility(
        visible = connectionSuccess || connectionLost
   ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val backgroundColor = when (status) {
                        ConnectivityStatus.Unavailable -> Color(0xD9AF2B23)
                        else -> Color(0xD92B901A)
                    }
                    drawRect(color = backgroundColor)
                }
                .statusBarsPadding()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(
                    id = if (status == ConnectivityStatus.Unavailable) R.drawable.connection_lost else R.drawable.connection_restore
                ),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = if (status == ConnectivityStatus.Unavailable) R.string.connection_lost else R.string.connection_restore),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FORMULAR,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp,
                )
            )
        }
    }
}