package data.repository

import data.mapper.toData
import data.mapper.toDomain
import data.remote.AuthAPI
import data.remote.UserAPI
import domain.model.PointsSubject
import domain.repository.UserRepository
import domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override suspend fun getUser(): Result<User> = userAPI
        .getUser()
        .map { it.toDomain() }

    override suspend fun getUserAsFlow(): Result<Flow<User>> = userAPI
        .getUserAsFlow()
        .map { flow -> flow.map { it.toDomain() } }

    override suspend fun updateStreak(streak: MutableList<String>) = userAPI
        .updateStreak(streak)

    override suspend fun updatePoints(pointsSubject: PointsSubject): Result<Unit> =
        userAPI
            .getUser()
            .getOrNull()
            ?.pointsSubjects
            ?.let { pointsSubjects ->
                (pointsSubjects.toSet() + pointsSubject.toData())
                    .toList()
                    .let { userAPI.addPoints(it) }
            }
            ?: Result.failure(Exception("User not found"))
}