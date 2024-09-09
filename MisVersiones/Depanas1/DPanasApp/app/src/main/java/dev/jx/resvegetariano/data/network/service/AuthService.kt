package dev.jx.resvegetariano.data.network.service

import dev.jx.resvegetariano.data.network.model.ClienteModel
import dev.jx.resvegetariano.data.network.response.Login
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @POST("auth/sign-in/google")
    @FormUrlEncoded
    suspend fun signInWithGoogle(@Field("idToken") idToken: String): Response<Login<ClienteModel>>
}