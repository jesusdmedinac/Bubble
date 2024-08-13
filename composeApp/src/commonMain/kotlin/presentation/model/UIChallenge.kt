package presentation.model

import data.remote.model.DataChallenge
import data.remote.model.DataReward
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

    fun toDataChallenge() = DataChallenge(
        id = id,
        title = name,
        description = description,
        image = image,
        category = when (category) {
            ChallengeCategory.TODO -> data.remote.model.DataChallengeCategory.TODO
            ChallengeCategory.LECTURA -> data.remote.model.DataChallengeCategory.LECTURA
            ChallengeCategory.AIRE_LIBRE -> data.remote.model.DataChallengeCategory.AIRE_LIBRE
            ChallengeCategory.ARTE -> data.remote.model.DataChallengeCategory.ARTE
            ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO -> data.remote.model.DataChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO
            ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY -> data.remote.model.DataChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY
            ChallengeCategory.COCINA_Y_COMIDA -> data.remote.model.DataChallengeCategory.COCINA_Y_COMIDA
            ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD -> data.remote.model.DataChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD
            ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE -> data.remote.model.DataChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE
            ChallengeCategory.SALUD_Y_BIENESTAR -> data.remote.model.DataChallengeCategory.SALUD_Y_BIENESTAR
            ChallengeCategory.DESAFIOS_RIDICULOS -> data.remote.model.DataChallengeCategory.DESAFIOS_RIDICULOS
            ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO -> data.remote.model.DataChallengeCategory.MUSICA_Y_ENTRETENIMIENTO
        },
        rejected = rejected,
        status = when (status) {
            ChallengeStatus.SUGGESTED -> data.remote.model.DataChallengeStatus.SUGGESTED
            ChallengeStatus.ACCEPTED -> data.remote.model.DataChallengeStatus.ACCEPTED
            ChallengeStatus.COMPLETED -> data.remote.model.DataChallengeStatus.COMPLETED
            ChallengeStatus.IN_PROGRESS -> data.remote.model.DataChallengeStatus.IN_PROGRESS
            ChallengeStatus.EXPIRED -> data.remote.model.DataChallengeStatus.EXPIRED
            ChallengeStatus.CANCELLED -> data.remote.model.DataChallengeStatus.CANCELLED
        }
    )

    fun toChallenge(): DataChallenge = DataChallenge(
        id = id,
        title = name,
        description = description,
        image = image,
        rewards = rewards.map { it.toReward() },
        category = when (category) {
            ChallengeCategory.TODO -> data.remote.model.DataChallengeCategory.TODO
            ChallengeCategory.LECTURA -> data.remote.model.DataChallengeCategory.LECTURA
            ChallengeCategory.AIRE_LIBRE -> data.remote.model.DataChallengeCategory.AIRE_LIBRE
            ChallengeCategory.ARTE -> data.remote.model.DataChallengeCategory.ARTE
            ChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO -> data.remote.model.DataChallengeCategory.EJERCICIO_Y_BIENESTAR_FISICO
            ChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY -> data.remote.model.DataChallengeCategory.MANUALIDADES_Y_PROYECTOS_DIY
            ChallengeCategory.COCINA_Y_COMIDA -> data.remote.model.DataChallengeCategory.COCINA_Y_COMIDA
            ChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD -> data.remote.model.DataChallengeCategory.VOLUNTARIADO_Y_COMUNIDAD
            ChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE -> data.remote.model.DataChallengeCategory.DESARROLLO_PERSONAL_Y_APRENDIZAJE
            ChallengeCategory.SALUD_Y_BIENESTAR -> data.remote.model.DataChallengeCategory.SALUD_Y_BIENESTAR
            ChallengeCategory.DESAFIOS_RIDICULOS -> data.remote.model.DataChallengeCategory.DESAFIOS_RIDICULOS
            ChallengeCategory.MUSICA_Y_ENTRETENIMIENTO -> data.remote.model.DataChallengeCategory.MUSICA_Y_ENTRETENIMIENTO
        },
        rejected = rejected,
        status = when (status) {
            ChallengeStatus.SUGGESTED -> data.remote.model.DataChallengeStatus.SUGGESTED
            ChallengeStatus.ACCEPTED -> data.remote.model.DataChallengeStatus.ACCEPTED
            ChallengeStatus.COMPLETED -> data.remote.model.DataChallengeStatus.COMPLETED
            ChallengeStatus.IN_PROGRESS -> data.remote.model.DataChallengeStatus.IN_PROGRESS
            ChallengeStatus.EXPIRED -> data.remote.model.DataChallengeStatus.EXPIRED
            ChallengeStatus.CANCELLED -> data.remote.model.DataChallengeStatus.CANCELLED
        }
    )

    val statusLabel: String
        get() = when {
            rejected -> ""
            else -> status.title
        }
}

@Serializable
data class UIReward(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val points: Int = 0,
) {
    fun toReward(): DataReward = DataReward(
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
enum class ChallengeStatus(
    val title: String
) {
    SUGGESTED("Sugerido"),
    ACCEPTED("Aceptado"),
    COMPLETED("Completado"),
    IN_PROGRESS("En progreso"),
    EXPIRED("Caducado"),
    CANCELLED("Cancelado"),
}