package domain

import domain.model.User

interface UserRepository {
    suspend fun initUser(): Result<Unit>
    suspend fun getUser(): Result<User>
}