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

fun Message.toData(): DataMessage = DataMessage(
    id = id,
    author = author,
    dataBody = body.toData()
)

fun Body.toData(): DataBody = DataBody(
    message = message,
    dataChallenge = challenge?.toData()
)

fun Challenge.toData(): DataChallenge = DataChallenge(
    id = id,
    title = title,
    description = description,
    image = image,
    dataRewards = rewards.map { it.toData() },
    category = category.toData(),
    rejected = rejected,
    status = status.toData(),
)

fun Reward.toData(): DataReward = DataReward(
    id = id,
    title = title,
    description = description,
    image = image,
    points = points,
)

fun ChallengeCategory.toData(): DataChallengeCategory = when (this) {
    ChallengeCategory.TODO -> DataChallengeCategory.TODO
    ChallengeCategory.LECTURA -> DataChallengeCategory.LECTURA
    ChallengeCategory.AIRE_LIBRE -> DataChallengeCategory.AIRE_LIBRE
    ChallengeCategory.ARTE -> DataChallengeCategory.ARTE
    ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO -> DataChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO
    ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY -> DataChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY
    ChallengeCategory.COCINA_Y_COMIDA -> DataChallengeCategory.COCINA_Y_COMIDA
    ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD -> DataChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD
    ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE -> DataChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE
    ChallengeCategory.SALUD_Y_BIENESTAR -> DataChallengeCategory.SALUD_Y_BIENESTAR
    ChallengeCategory.DESAFIOS_RIDICULOS -> DataChallengeCategory.DESAFIOS_RIDICULOS
    ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO -> DataChallengeCategory.MUSICA_Y_ENTRETENIMIENTO
}

fun ChallengeStatus.toData(): DataChallengeStatus = when(this) {
    ChallengeStatus.SUGGESTED -> DataChallengeStatus.SUGGESTED
    ChallengeStatus.ACCEPTED -> DataChallengeStatus.ACCEPTED
    ChallengeStatus.COMPLETED -> DataChallengeStatus.COMPLETED
    ChallengeStatus.IN_PROGRESS -> DataChallengeStatus.IN_PROGRESS
    ChallengeStatus.EXPIRED -> DataChallengeStatus.EXPIRED
    ChallengeStatus.CANCELLED -> DataChallengeStatus.CANCELLED
}