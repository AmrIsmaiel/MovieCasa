package com.som3a.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsUiModel(
    var adult: Boolean?,
    var budget: Int?,
    var genres: List<String>?,
    var homepage: String?,
    var id: Int?,
    var imdbId: String?,
    var originalLanguage: String?,
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double?,
    var posterPath: String?,
    var productionCompanies: List<String>?,
    var productionCountries: List<String>?,
    var releaseDate: String?,
    var revenue: Int?,
    var runtime: Int?,
    var spokenLanguages: List<String>?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean?,
    var voteAverage: Double?,
    var voteCount: Int?
) : Parcelable
