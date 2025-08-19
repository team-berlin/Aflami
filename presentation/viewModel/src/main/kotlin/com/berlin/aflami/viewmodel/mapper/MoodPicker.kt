package com.berlin.aflami.viewmodel.mapper
/**
 * Represents different user moods and maps them to a list of corresponding TMDB genre IDs.
 *
 * This enum is used to recommend movies based on the user's current mood.
 * Each mood is associated with one or more TMDB genre IDs.
 *
* ### Enum Values:
* - **SAD** → Comedy (35)
* - **NEUTRAL** → Documentary (99), Mystery (9648)
* - **ROMANTIC** → Romance (10749), Music (10402)
* - **ANGRY** → Comedy (35), Animation (16), Family (10751)
* - **DEPRESSED** → Drama (18), Animation (16)
* - **SAD_DIZZY** → Adventure (12), Fantasy (14), Science Fiction (878)
 * @property moodGenres List of TMDB genre IDs associated with the mood.
 */
enum class UserMood(val moodGenres: List<Int>) {
    SAD(listOf(35)),
    NEUTRAL(listOf(99, 9648)),
    ROMANTIC(listOf(10749, 10402)),
    ANGRY(listOf(35, 16, 10751)),
    DEPRESSED(listOf(18, 16)),
    SAD_DIZZY(listOf(12, 14, 878));
}