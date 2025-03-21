package com.cocido.taily_app.data.api

import com.cocido.taily_app.data.model.LoginRequest
import com.cocido.taily_app.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/auth/google")
    suspend fun loginWithGoogle(@Query("token") idToken: String): Response<LoginResponse>
}
