package com.kotdev.numbersapp.core_ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.collections.immutable.ImmutableMap

@Composable
fun AnnotatedText(
    modifier: Modifier = Modifier,
    fullText: String,
    hyperLinks: ImmutableMap<String, SpanStyle>,
    style: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    action: (String) -> Unit,
) {
    val annotatedString = remember(fullText) {
        buildAnnotatedString {
            append(fullText)

            for ((key, value) in hyperLinks) {
                val startIndex = fullText.indexOf(key)
                if (startIndex >= 0) {
                    val endIndex = startIndex + key.length
                    addStyle(value, startIndex, endIndex)
                    addStringAnnotation("URL", key, startIndex, endIndex)
                }
            }
        }
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = style,
        maxLines = maxLines,
        overflow = overflow,
        onClick = { offset ->
            annotatedString.getStringAnnotations("URL", offset, offset)
                .firstOrNull()?.let {
                    action(it.item)
                }
        }
    )
}