package data.repository

import data.remote.AuthAPI
import data.remote.UserAPI
import data.remote.model.DataUser
import domain.UserRepository

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