package usecase.game

import repository.GameRepository
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(userID: Int): Int {
        return repository.getPoints(userID)
    }
}
