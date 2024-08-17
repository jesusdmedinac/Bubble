package domain.usecase

import domain.model.Message
import domain.model.PointsSubject
import domain.repository.ChatRepository
import domain.repository.UserRepository

interface AddSendMessagePointsUseCase {
    suspend operator fun invoke(message: Message): Result<Unit>
}

class AddSendMessagePointsUseCaseImpl(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
) : AddSendMessagePointsUseCase {
    override suspend fun invoke(message: Message): Result<Unit> = chatRepository
        .sendMessage(message)
        .map { userRepository.updatePoints(PointsSubject.MessageSent(message.id)) }
}