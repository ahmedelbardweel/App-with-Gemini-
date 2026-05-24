package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.screens.*

@Composable
fun MainActivityView(
    viewModel: ShopViewModel,
    currentScreen: Screen,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                (fadeIn() + slideInHorizontally { width -> -width }) togetherWith
                (fadeOut() + slideOutHorizontally { width -> width })
            },
            label = "screen_transition"
        ) { targetScreen ->
            when (targetScreen) {
                Screen.HOME -> ShopHomeScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                Screen.DETAIL -> ProductDetailScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                Screen.CART -> CartScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                Screen.CHECKOUT -> CheckoutScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                Screen.TRACKING -> TrackingScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
