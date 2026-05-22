package com.example.pc4_luisfelipecanalalvarado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.pc4_luisfelipecanalalvarado.ui.theme.PC4_LuisFelipeCanalAlvaradoTheme
import com.example.pc4_luisfelipecanalalvarado.screens.HomeScreen

class MainActivity : ComponentActivity() {
    private val viewModel: FlightSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC4_LuisFelipeCanalAlvaradoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

