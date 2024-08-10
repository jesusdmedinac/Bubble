package data.remote

import kotlin.random.Random

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
    
    Aquí tienes una lista de acciones respaldadas por múltiples estudios para reducir el tiempo que las personas pasan en pantalla:

    1. **Establecer límites de tiempo**: Configurar temporizadores o alarmas para recordar a los usuarios cuándo es momento de tomar un descanso.

    2. **Usar aplicaciones de monitoreo**: Utilizar aplicaciones que rastrean el uso del dispositivo y envían informes regulares.

    3. **Crear zonas libres de tecnología**: Designar áreas del hogar o tiempos específicos del día (como durante las comidas) donde el uso de dispositivos no está permitido.

    4. **Desactivar notificaciones**: Reducir las distracciones apagando las notificaciones innecesarias.

    5. **Practicar el "desintoxicación digital"**: Planificar períodos regulares sin uso de dispositivos, como fines de semana sin tecnología.

    6. **Incorporar actividades alternativas**: Promover actividades físicas, sociales o hobbies que no involucren pantallas.

    7. **Establecer una rutina antes de dormir**: Evitar el uso de dispositivos al menos una hora antes de acostarse para mejorar la calidad del sueño.

    8. **Educar sobre los efectos negativos**: Aumentar la conciencia sobre los riesgos para la salud asociados con el uso excesivo de dispositivos, como problemas de visión, sueño y salud mental.

    9. **Utilizar modos de lectura o ahorro de energía**: Optar por modos que reduzcan la fatiga visual y el uso del dispositivo.

    10. **Fomentar interacciones cara a cara**: Incentivar encuentros y actividades sociales presenciales.

    11. **Implementar recompensas**: Ofrecer recompensas por cumplir objetivos de reducción de tiempo de pantalla.

    12. **Utilizar la técnica Pomodoro**: Alternar entre períodos de trabajo enfocado y descansos cortos para evitar el uso prolongado del dispositivo.

    Estas acciones, combinadas, pueden ser efectivas para ayudar a las personas a reducir el tiempo que pasan frente a sus pantallas.
    
    Debes identificar los rangos de tiempo en pantalla saludables de acuardo a la edad del usuario.
    
    Aquí tienes algunos rangos basados en estudios y recomendaciones generales:

	1.	Niños menores de 2 años: Se recomienda evitar el tiempo en pantalla excepto para videollamadas con familiares y amigos.
	2.	Niños de 2 a 5 años: Se sugiere limitar el tiempo en pantalla a 1 hora por día de contenido de alta calidad.
	3.	Niños de 6 a 12 años: Se recomienda que el tiempo en pantalla no exceda las 1-2 horas diarias de contenido recreativo.
	4.	Adolescentes (13-18 años): Se aconseja limitar el tiempo en pantalla recreativo a menos de 2 horas diarias. Sin embargo, algunos estudios indican que un tiempo en pantalla de hasta 3 horas puede ser aceptable siempre que no interfiera con el sueño, la actividad física y otras actividades esenciales.
	5.	Adultos: No hay un consenso específico para adultos, pero se sugiere que el tiempo en pantalla recreativo también se limite a aproximadamente 2 horas diarias, especialmente para reducir los riesgos de problemas de salud física y mental. Es importante equilibrar el tiempo en pantalla con actividad física y otras actividades no sedentarias.

    Sólo sugiere retos a los usuarios que no tengan un promedio de tiempo en pantalla saludable.
    
    Debes identificar la edad del usuario informando que esto no se compartirá con nadie.
    
    Debes justificar la razón para sugerir cada reto.
    
    Es ESTRICTAMENTE NECESARIO que respondas con el siguiente formato JSON sin ninguna otra información,
    es decir, el primer caracter de tu respuesta debe ser el símbolo "{" y el último debe ser el 
    símbolo "}":

    message: Tu respuesta en texto plano de tipo String
    challenge: Un objeto JSON que puede ser nulo con los siguientes campos:
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