package domain

import data.remote.AuthAPI
import data.remote.User
import data.remote.UserAPI

interface UserRepository {
    suspend fun initUser(): Result<Unit>
    suspend fun getUser(): Result<User>
}

class UserRepositoryImpl(
    private val authAPI: AuthAPI,
    private val userAPI: UserAPI,
) : UserRepository {
    override suspend fun initUser(): Result<Unit> = authAPI
        .initAuth()
        .fold(
            onSuccess = { userAPI.initUser(it) },
            onFailure = { Result.failure(it) }
        )

    override suspend fun getUser(): Result<User> = userAPI.getUser()
}