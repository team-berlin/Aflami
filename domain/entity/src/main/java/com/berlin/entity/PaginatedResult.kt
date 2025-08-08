package com.berlin.entity

data class PaginatedResult<T>(
    val page: Int,
    val totalPages: Int,
    val results: List<T>
)
