package presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class UIChallenge(
    override val id: Int,
    override val name: String,
    override val description: String,
    override val image: String,
    val challengeCategory: ChallengeCategory,
    val rewards: List<UIReward> = emptyList()
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
}

@Serializable
data class UIReward(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val points: Int = 0,
)

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