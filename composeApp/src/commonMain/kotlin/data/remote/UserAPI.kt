package data.remote

import data.utils.FirebaseUtils
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

interface UserAPI {
    suspend fun initUser(user: User): Result<Unit>
    suspend fun getUser(): Result<User>
}

class UserAPIImpl(
    private val firebaseUtils: FirebaseUtils,
) : UserAPI {
    override suspend fun initUser(user: User): Result<Unit> = runCatching {
        val child = firebaseUtils.getCurrentUserChild()
        if (child?.valueEvents?.firstOrNull()?.exists == false) {
            child.setValue(user)
        }
    }

    override suspend fun getUser(): Result<User> = runCatching {
        firebaseUtils.getCurrentUserChild()
            ?.valueEvents
            ?.firstOrNull()
            ?.value()
            ?: throw Exception("User not found")
    }
}