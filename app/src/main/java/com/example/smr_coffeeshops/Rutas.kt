package com.example.smr_coffeeshops

sealed class Routes(val route:String) {
    object Pantalla1:Routes("pantalla1")
    object Pantalla2:Routes("pantalla2/{nombreCafe}")
}