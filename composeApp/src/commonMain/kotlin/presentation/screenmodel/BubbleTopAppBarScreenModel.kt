package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.today
import domain.UserRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import presentation.mapper.toUI
import presentation.model.UIUser

class BubbleTopAppBarScreenModel(
    private val userRepository: UserRepository,
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
        val today = today()
        val formattedTodayDate = LocalDate.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
        }.format(today)
        var streak = state.user.streak.toMutableList()
        val lastStreak = streak.lastOrNull()
        if (lastStreak == null) {
            streak.add(formattedTodayDate)
            userRepository.updateStreak(streak)
        } else {
            val lastStreakDate = LocalDate.parse(lastStreak)
            val daysBetween = today.minus(lastStreakDate).days
            when {
                daysBetween == 0 -> Unit
                daysBetween == 1 -> {
                    streak.add(formattedTodayDate)
                    userRepository.updateStreak(streak)
                }

                daysBetween > 1 -> {
                    streak = mutableListOf(formattedTodayDate)
                    userRepository.updateStreak(streak)
                }
            }
        }
    }
}

data class BubbleTopAppBarState(
    val user: UIUser = UIUser(),
)

sealed class BubbleTopAppBarSideEffect