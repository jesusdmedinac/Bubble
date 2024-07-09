package di

import org.koin.dsl.module
import presentation.screenmodel.BubbleTabScreenModel

fun domainModule() = module {
    single { BubbleTabScreenModel(get(), get(), get()) }
}

fun appModules() = listOf(
    domainModule()
)