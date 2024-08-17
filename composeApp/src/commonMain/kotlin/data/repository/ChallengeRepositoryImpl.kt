package data.repository

import data.mapper.toData
import data.mapper.toDomain
import data.remote.ChallengesAPI
import domain.model.Challenge
import domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChallengeRepositoryImpl(
    private val challengesAPI: ChallengesAPI
) : ChallengeRepository {
    override suspend fun getChallenges(): Result<Flow<List<Challenge>>> =
        challengesAPI.getChallenges()
            .map { flow ->
                flow.map { it.map { dataChallenge -> dataChallenge.toDomain() } }
            }

    override suspend fun getChallenge(id: Int): Result<Flow<Challenge>> =
        challengesAPI.getChallenge(id)
            .map { flow ->
                flow.map { it.toDomain() }
            }

    override suspend fun saveChallenge(challenge: Challenge): Result<Challenge> =
        challengesAPI.saveChallenge(challenge.toData()).map { it.toDomain() }

    override suspend fun deleteChallenge(id: Int): Result<Unit> =
        challengesAPI.deleteChallenge(id)
}