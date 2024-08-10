package di

import data.remote.BubbleInstructionsAPI
import data.remote.BubbleInstructionsAPIImpl
import data.remote.GeminiAPI
import data.remote.GeminiAPIImpl
import data.remote.httpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

fun serverDataModule() = module {
    single<HttpClient> { httpClient() }
    single<BubbleInstructionsAPI> { BubbleInstructionsAPIImpl(get(), get()) }
    single<GeminiAPI> { GeminiAPIImpl(get(), get(), get()) }
}

fun serverModules() = listOf(
    serverDataModule()
)