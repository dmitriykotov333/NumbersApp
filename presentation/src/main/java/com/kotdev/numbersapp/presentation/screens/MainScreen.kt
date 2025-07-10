package com.kotdev.numbersapp.presentation.screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.input.pointer.pointerInput
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
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.components.AnimateBackground
import com.kotdev.numbersapp.core_ui.components.AnnotatedText
import com.kotdev.numbersapp.core_ui.components.GridContent
import com.kotdev.numbersapp.presentation.screens.contents.CollapsingLayout
import com.kotdev.numbersapp.presentation.screens.contents.FilterDialog
import com.kotdev.numbersapp.presentation.screens.contents.HistoryContent
import com.kotdev.numbersapp.presentation.screens.contents.MainContent
import com.kotdev.numbersapp.presentation.viewmodels.main.MainEvent
import com.kotdev.numbersapp.presentation.viewmodels.main.MainViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.NonCancellable.key
import okhttp3.internal.toImmutableList
import kotlin.math.ceil

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
) {
    val states by viewModel.states().collectAsState()
    val histories = viewModel.histories.collectAsLazyPagingItems()
    val selectedIds = viewModel.selectedIds.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        CollapsingLayout(
            expandedContent = { modifier ->
                Box(
                    modifier = modifier.height(500.dp), contentAlignment = Alignment.Center
                ) {
                    AnimateBackground()
                    GridContent(
                        modifier = Modifier.fillMaxSize()
                    )
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(.4f),
                        model = R.drawable.grainy,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                    )
                    MainContent(
                        state = states,
                        eventHandler = viewModel::obtainEvent
                    )
                }

            },
            collapsedContent = { modifier ->

            }
        ) { modifier, close ->
            HistoryContent(
                modifier = modifier,
                histories = histories,
                selectedIds = selectedIds,
                close = close,
                paddingValues = paddingValues,
                eventHandler = viewModel::obtainEvent
            )
        }

        var showFilter by rememberSaveable {
            mutableStateOf(false)
        }
        if (showFilter) {
            FilterDialog {
                showFilter = !showFilter
            }
        }
        IconButton(
            modifier = Modifier
                .padding(24.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd)
                .background(Color(0xFF0E1A5D), CircleShape)
                .shadow(8.dp, CircleShape), onClick = {
                showFilter = !showFilter
            }) {
            Image(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(R.drawable.filter),
                contentDescription = ""
            )
        }
    }
}



