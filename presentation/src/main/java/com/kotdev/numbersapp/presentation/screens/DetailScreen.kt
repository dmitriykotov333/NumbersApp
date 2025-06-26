package com.kotdev.numbersapp.presentation.screens


import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import com.kotdev.numbersapp.core.extensions.joinStrings
import com.kotdev.numbersapp.core.extensions.openUrl
import com.kotdev.numbersapp.core.extensions.share
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.components.AnnotatedText
import com.kotdev.numbersapp.core_ui.enums.color
import com.kotdev.numbersapp.core_ui.modifiers.ShimmerColor
import com.kotdev.numbersapp.core_ui.modifiers.bounceClick
import com.kotdev.numbersapp.core_ui.modifiers.noRippleClickable
import com.kotdev.numbersapp.core_ui.modifiers.shimmer
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import com.kotdev.numbersapp.core_ui.theme.GOTHIC
import com.kotdev.numbersapp.presentation.viewmodels.detail.DetailEvent
import com.kotdev.numbersapp.presentation.viewmodels.detail.DetailViewModel
import kotlinx.collections.immutable.persistentMapOf
import kotlin.math.ceil

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
) {
    val states by viewModel.states().collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                viewModel.obtainEvent(DetailEvent.ClickBack)
            }) {
                Image(
                    modifier = Modifier.rotate(180f),
                    imageVector = ImageVector.vectorResource(R.drawable.right_arrow),
                    contentDescription = "Back"
                )
            }
            Spacer(Modifier.width(16.dp))
            Text(
                modifier = Modifier,
                text = "Back",
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FORMULAR,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(modifier = Modifier.padding(end = 16.dp), onClick = {
                viewModel.obtainEvent(DetailEvent.ClickShare)
            }) {
                Image(
                    modifier = Modifier,
                    imageVector = ImageVector.vectorResource(R.drawable.share),
                    contentDescription = "Back"
                )
            }
        }
        Spacer(Modifier.height(21.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .background(states.type.color(), CircleShape)
                    .padding(6.dp),
                text = stringResource(id = states.type.stringId),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontSize = 19.sp,
                    fontFamily = FORMULAR,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(Modifier.width(12.dp))
            Text(
                modifier = Modifier
                    .background(Color(0xFF2B6AD3), CircleShape)
                    .padding(6.dp),
                text = states.number,
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontSize = 19.sp,
                    fontFamily = FORMULAR,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Spacer(Modifier.height(21.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = states.description,
            style = TextStyle(
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 21.sp,
                fontFamily = GOTHIC,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(21.dp))
        AnnotatedText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            fullText = "http://numbersapi.com/${states.number}/${states.type.name.lowercase()}",
            hyperLinks = persistentMapOf(
                "http://numbersapi.com" to SpanStyle(
                    color = Color(0xFF00B0FF),
                    fontWeight = FontWeight.Bold
                ),
                "/${states.number}/${states.type.name.lowercase()}" to SpanStyle(
                    color = Color(0xFF00B0FF),
                    fontWeight = FontWeight.Bold
                ),
            ),
            style = TextStyle(
                textAlign = TextAlign.Start,
                color = Color(0xFF00B0FF),
                fontSize = 13.sp,
                fontFamily = FORMULAR,
                fontWeight = FontWeight.Medium
            ),
            action = {
                viewModel.obtainEvent(DetailEvent.OpenUrl)
            }
        )
    }
}
