package usecase.mediadetails

import com.berlin.entity.Movie
import repository.ContinueWatchingRepository

class AddContinueWatchingMovieUseCase (
    private val repository: ContinueWatchingRepository
){
    suspend operator fun invoke(movie: Movie) {
        repository.addContinueWatchingMovie(movie)
    }

}