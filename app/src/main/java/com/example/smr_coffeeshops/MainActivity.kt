package com.example.smr_coffeeshops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smr_coffeeshops.ui.theme.SMR_CoffeeShopsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMR_CoffeeShopsTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars) //Agrega espacio para la barra de estado y de navegacion, solo para material2
                ) { innerPadding ->

                    //Navegacion con RUTAS
                    val navController = rememberNavController()

                    NavHost(modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Routes.Pantalla1.route
                    ) {
                        composable(Routes.Pantalla1.route) {
                            Pantalla1(navController)
                        }
                        composable(
                            route = Routes.Pantalla2.route,
                            arguments = listOf(navArgument("nombreCafe") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val nombreCafe = backStackEntry.arguments?.getString("nombreCafe") ?: ""
                            Comentarios(nombreCafe = nombreCafe)
                        }
                    }

                }
            }
        }
    }
}

