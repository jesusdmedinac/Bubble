package data.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth

interface AuthAPI {
    suspend fun initAuth(): Result<User>
}

class AuthAPIImpl(
    private val auth: FirebaseAuth = Firebase.auth
) : AuthAPI {
    override suspend fun initAuth(): Result<User> = runCatching {
        User(id = auth.signInAnonymously().user?.uid ?: "")
    }
}