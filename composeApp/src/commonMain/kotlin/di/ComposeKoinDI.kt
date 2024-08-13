package di

import data.remote.AuthAPI
import data.remote.AuthAPIImpl
import data.remote.ChallengesAPI
import data.remote.ChallengesAPIImpl
import data.remote.ChatMessagesAPI
import data.remote.ChatMessagesAPIImpl
import data.remote.UserAPI
import data.remote.UserAPIImpl
import data.repository.ChatRepositoryImpl
import data.repository.UserRepositoryImpl
import data.utils.FirebaseUtils
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import domain.ChatRepository
import domain.UserRepository
import org.koin.core.KoinApplication
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import presentation.screenmodel.BubbleTabScreenModel
import presentation.screenmodel.BubbleTopAppBarScreenModel
import presentation.screenmodel.ProfileTabScreenModel
import kotlin.native.concurrent.ThreadLocal

fun firebaseModule() = module {
    single<FirebaseDatabase> {
        Firebase.database.apply {
            setPersistenceEnabled(true)
        }
    }
    single<FirebaseAuth> { Firebase.auth }
    single { FirebaseUtils(get(), get()) }
}

fun dataModule() = module {
    single<ChatMessagesAPI> { ChatMessagesAPIImpl(get()) }
    single<AuthAPI> { AuthAPIImpl() }
    single<ChallengesAPI> { ChallengesAPIImpl(get()) }
    single<UserAPI> { UserAPIImpl(get()) }
}

fun domainModule() = module {
    single<ChatRepository> { ChatRepositoryImpl(get(), get(), get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}

fun presentationModule() = module {
    single { BubbleTopAppBarScreenModel(get(), get()) }
    single { BubbleTabScreenModel(get(), get(), get(), get(), get()) }
    single { ProfileTabScreenModel(get(), get(), get()) }
}

fun appModules() = listOf(
    firebaseModule(),
    dataModule(),
    domainModule(),
    presentationModule(),
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