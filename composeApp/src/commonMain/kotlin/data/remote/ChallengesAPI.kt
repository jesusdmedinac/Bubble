package data.remote

import data.remote.model.DataChallenge
import data.utils.FirebaseUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

interface ChallengesAPI {
    suspend fun getChallenges(): Result<Flow<List<DataChallenge>>>
    suspend fun getChallenge(id: Int): Result<Flow<DataChallenge>>
    suspend fun saveChallenge(dataChallenge: DataChallenge): Result<DataChallenge>
    suspend fun deleteChallenge(id: Int): Result<Unit>

    companion object {
        val Default = object : ChallengesAPI {
            override suspend fun getChallenges(): Result<Flow<List<DataChallenge>>> {
                TODO("getChallenges on ChallengeAPI is not yet implemented")
            }

            override suspend fun getChallenge(id: Int): Result<Flow<DataChallenge>> {
                TODO("getChallenge on ChallengeAPI is not yet implemented")
            }

            override suspend fun saveChallenge(dataChallenge: DataChallenge): Result<DataChallenge> {
                TODO("saveChallenge on ChallengeAPI is not yet implemented")
            }

            override suspend fun deleteChallenge(id: Int): Result<Unit> {
                TODO("deleteChallenge on ChallengeAPI is not yet implemented")
            }
        }
    }
}

class ChallengesAPIImpl(
    private val firebaseUtils: FirebaseUtils,
) : ChallengesAPI {
    override suspend fun getChallenges(): Result<Flow<List<DataChallenge>>> = runCatching {
        challengesChild()
            ?.valueEvents
            ?.mapNotNull { snapshot -> snapshot.value<List<DataChallenge>?>() }
            ?: emptyFlow()
    }

    override suspend fun getChallenge(id: Int): Result<Flow<DataChallenge>> = runCatching {
        challengeChild(id)
            ?.valueEvents
            ?.map { snapshot -> snapshot.value() }
            ?: emptyFlow()
    }

    override suspend fun saveChallenge(dataChallenge: DataChallenge): Result<DataChallenge> = runCatching {
        challengeChild(dataChallenge.id)
            ?.setValue(dataChallenge)
        dataChallenge
    }

    override suspend fun deleteChallenge(id: Int): Result<Unit> = runCatching {
        challengeChild(id)
            ?.removeValue()
    }

    private fun challengesChild() = firebaseUtils
        .getCurrentUserChild()
        ?.child("challenges")

    private fun challengeChild(id: Int) = challengesChild()
        ?.child(id.toString())
}