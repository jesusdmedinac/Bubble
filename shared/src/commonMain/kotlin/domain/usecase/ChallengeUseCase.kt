package domain.usecase

import domain.model.Challenge
import domain.model.ChallengeStatus
import domain.model.PointsSubject
import domain.repository.ChallengeRepository
import domain.repository.UserRepository

interface ChallengeUseCase {
    suspend fun suggest(challenge: Challenge): Result<Challenge>
    suspend fun accept(challenge: Challenge): Result<Challenge>
    suspend fun complete(challenge: Challenge): Result<Challenge>
    suspend fun cancel(challenge: Challenge): Result<Challenge>
    suspend fun expire(challenge: Challenge): Result<Challenge>
}

class ChallengeUseCaseImpl(
    private val userRepository: UserRepository,
    private val challengeRepository: ChallengeRepository,
) : ChallengeUseCase {

    override suspend fun suggest(challenge: Challenge): Result<Challenge> = challengeRepository
        .saveChallenge(challenge.copy(status = ChallengeStatus.SUGGESTED, rejected = false))

    override suspend fun accept(challenge: Challenge): Result<Challenge> = challengeRepository
        .saveChallenge(challenge.copy(status = ChallengeStatus.ACCEPTED))
        .fold(
            onSuccess = { savedChallenge ->
                userRepository.updatePoints(PointsSubject.ChallengeAccepted(savedChallenge))
                    .fold(
                        onSuccess = { Result.success(savedChallenge) },
                        onFailure = { Result.failure(it) }
                    )
            },
            onFailure = {
                Result.failure(it)
            }
        )

    override suspend fun complete(challenge: Challenge): Result<Challenge> = challengeRepository
        .saveChallenge(challenge.copy(status = ChallengeStatus.COMPLETED))
        .fold(
            onSuccess = { savedChallenge ->
                userRepository.updatePoints(PointsSubject.ChallengeCompleted(savedChallenge))
                    .fold(
                        onSuccess = { Result.success(savedChallenge) },
                        onFailure = { Result.failure(it) }
                    )
            },
            onFailure = {
                Result.failure(it)
            }
        )

    override suspend fun cancel(challenge: Challenge): Result<Challenge> = challengeRepository
        .saveChallenge(challenge.copy(status = ChallengeStatus.CANCELLED))

    override suspend fun expire(challenge: Challenge): Result<Challenge> = challengeRepository
        .saveChallenge(challenge.copy(status = ChallengeStatus.EXPIRED))
}
