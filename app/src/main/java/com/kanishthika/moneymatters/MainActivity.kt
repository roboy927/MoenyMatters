package com.kanishthika.moneymatters

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.kanishthika.moneymatters.config.models.SplashModel
import com.kanishthika.moneymatters.config.navigation.AppNavHost
import com.kanishthika.moneymatters.config.theme.MoneyMattersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: SplashModel by viewModels ()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { viewModel.isLoading.value }

        setContent {
            MoneyMattersTheme {
                enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(0xFF000000.toInt()))
                AppNavHost()
            }
        }
    }
}

