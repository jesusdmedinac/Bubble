package data.remote.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignInAnonymouslyResponse(
    val idToken: String = "",
    val email: String = "",
    val refreshToken: String = "",
    val expiresIn: String = "",
    val localId: String = "",
)