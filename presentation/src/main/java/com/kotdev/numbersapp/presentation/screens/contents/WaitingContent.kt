package com.kotdev.numbersapp.presentation.screens.contents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.kotdev.numbersapp.core_ui.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun WaitingContent() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.waiting))
    LottieAnimation(composition, iterations = LottieConstants.IterateForever)
}