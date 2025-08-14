package usecase.game

import repository.GameRepository
import javax.inject.Inject

class UpdatePointsUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(userID: Int, points: Int) {
        repository.updatePoints(userID, points)
    }
}