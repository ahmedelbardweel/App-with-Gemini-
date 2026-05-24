package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MainActivityView
import com.example.ui.ShopViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: ShopViewModel = viewModel()
                val currentScreen by viewModel.currentScreen.collectAsState()
                val canGoBack by viewModel.canGoBack.collectAsState()

                // Register native back press handler to pop our screens instead of closing
                BackHandler(enabled = canGoBack) {
                    viewModel.navigateBack()
                }

                // Force Right-to-Left (RTL) layout direction to match Arabic visual identity
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    MainActivityView(
                        viewModel = viewModel,
                        currentScreen = currentScreen,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
