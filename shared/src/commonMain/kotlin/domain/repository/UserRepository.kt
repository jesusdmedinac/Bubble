package domain.repository

import domain.model.PointsSubject
import domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun initUser(): Result<Unit>
    suspend fun getUser(): Result<User>
    suspend fun getUserAsFlow(): Result<Flow<User>>
    suspend fun updateStreak(streak: MutableList<String>)
    suspend fun updatePoints(pointsSubject: PointsSubject): Result<Unit>
}