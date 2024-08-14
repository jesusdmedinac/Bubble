package domain.usecase

import data.today
import domain.model.PointsSubject
import domain.repository.UserRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlinx.datetime.minus

interface AddNewStreakPointsUseCase {
    suspend operator fun invoke(currentStreak: List<String>): Result<List<String>>
}

class AddNewStreakPointsUseCaseImpl(
    private val userRepository: UserRepository,
) : AddNewStreakPointsUseCase {
    override suspend fun invoke(currentStreak: List<String>): Result<List<String>> = runCatching {
        val today = today()
        val formattedTodayDate = LocalDate.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
        }.format(today)
        var streak = currentStreak.toMutableList()
        val lastStreak = streak.lastOrNull()
        if (lastStreak == null) {
            streak.add(formattedTodayDate)
            userRepository.updateStreak(streak)
            userRepository.updatePoints(PointsSubject.NewStreak(formattedTodayDate))
        } else {
            val lastStreakDate = LocalDate.parse(lastStreak)
            val daysBetween = today.minus(lastStreakDate).days
            when {
                daysBetween == 0 -> Unit
                daysBetween == 1 -> {
                    streak.add(formattedTodayDate)
                    userRepository.updateStreak(streak)
                    userRepository.updatePoints(PointsSubject.NewStreak(formattedTodayDate))
                }

                daysBetween > 1 -> {
                    streak = mutableListOf(formattedTodayDate)
                    userRepository.updateStreak(streak)
                    userRepository.updatePoints(PointsSubject.NewStreak(formattedTodayDate))
                }
            }
        }
        streak
    }
}