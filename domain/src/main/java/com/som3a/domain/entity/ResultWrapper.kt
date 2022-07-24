package com.som3a.domain.entity


data class ResultWrapper<T>(
    val page: Int?,
    val results: List<T>?,
    val total_results: Int?,
    val total_pages: Int?
)