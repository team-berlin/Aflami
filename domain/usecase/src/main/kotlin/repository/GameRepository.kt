package repository

interface GameRepository {
    suspend fun getPoints(userID:Int): Int
    suspend fun updatePoints(userID: Int, points: Int)
    suspend fun addPoints(userID: Int, points: Int)
}