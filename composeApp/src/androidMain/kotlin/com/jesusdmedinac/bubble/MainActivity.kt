package com.jesusdmedinac.bubble

import App
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import data.Challenge
import data.ChatAPI
import data.Message
import data.Reward
import getHttpClient
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.resourcesFetcher
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val androidConfig = remember {
                KamelConfig {
                    takeFrom(KamelConfig.Default)
                    httpFetcher(getHttpClient())
                    resourcesFetcher(this@MainActivity)
                }
            }
            CompositionLocalProvider(LocalKamelConfig provides androidConfig) {
                App(
                    chatAPI = ChatAPIImpl()
                )
            }
        }
    }
}

class ChatAPIImpl : ChatAPI {
    val challenges: List<Challenge>
        get() = listOf(
            Challenge(
                1,
                "Desafío de la hora sin pantalla",
                "Dedica al menos una hora al día a actividades que no requieran el uso de dispositivos móviles, como leer un libro, hacer ejercicio o salir a caminar.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                2,
                "Día sin redes sociales",
                "Escoge un día a la semana para abstenerse completamente de usar cualquier red social. Utiliza ese tiempo para conectarte con amigos y familiares de forma presencial o mediante llamadas telefónicas.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                3,
                "Desconexión digital antes de dormir",
                "Establece un límite de tiempo, como una hora antes de acostarte, para apagar todos los dispositivos electrónicos y dedicarte a actividades relajantes, como leer un libro o meditar, para mejorar la calidad de tu sueño.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                4,
                "Caja de carga fuera del dormitorio",
                "Coloca tu cargador de dispositivos móviles en una habitación diferente a la que duermes para evitar la tentación de revisar tu teléfono antes de dormir y al despertar.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                5,
                "Paseo sin teléfono",
                "Realiza caminatas cortas o paseos diarios sin llevar contigo tu dispositivo móvil. Esto te ayudará a desconectar y a disfrutar del entorno sin distracciones.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                6,
                "Desafío de la pantalla en blanco y negro",
                "Configura tu teléfono para que la pantalla se muestre en blanco y negro durante un día entero. Esto puede reducir la atracción visual y limitar el tiempo que pasas frente a la pantalla.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                7,
                "Desafío de notificaciones",
                "Desactiva las notificaciones innecesarias de aplicaciones que no son urgentes o importantes. Esto te ayudará a reducir las interrupciones constantes y a enfocarte en tus tareas diarias.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                8,
                "Hora de pantalla limitada",
                "Establece un límite de tiempo diario para el uso de dispositivos móviles y utiliza aplicaciones o funciones del dispositivo que te ayuden a monitorear y limitar tu tiempo de pantalla.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                9,
                "Desafío de la conversación cara a cara",
                "Prioriza las interacciones en persona sobre las conversaciones en línea. Programa encuentros con amigos y familiares para disfrutar de momentos de calidad sin la distracción de los dispositivos móviles.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            ),
            Challenge(
                10,
                "Día de actividades sin tecnología",
                "Dedica un día completo a realizar actividades que no requieran el uso de tecnología, como cocinar, hacer manualidades o practicar deportes al aire libre.",
                "https://picsum.photos/id/${Random.nextInt(0, 30)}/200/300",
                rewards = listOf(
                    Reward(
                        title = "Amazon Gift Card",
                        description = "Disfruta de tiempo adicional para dedicarte a tus pasatiempos favoritos.",
                        image = "https://images-na.ssl-images-amazon.com/images/G/01/GiftCards/GCLP/D_AGC_US-es.jpg"
                    ),
                    Reward(
                        title = "Un boleto 2x1 al cine",
                        description = "Mejora tu capacidad de concentración al reducir la distracción de los dispositivos móviles.",
                        image = "https://pbs.twimg.com/media/EVPaRBUVAAAupv0?format=jpg&name=medium"
                    )
                )
            )
        )
    val instructions = """
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
            
            Debes justificar la razón para sugerir cada reto.
                        
            Los retos disponibles son los siguientes: ${
        Json.encodeToString(
            challenges
        )
    }
        
            Si el usuario te pregunta, puedes utilizar los datos de uso para generar una respuesta
            personalizada sobre el tiempo real de uso del usuario.
            
            Es ESTRICTAMENTE NECESARIO que respondas con el siguiente formato JSON sin ninguna otra información:
            {
                "message": "Tu respuesta",
                "challenge": {
                    "id": 1,
                    "title": "Title",
                    "description": "Description",
                    "image": "https://example.com/image.jpg",
                    "rewards": [
                        {
                            "id": 1,
                            "title": "Title",
                            "description": "Description",
                            "image": "https://example.com/image.jpg",
                            "points": 100
                        }
                    ]
                },
            }
            
            REGLAS:
            - El challenge puede ser null si no hay ninguna sugerencia.
            - El mensaje nunca debe ser null.
        """.trimIndent()
    private val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with most use cases
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "AIzaSyCqJirMwoz8fcMNbfcYXnge0dBro5DdwLs",
        systemInstruction = content { text(instructions) },
    )

    override suspend fun sendMessage(messages: List<Message>): Message {
        val reversedMessages = messages
            .reversed()
        val history = reversedMessages
            .map { content(role = it.author) { text(it.body) } }
            .dropLast(1)
        generativeModel.startChat(
            history = history
        )
        val response = generativeModel.generateContent(
            reversedMessages.lastOrNull()?.body ?: "Empty message"
        )
        return Message(
            author = "system",
            body = response.text ?: "Error generating response"
        )
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(chatAPI = object : ChatAPI {
        override suspend fun sendMessage(messages: List<Message>): Message {
            TODO("Not yet implemented")
        }
    })
}