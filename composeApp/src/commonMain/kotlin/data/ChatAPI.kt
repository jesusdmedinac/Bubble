package data

import kotlin.random.Random

interface ChatAPI {
    suspend fun sendMessage(messages: List<Message>): Message

    companion object {
        val Default = object : ChatAPI {
            override suspend fun sendMessage(messages: List<Message>): Message {
                TODO("sendMessage on ChatAPI is not implemented yet")
            }
        }
    }
}

private val challenges: List<Challenge>
    get() = listOf(
        Challenge(
            1,
            "Desafío de la hora sin pantalla",
            "Dedica al menos una hora al día a actividades que no requieran el uso de dispositivos móviles, como leer un libro, hacer ejercicio o salir a caminar.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            2,
            "Día sin redes sociales",
            "Escoge un día a la semana para abstenerse completamente de usar cualquier red social. Utiliza ese tiempo para conectarte con amigos y familiares de forma presencial o mediante llamadas telefónicas.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            3,
            "Desconexión digital antes de dormir",
            "Establece un límite de tiempo, como una hora antes de acostarte, para apagar todos los dispositivos electrónicos y dedicarte a actividades relajantes, como leer un libro o meditar, para mejorar la calidad de tu sueño.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            4,
            "Caja de carga fuera del dormitorio",
            "Coloca tu cargador de dispositivos móviles en una habitación diferente a la que duermes para evitar la tentación de revisar tu teléfono antes de dormir y al despertar.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            5,
            "Paseo sin teléfono",
            "Realiza caminatas cortas o paseos diarios sin llevar contigo tu dispositivo móvil. Esto te ayudará a desconectar y a disfrutar del entorno sin distracciones.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            6,
            "Desafío de la pantalla en blanco y negro",
            "Configura tu teléfono para que la pantalla se muestre en blanco y negro durante un día entero. Esto puede reducir la atracción visual y limitar el tiempo que pasas frente a la pantalla.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            7,
            "Desafío de notificaciones",
            "Desactiva las notificaciones innecesarias de aplicaciones que no son urgentes o importantes. Esto te ayudará a reducir las interrupciones constantes y a enfocarte en tus tareas diarias.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            8,
            "Hora de pantalla limitada",
            "Establece un límite de tiempo diario para el uso de dispositivos móviles y utiliza aplicaciones o funciones del dispositivo que te ayuden a monitorear y limitar tu tiempo de pantalla.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            9,
            "Desafío de la conversación cara a cara",
            "Prioriza las interacciones en persona sobre las conversaciones en línea. Programa encuentros con amigos y familiares para disfrutar de momentos de calidad sin la distracción de los dispositivos móviles.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        ),
        Challenge(
            10,
            "Día de actividades sin tecnología",
            "Dedica un día completo a realizar actividades que no requieran el uso de tecnología, como cocinar, hacer manualidades o practicar deportes al aire libre.",
            "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
            rewards = emptyList()
        )
    )


fun systemInstructions(): String = """
    Tu nombre es Bubble. Eres un asistente para reducir el uso de dispositivos móviles.
    
    Naciste bajo la idea del siguiente publicación:
    The mediating role of loneliness in the relationship between smartphone addiction and subjective well-being
    
    Este estudio examinó el papel mediador de la soledad en la relación entre la adicción al
    smartphone y el bienestar subjetivo entre estudiantes universitarios chinos. Se llevó a 
    cabo en 16 universidades de ocho provincias y municipios en China, con la participación 
    de 1527 estudiantes. Se utilizaron escalas para medir la adicción al smartphone, la soledad 
    y el bienestar subjetivo. Los hallazgos revelaron que: (1) variables demográficas como 
    el lugar de origen, nivel educativo e ingreso familiar influían en el bienestar subjetivo 
    de los estudiantes universitarios; (2) existía una correlación negativa significativa 
    entre la adicción al smartphone y el bienestar subjetivo, junto con una correlación 
    positiva significativa entre la adicción al smartphone y la soledad, indicando el efecto 
    predictivo negativo significativo de la adicción al smartphone en el bienestar subjetivo; 
    (3) la soledad mediaba parcialmente la relación entre la adicción al smartphone y el 
    bienestar subjetivo entre los estudiantes universitarios, lo que sugiere que la adicción 
    al smartphone podría impactar directamente en el bienestar subjetivo de los estudiantes 
    universitarios, o indirectamente a través de su efecto en la soledad.
    
    Bajo esta premisa, puedes sugerir retos a los usuarios de acuerdo a la conversación.
    
    La lista de retos disponibles es la siguiente: $challenges
    
    Debes justificar la razón para sugerir cada reto.

    Si el usuario te pregunta, puedes utilizar los datos de uso para generar una respuesta
    personalizada sobre el tiempo real de uso del usuario.
    
    Es ESTRICTAMENTE NECESARIO que respondas con el siguiente formato JSON sin ninguna otra información,
    es decir, el primer caracter de tu respuesta debe ser el símbolo "{" y el último debe ser el 
    símbolo "}":

    message: Tu respuesta en texto plano de tipo String
    challenge: Un objeto JSON con los siguientes campos:
        id: El ID del reto de tipo Int
        title: El título del reto de tipo String
        description: La descripción del reto de tipo String
        image: La URL de la imagen del reto de tipo String
        rewards: Un array de objetos JSON con los siguientes campos:
            id: El ID del premio de tipo Int
            title: El título del premio de tipo String
            description: La descripción del premio de tipo String
            image: La URL de la imagen del premio de tipo String
            points: Los puntos que se le darán al usuario de tipo Int
""".trimIndent()

fun String.toJsonAsString(): String = runCatching {
    if (contains("```json")) this
        .substringAfter("```json")
        .substringBefore("```")
    else if (contains("```")) this
        .substringAfter("```")
        .substringBefore("```")
    else this
}
    .fold(
        onSuccess = { it },
        onFailure = {
            """
                    {
                        "message": "Perdona, no tengo respuesta",
                        "challenge": null
                    }
                """.trimIndent()
        }
    )