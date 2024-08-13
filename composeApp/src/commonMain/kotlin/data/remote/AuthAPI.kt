package data.remote

import data.remote.model.DataUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth

interface AuthAPI {
    suspend fun initAuth(): Result<DataUser>
}

class AuthAPIImpl(
    private val auth: FirebaseAuth = Firebase.auth
) : AuthAPI {
    override suspend fun initAuth(): Result<DataUser> = runCatching {
        DataUser(id = auth.signInAnonymously().user?.uid ?: "")
    }
}