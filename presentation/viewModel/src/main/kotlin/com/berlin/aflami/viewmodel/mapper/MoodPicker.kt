package com.berlin.aflami.viewmodel.mapper

enum class UserMood(mood: List<String>) {
    SAD(listOf("Comedy")),
    NEUTRAL(listOf("Documentary", "Mystery")),
    ROMANTIC(
        listOf(
            "Romance", "Musical"
        )
    ),
    ANGRY(listOf("Comedy", "Animation", "Family")),
    DEPRESSED(
        listOf(
            "Drama", "Animation"
        )
    ),
    SAD_DIZZY(listOf("Adventure", "Fantasy", "Science Fiction")),
}
