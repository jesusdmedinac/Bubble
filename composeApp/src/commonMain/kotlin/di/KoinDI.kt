package di

import data.ChatAPI
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import presentation.screenmodel.BubbleTabScreenModel

fun domainModule() = module {
    single { BubbleTabScreenModel(get(), get()) }
}

fun appModules() = listOf(
    domainModule()
)