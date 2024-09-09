package dev.jx.resvegetariano.data.network.client

import dev.jx.resvegetariano.data.network.model.ClienteModel
import dev.jx.resvegetariano.data.network.response.Login
import dev.jx.resvegetariano.data.network.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthClient @Inject constructor(private val authService: AuthService) {

    suspend fun signInWithGoogle(idToken: String): Login<ClienteModel> {
        return withContext(Dispatchers.IO) {
            val response = authService.signInWithGoogle(idToken)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw Exception("Unathorized")
            }
        }
    }
}