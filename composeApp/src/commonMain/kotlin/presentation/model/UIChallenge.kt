package presentation.model

import data.remote.model.DataChallenge
import data.remote.model.DataChallengeCategory
import data.remote.model.DataChallengeStatus
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
        },
        rejected = rejected,
        status = when (status) {
            ChallengeStatus.SUGGESTED -> DataChallengeStatus.SUGGESTED
            ChallengeStatus.ACCEPTED -> DataChallengeStatus.ACCEPTED
            ChallengeStatus.COMPLETED -> DataChallengeStatus.COMPLETED
            ChallengeStatus.IN_PROGRESS -> DataChallengeStatus.IN_PROGRESS
            ChallengeStatus.EXPIRED -> DataChallengeStatus.EXPIRED
            ChallengeStatus.CANCELLED -> DataChallengeStatus.CANCELLED
        }
    )

    fun toChallenge(): DataChallenge = DataChallenge(
        id = id,
        title = name,
        description = description,
        image = image,
        rewards = rewards.map { it.toReward() },
        category = when (category) {
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
        },
        rejected = rejected,
        status = when (status) {
            ChallengeStatus.SUGGESTED -> DataChallengeStatus.SUGGESTED
            ChallengeStatus.ACCEPTED -> DataChallengeStatus.ACCEPTED
            ChallengeStatus.COMPLETED -> DataChallengeStatus.COMPLETED
            ChallengeStatus.IN_PROGRESS -> DataChallengeStatus.IN_PROGRESS
            ChallengeStatus.EXPIRED -> DataChallengeStatus.EXPIRED
            ChallengeStatus.CANCELLED -> DataChallengeStatus.CANCELLED
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