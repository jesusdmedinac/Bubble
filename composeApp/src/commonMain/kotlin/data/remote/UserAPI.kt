package data.remote

import data.remote.model.DataUser
import data.utils.FirebaseUtils
import kotlinx.coroutines.flow.firstOrNull

interface UserAPI {
    suspend fun initUser(dataUser: DataUser): Result<Unit>
    suspend fun getUser(): Result<DataUser>
}

class UserAPIImpl(
    private val firebaseUtils: FirebaseUtils,
) : UserAPI {
    override suspend fun initUser(dataUser: DataUser): Result<Unit> = runCatching {
        val child = firebaseUtils.getCurrentUserChild()
        if (child?.valueEvents?.firstOrNull()?.exists == false) {
            child.setValue(dataUser)
        }
    }

    override suspend fun getUser(): Result<DataUser> = runCatching {
        firebaseUtils.getCurrentUserChild()
            ?.valueEvents
            ?.firstOrNull()
            ?.value()
            ?: throw Exception("User not found")
    }
}