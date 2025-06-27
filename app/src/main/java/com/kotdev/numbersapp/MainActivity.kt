package com.kotdev.numbersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kotdev.numbersapp.core_ui.components.ConnectionCheckContent
import com.kotdev.numbersapp.core_ui.theme.GOTHIC
import com.kotdev.numbersapp.core_ui.theme.NumbersAppTheme
import com.kotdev.numbersapp.navigation.core.AppNavigator
import com.kotdev.numbersapp.navigation.core.NavController
import com.kotdev.numbersapp.navigation.core.provider.NavEntryPointProvider
import com.kotdev.numbersapp.navigation.destination.MainDestination
import com.kotdev.numbersapp.navigation.extensions.isLight
import com.kotdev.numbersapp.presentation.viewmodels.network.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navEntryPointProvider: Set<@JvmSuppressWildcards NavEntryPointProvider>

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumbersAppTheme {
                val viewModel = hiltViewModel<NetworkViewModel>()
                val isConnected by viewModel.isConnected.collectAsState()

                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                val entryPointItems = remember {
                    navEntryPointProvider
                        .map { it.routeItem }
                        .sortedBy { it.isStart }
                }

                navController.isLight(this, listOf(MainDestination))

                NavController(
                    navigator = navigator,
                    navController = navController,
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        ) { snackbarData: SnackbarData ->
                            Text(
                                text = snackbarData.visuals.message,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .background(
                                        Color.Red.copy(alpha = .85f),
                                        RoundedCornerShape(11.dp)
                                    )
                                    .padding(16.dp),
                                style = TextStyle(
                                    textAlign = TextAlign.Start,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = GOTHIC,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                        }
                    },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = entryPointItems.find { it.isStart }?.route
                                ?: MainDestination,
                        ) {
                            navEntryPointProvider.forEach { subGraph ->
                                subGraph.graph(
                                    this,
                                    snackbarHostState,
                                    innerPadding
                                )
                            }
                        }
                        ConnectionCheckContent(isConnected)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NumbersAppTheme {
        Greeting("Android")
    }
}