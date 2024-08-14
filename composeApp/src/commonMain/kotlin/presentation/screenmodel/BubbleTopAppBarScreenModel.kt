package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.mapper.toData
import data.remote.AnalyticsAPI
import data.today
import domain.repository.UserRepository
import domain.usecase.AddNewStreakPointsUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import presentation.mapper.toDomain
import presentation.mapper.toUI
import presentation.model.UIUser

class BubbleTopAppBarScreenModel(
    private val userRepository: UserRepository,
    private val analyticsAPI: AnalyticsAPI,
    private val addNewStreakPointsUseCase: AddNewStreakPointsUseCase,
) : ScreenModel, ContainerHost<BubbleTopAppBarState, BubbleTopAppBarSideEffect> {
    override val container: Container<BubbleTopAppBarState, BubbleTopAppBarSideEffect> =
        screenModelScope.container(BubbleTopAppBarState()) {
            coroutineScope {
                launch {
                    collectUserFlow()
                }
            }
        }

    private suspend fun collectUserFlow() {
        userRepository
            .getUserAsFlow()
            .onSuccess { flow ->
                flow.collect { user ->
                    onLoad(user.toUI())
                }
            }
            .onFailure { exception ->
                exception.printStackTrace()
                delay(1000)
                collectUserFlow()
            }
    }

    private fun onLoad(user: UIUser) = intent {
        reduce {
            state.copy(
                user = user
            )
        }
        addNewStreakPointsUseCase(state.user.streak)
            .onSuccess { newStreak ->
                analyticsAPI.sendUpdateStreakEvent(user.toDomain().toData(), newStreak)
            }
    }
}

data class BubbleTopAppBarState(
    val user: UIUser = UIUser(),
)

sealed class BubbleTopAppBarSideEffect