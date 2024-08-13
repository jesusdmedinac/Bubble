package domain

import data.remote.AuthAPI
import data.remote.model.DataUser
import data.remote.UserAPI

interface UserRepository {
    suspend fun initUser(): Result<Unit>
    suspend fun getUser(): Result<DataUser>
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

    override suspend fun getUser(): Result<DataUser> = userAPI.getUser()
}