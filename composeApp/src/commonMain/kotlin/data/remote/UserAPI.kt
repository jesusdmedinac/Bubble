package data.remote

import data.remote.model.DataPointsSubject
import data.remote.model.DataUser
import data.utils.FirebaseUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull

interface UserAPI {
    suspend fun initUser(dataUser: DataUser): Result<Unit>
    suspend fun getUser(): Result<DataUser>
    suspend fun getUserAsFlow(): Result<Flow<DataUser>>
    suspend fun updateStreak(streak: MutableList<String>)
    suspend fun addPoints(pointsSubjects: List<DataPointsSubject>): Result<Unit>
}

class UserAPIImpl(
    private val firebaseUtils: FirebaseUtils,
) : UserAPI {
    override suspend fun initUser(dataUser: DataUser): Result<Unit> = runCatching {
        val child = firebaseUtils.getCurrentUserChild()
        if (child?.valueEvents?.firstOrNull()?.exists == false) {
            child.setValue(dataUser.copy(id = child.key ?: ""))
        }
    }

    override suspend fun getUser(): Result<DataUser> = runCatching {
        firebaseUtils.getCurrentUserChild()
            ?.valueEvents
            ?.firstOrNull()
            ?.value()
            ?: throw Exception("User not found")
    }

    override suspend fun getUserAsFlow(): Result<Flow<DataUser>> = runCatching {
        firebaseUtils
            .getCurrentUserChild()
            ?.valueEvents
            ?.mapNotNull { dataSnapshot ->
                dataSnapshot.value<DataUser?>()
                    ?.let { it.copy(streak = it.streak.filterNot { streak -> streak == "null" }) }
                    ?.copy(id = dataSnapshot.key ?: "")
            }
            ?: throw Exception("User not found")
    }

    override suspend fun updateStreak(streak: MutableList<String>) {
        val child = firebaseUtils.getCurrentUserChild()
        child
            ?.child("streak")
            ?.setValue(streak)
    }

    override suspend fun addPoints(pointsSubjects: List<DataPointsSubject>) = runCatching {
        firebaseUtils
            .getCurrentUserChild()
            ?.run {
                child("pointsSubjects")
                    .setValue(pointsSubjects)
                child("points")
                    .setValue(pointsSubjects.sumOf { it.points })
            }
            ?: throw Exception("User not found")
    }
}