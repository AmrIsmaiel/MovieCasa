package com.som3a.domain.entity

data class MovieDetails(
    var adult: Boolean?,
    var backdropPath: String?,
    var budget: Int?,
    var genres: List<Genres>?,
    var homepage: String?,
    var id: Int?,
    var imdbId: String?,
    var originalLanguage: String?,
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double?,
    var posterPath: String?,
    var productionCompanies: List<ProductionCompanies>?,
    var productionCountries: List<ProductionCountries>?,
    var releaseDate: String?,
    var revenue: Int?,
    var runtime: Int?,
    var spokenLanguages: List<SpokenLanguages>?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean?,
    var voteAverage: Double?,
    var voteCount: Int?
)
