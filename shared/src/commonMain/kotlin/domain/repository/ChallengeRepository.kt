package domain.repository

import domain.model.Challenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getChallenges(): Result<Flow<List<Challenge>>>
    suspend fun getChallenge(id: Int): Result<Flow<Challenge>>
    suspend fun saveChallenge(challenge: Challenge): Result<Challenge>
    suspend fun deleteChallenge(id: Int): Result<Unit>
}