package com.cocido.taily_app.data.model

data class LoginResponse(
    val token: String,  // Token que devuelve la API
    val userId: String  // ID del usuario autenticado
)
