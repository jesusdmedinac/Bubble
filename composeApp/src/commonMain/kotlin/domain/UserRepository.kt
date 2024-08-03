package domain

import data.remote.AuthAPI
import data.remote.UserAPI

interface UserRepository {
    suspend fun initUser(): Result<Unit>
}

class UserRepositoryImpl(
    private val authAPI: AuthAPI,
    private val userAPI: UserAPI,
) : UserRepository {
    override suspend fun initUser(): Result<Unit> = authAPI
        .initAuth()
        .fold(
            onSuccess = { userAPI.initUser() },
            onFailure = { Result.failure(it) }
        )
}