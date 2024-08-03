package data.remote

import data.utils.FirebaseUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

interface ChallengesAPI {
    suspend fun getChallenges(): Result<Flow<List<Challenge>>>
    suspend fun getChallenge(id: Int): Result<Flow<Challenge>>
    suspend fun saveChallenge(challenge: Challenge): Result<Unit>
    suspend fun deleteChallenge(id: Int): Result<Unit>

    companion object {
        val Default = object : ChallengesAPI {
            override suspend fun getChallenges(): Result<Flow<List<Challenge>>> {
                TODO("getChallenges on ChallengeAPI is not yet implemented")
            }

            override suspend fun getChallenge(id: Int): Result<Flow<Challenge>> {
                TODO("getChallenge on ChallengeAPI is not yet implemented")
            }

            override suspend fun saveChallenge(challenge: Challenge): Result<Unit> {
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
    override suspend fun getChallenges(): Result<Flow<List<Challenge>>> = runCatching {
        challengesChild()
            ?.valueEvents
            ?.mapNotNull { snapshot -> snapshot.value<List<Challenge>?>() }
            ?: emptyFlow()
    }

    override suspend fun getChallenge(id: Int): Result<Flow<Challenge>> = runCatching {
        challengeChild(id)
            ?.valueEvents
            ?.map { snapshot -> snapshot.value() }
            ?: emptyFlow()
    }

    override suspend fun saveChallenge(challenge: Challenge): Result<Unit> = runCatching {
        challengeChild(challenge.id)
            ?.setValue(challenge)
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