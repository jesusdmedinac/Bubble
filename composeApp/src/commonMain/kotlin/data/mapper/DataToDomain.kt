package data.mapper

import data.remote.model.DataBody
import data.remote.model.DataChallenge
import data.remote.model.DataChallengeCategory
import data.remote.model.DataChallengeStatus
import data.remote.model.DataMessage
import data.remote.model.DataReward
import domain.model.Body
import domain.model.Challenge
import domain.model.ChallengeCategory
import domain.model.ChallengeStatus
import domain.model.Message
import domain.model.Reward
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun DataMessage.toDomain(): Message = Message(
    id = id,
    author = author,
    body = dataBody.toDomain()
)

fun DataBody.toDomain(): Body = Body(
    message = message,
    challenge = dataChallenge?.toDomain()
)

fun DataChallenge.toDomain(): Challenge = Challenge(
    id = id,
    title = title,
    description = description,
    image = image,
    rewards = dataRewards.map { it.toDomain() },
    category = category.toDomain(),
    rejected = rejected,
    status = status.toDomain(),
)

fun DataReward.toDomain(): Reward = Reward(
    id = id,
    title = title,
    description = description,
    image = image,
    points = points,
)

fun DataChallengeCategory.toDomain(): ChallengeCategory = when (this) {
    DataChallengeCategory.TODO -> ChallengeCategory.TODO
    DataChallengeCategory.LECTURA -> ChallengeCategory.LECTURA
    DataChallengeCategory.AIRE_LIBRE -> ChallengeCategory.AIRE_LIBRE
    DataChallengeCategory.ARTE -> ChallengeCategory.ARTE
    DataChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO -> ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO
    DataChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY -> ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY
    DataChallengeCategory.COCINA_Y_COMIDA -> ChallengeCategory.COCINA_Y_COMIDA
    DataChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD -> ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD
    DataChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE -> ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE
    DataChallengeCategory.SALUD_Y_BIENESTAR -> ChallengeCategory.SALUD_Y_BIENESTAR
    DataChallengeCategory.DESAFIOS_RIDICULOS -> ChallengeCategory.DESAFIOS_RIDICULOS
    DataChallengeCategory.MUSICA_Y_ENTRETENIMIENTO -> ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO
}

fun DataChallengeStatus.toDomain(): ChallengeStatus = when (this) {
    DataChallengeStatus.SUGGESTED -> ChallengeStatus.SUGGESTED
    DataChallengeStatus.ACCEPTED -> ChallengeStatus.ACCEPTED
    DataChallengeStatus.COMPLETED -> ChallengeStatus.COMPLETED
    DataChallengeStatus.IN_PROGRESS -> ChallengeStatus.IN_PROGRESS
    DataChallengeStatus.EXPIRED -> ChallengeStatus.EXPIRED
    DataChallengeStatus.CANCELLED -> ChallengeStatus.CANCELLED
}

// region List Mappers
fun List<DataMessage>.toDomain(): List<Message> = map { it.toDomain() }

fun Flow<List<DataMessage>>.toDomain(): Flow<List<Message>> = map { dataMessages ->
    dataMessages.toDomain()
}

fun Result<Flow<List<DataMessage>>>.toDomain(): Result<Flow<List<Message>>> = map { dataMessages ->
    dataMessages.toDomain()
}
// endregion