package usecase

import repository.MovieDetailsRepository

class GetTopRating (private val moviesDetailsRepository: MovieDetailsRepository,
    private val tvShRepository: MovieDetailsRepository){
    operator suspend fun invoke() {

    }
}