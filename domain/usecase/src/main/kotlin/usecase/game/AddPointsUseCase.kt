package usecase.game

import repository.GameRepository
import javax.inject.Inject

class AddPointsUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(userID: Int, points: Int) {
        repository.addPoints(userID, points)
    }
}
