package presentation.model

import data.remote.Challenge
import data.remote.Reward
import kotlinx.serialization.Serializable

@Serializable
data class UIChallenge(
    override val id: Int = 0,
    override val name: String = "",
    override val description: String = "",
    override val image: String = "",
    val category: ChallengeCategory = ChallengeCategory.TODO,
    val rewards: List<UIReward> = emptyList(),
    val rejected: Boolean = false,
    val status: ChallengeStatus = ChallengeStatus.SUGGESTED,
) : UICard {
    val shareText: String
        get() {
            val rewardsText = if (rewards.isNotEmpty()) {
                "Recompensas: ${rewards.joinToString { it.title }}"
            } else {
                "La recompensa es mi nuevo hábito saludable."
            }
            return """
                ¡Bubble me recomendó el reto "$name"!
              
                $description
                
                $rewardsText
                
                ¡Únete a mí!
                https://tally.so/r/3E1E2A
                
                #Bubble #BuildWithGemini 
                #KMP #CMP 
                #KotlinMultiplatform #ComposeMultiplatform
                @JesusDMedinaC 
            """.trimIndent()
        }

    fun toDataChallenge() = Challenge(
        id = id,
        title = name,
        description = description,
        image = image,
        category = when (category) {
            ChallengeCategory.TODO -> data.remote.ChallengeCategory.TODO
            ChallengeCategory.LECTURA -> data.remote.ChallengeCategory.LECTURA
            ChallengeCategory.AIRE_LIBRE -> data.remote.ChallengeCategory.AIRE_LIBRE
            ChallengeCategory.ARTE -> data.remote.ChallengeCategory.ARTE
            ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO -> data.remote.ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO
            ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY -> data.remote.ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY
            ChallengeCategory.COCINA_Y_COMIDA -> data.remote.ChallengeCategory.COCINA_Y_COMIDA
            ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD -> data.remote.ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD
            ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE -> data.remote.ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE
            ChallengeCategory.SALUD_Y_BIENESTAR -> data.remote.ChallengeCategory.SALUD_Y_BIENESTAR
            ChallengeCategory.DESAFIOS_RIDICULOS -> data.remote.ChallengeCategory.DESAFIOS_RIDICULOS
            ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO -> data.remote.ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO
        },
        rejected = rejected,
        status = when (status) {
            ChallengeStatus.SUGGESTED -> data.remote.ChallengeStatus.SUGGESTED
            ChallengeStatus.ACCEPTED -> data.remote.ChallengeStatus.ACCEPTED
            ChallengeStatus.COMPLETED -> data.remote.ChallengeStatus.COMPLETED
            ChallengeStatus.IN_PROGRESS -> data.remote.ChallengeStatus.IN_PROGRESS
            ChallengeStatus.EXPIRED -> data.remote.ChallengeStatus.EXPIRED
            ChallengeStatus.CANCELLED -> data.remote.ChallengeStatus.CANCELLED
        }
    )

    fun toChallenge(): Challenge = Challenge(
        id = id,
        title = name,
        description = description,
        image = image,
        rewards = rewards.map { it.toReward() },
        category = when (category) {
            ChallengeCategory.TODO -> data.remote.ChallengeCategory.TODO
            ChallengeCategory.LECTURA -> data.remote.ChallengeCategory.LECTURA
            ChallengeCategory.AIRE_LIBRE -> data.remote.ChallengeCategory.AIRE_LIBRE
            ChallengeCategory.ARTE -> data.remote.ChallengeCategory.ARTE
            ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO -> data.remote.ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO
            ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY -> data.remote.ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY
            ChallengeCategory.COCINA_Y_COMIDA -> data.remote.ChallengeCategory.COCINA_Y_COMIDA
            ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD -> data.remote.ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD
            ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE -> data.remote.ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE
            ChallengeCategory.SALUD_Y_BIENESTAR -> data.remote.ChallengeCategory.SALUD_Y_BIENESTAR
            ChallengeCategory.DESAFIOS_RIDICULOS -> data.remote.ChallengeCategory.DESAFIOS_RIDICULOS
            ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO -> data.remote.ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO
        },
        rejected = rejected,
        status = when (status) {
            ChallengeStatus.SUGGESTED -> data.remote.ChallengeStatus.SUGGESTED
            ChallengeStatus.ACCEPTED -> data.remote.ChallengeStatus.ACCEPTED
            ChallengeStatus.COMPLETED -> data.remote.ChallengeStatus.COMPLETED
            ChallengeStatus.IN_PROGRESS -> data.remote.ChallengeStatus.IN_PROGRESS
            ChallengeStatus.EXPIRED -> data.remote.ChallengeStatus.EXPIRED
            ChallengeStatus.CANCELLED -> data.remote.ChallengeStatus.CANCELLED
        }
    )
}

@Serializable
data class UIReward(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val points: Int = 0,
) {
    fun toReward(): Reward = Reward(
        id = id,
        title = title,
        description = description,
        image = image,
        points = points,
    )
}

@Serializable
enum class ChallengeCategory(
    val title: String
) {
    TODO("Todo"),
    LECTURA("Lectura"),
    AIRE_LIBRE("Aire Libre"),
    ARTE("Arte"),
    EJERCICIO_Y_BIENESTAR_FISICO("Ejercicio y bienestar físico"),
    MANUALIDADES_Y_PROYECTOS_DIY("Manualidades y proyectos DIY"),
    COCINA_Y_COMIDA("Cocina y comida"),
    VOLUNTARIADO_Y_COMUNIDAD("Voluntariado y comunidad"),
    DESARROLLO_PERSONAL_Y_APRENDIZAJE("Desarrollo personal y aprendizaje"),
    SALUD_Y_BIENESTAR("Salud y bienestar"),
    DESAFIOS_RIDICULOS("Desafíos y ridiculos"),
    MUSICA_Y_ENTRETENIMIENTO("Música y entretenimiento"),
}

@Serializable
enum class ChallengeStatus {
    SUGGESTED,
    ACCEPTED,
    COMPLETED,
    IN_PROGRESS,
    EXPIRED,
    CANCELLED,
}