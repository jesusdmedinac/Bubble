package di

import org.koin.core.KoinApplication
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import presentation.screenmodel.BubbleTabScreenModel
import presentation.screenmodel.ProfileTabScreenModel
import kotlin.native.concurrent.ThreadLocal

fun domainModule() = module {
    single { BubbleTabScreenModel(get(), get(), get()) }
    single { ProfileTabScreenModel(get()) }
}

fun appModules() = listOf(
    domainModule()
)

@ThreadLocal
object KoinDI {
    var koinApplication: KoinApplication? = null

    fun init(koinApplication: KoinApplication) {
        this.koinApplication = koinApplication
    }

    inline fun <reified T : Any> get(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T = koinApplication?.koin?.get(qualifier, parameters)
        ?: throw IllegalStateException("KoinApplication is not initialized")
}