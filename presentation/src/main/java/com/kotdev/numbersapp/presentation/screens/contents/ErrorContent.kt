package com.kotdev.numbersapp.presentation.screens.contents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import com.kotdev.numbersapp.presentation.viewmodels.main.EditTextError

@Composable
internal fun ErrorContent(
    error: EditTextError
) {
    Spacer(Modifier.height(6.dp))
    if (error.isError) {
        Text(
            text = error.text,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
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