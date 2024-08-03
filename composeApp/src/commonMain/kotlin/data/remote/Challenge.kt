package data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Challenge(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val rewards: List<Reward> = emptyList(),
    val category: ChallengeCategory = ChallengeCategory.TODO,
    val rejected: Boolean = false,
    val status: ChallengeStatus = ChallengeStatus.SUGGESTED,
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

@Serializable
enum class ChallengeStatus {
    SUGGESTED,
    ACCEPTED,
    COMPLETED,
    IN_PROGRESS,
    EXPIRED,
    CANCELLED,
}