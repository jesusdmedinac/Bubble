package presentation.mapper

import data.remote.Challenge
import data.remote.ChallengeCategory
import data.remote.ChallengeStatus
import data.remote.Reward
import presentation.model.UIChallenge
import presentation.model.UIReward

fun Reward.toUIReward(): UIReward = UIReward(
    id = id,
    title = title,
    description = description,
    image = image,
    points = points,
)

fun Challenge.toUIChallenge(): UIChallenge = UIChallenge(
    id = id,
    name = title,
    description = description,
    image = image,
    rewards = rewards.map { it.toUIReward() },
    category = when (category) {
        ChallengeCategory.TODO -> presentation.model.ChallengeCategory.TODO
        ChallengeCategory.LECTURA -> presentation.model.ChallengeCategory.LECTURA
        ChallengeCategory.AIRE_LIBRE -> presentation.model.ChallengeCategory.AIRE_LIBRE
        ChallengeCategory.ARTE -> presentation.model.ChallengeCategory.ARTE
        ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO -> presentation.model.ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO
        ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY -> presentation.model.ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY
        ChallengeCategory.COCINA_Y_COMIDA -> presentation.model.ChallengeCategory.COCINA_Y_COMIDA
        ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD -> presentation.model.ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD
        ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE -> presentation.model.ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE
        ChallengeCategory.SALUD_Y_BIENESTAR -> presentation.model.ChallengeCategory.SALUD_Y_BIENESTAR
        ChallengeCategory.DESAFIOS_RIDICULOS -> presentation.model.ChallengeCategory.DESAFIOS_RIDICULOS
        ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO -> presentation.model.ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO
    },
    rejected = rejected,
    status = when (status) {
        ChallengeStatus.SUGGESTED -> presentation.model.ChallengeStatus.SUGGESTED
        ChallengeStatus.ACCEPTED -> presentation.model.ChallengeStatus.ACCEPTED
        ChallengeStatus.COMPLETED -> presentation.model.ChallengeStatus.COMPLETED
        ChallengeStatus.IN_PROGRESS -> presentation.model.ChallengeStatus.IN_PROGRESS
        ChallengeStatus.EXPIRED -> presentation.model.ChallengeStatus.EXPIRED
        ChallengeStatus.CANCELLED -> presentation.model.ChallengeStatus.CANCELLED
    },
)