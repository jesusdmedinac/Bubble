package presentation.mapper

import data.remote.model.DataChallenge
import data.remote.model.DataChallengeCategory
import data.remote.model.DataChallengeStatus
import data.remote.model.DataReward
import presentation.model.UIChallenge
import presentation.model.UIReward

fun DataReward.toUIReward(): UIReward = UIReward(
    id = id,
    title = title,
    description = description,
    image = image,
    points = points,
)

fun DataChallenge.toUIChallenge(): UIChallenge = UIChallenge(
    id = id,
    name = title,
    description = description,
    image = image,
    rewards = dataRewards.map { it.toUIReward() },
    category = when (category) {
        DataChallengeCategory.TODO -> presentation.model.ChallengeCategory.TODO
        DataChallengeCategory.LECTURA -> presentation.model.ChallengeCategory.LECTURA
        DataChallengeCategory.AIRE_LIBRE -> presentation.model.ChallengeCategory.AIRE_LIBRE
        DataChallengeCategory.ARTE -> presentation.model.ChallengeCategory.ARTE
        DataChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO -> presentation.model.ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO
        DataChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY -> presentation.model.ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY
        DataChallengeCategory.COCINA_Y_COMIDA -> presentation.model.ChallengeCategory.COCINA_Y_COMIDA
        DataChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD -> presentation.model.ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD
        DataChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE -> presentation.model.ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE
        DataChallengeCategory.SALUD_Y_BIENESTAR -> presentation.model.ChallengeCategory.SALUD_Y_BIENESTAR
        DataChallengeCategory.DESAFIOS_RIDICULOS -> presentation.model.ChallengeCategory.DESAFIOS_RIDICULOS
        DataChallengeCategory.MUSICA_Y_ENTRETENIMIENTO -> presentation.model.ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO
    },
    rejected = rejected,
    status = when (status) {
        DataChallengeStatus.SUGGESTED -> presentation.model.ChallengeStatus.SUGGESTED
        DataChallengeStatus.ACCEPTED -> presentation.model.ChallengeStatus.ACCEPTED
        DataChallengeStatus.COMPLETED -> presentation.model.ChallengeStatus.COMPLETED
        DataChallengeStatus.IN_PROGRESS -> presentation.model.ChallengeStatus.IN_PROGRESS
        DataChallengeStatus.EXPIRED -> presentation.model.ChallengeStatus.EXPIRED
        DataChallengeStatus.CANCELLED -> presentation.model.ChallengeStatus.CANCELLED
    },
)