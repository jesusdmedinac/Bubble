package data.remote.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignInAnonymouslyPayload(
    val returnSecureToken: Boolean = true
)