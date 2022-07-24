package com.som3a.feature.mapper

import com.som3a.common.Mapper
import com.som3a.domain.entity.*
import com.som3a.feature.model.MovieDetailsUiModel
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor() : Mapper<MovieDetails, MovieDetailsUiModel> {
    override fun map(input: MovieDetails): MovieDetailsUiModel {
        return MovieDetailsUiModel(
            adult = input.adult ?: false,
            budget = input.budget ?: -1,
            genres = mapGenresList(input.genres),
            homepage = input.homepage ?: "",
            id = input.id ?: -1,
            imdbId = input.imdbId ?: "",
            originalLanguage = input.originalLanguage ?: "",
            originalTitle = input.originalTitle ?: "",
            overview = input.overview ?: "",
            popularity = input.popularity ?: 0.0,
            posterPath = setPosterPath(input.posterPath),
            productionCompanies = mapProductionCompanies(input.productionCompanies),
            productionCountries = mapProductionCountries(input.productionCountries),
            releaseDate = input.releaseDate ?: "",
            revenue = input.revenue ?: 0,
            runtime = input.runtime ?: 0,
            spokenLanguages = mapLanguages(input.spokenLanguages),
            status = input.status ?: "",
            tagline = input.tagline ?: "",
            title = input.title ?: "",
            video = input.video ?: false,
            voteCount = input.voteCount ?: 0,
            voteAverage = input.voteAverage ?: 0.0
        )
    }

    private fun mapLanguages(spokenLanguages: List<SpokenLanguages>?): List<String>? {
        var languages = arrayListOf<String>()
        if (spokenLanguages != null) {
            spokenLanguages.map {
                if (it.name != null)
                    languages.add(it.name!!)
                else ""
            }
        } else languages = arrayListOf()
        return languages
    }

    private fun setPosterPath(posterPath: String?): String {
        return if (posterPath == null) ""
        else "https://image.tmdb.org/t/p/w500/$posterPath"
    }

    private fun mapGenresList(genres: List<Genres>?): List<String>? {
        val genresName = arrayListOf<String>()
        genres?.map {
            if (it.name != null)
                genresName.add(it.name!!)
            else ""
        }
        return genresName
    }

    private fun mapProductionCompanies(companies: List<ProductionCompanies>?): List<String> {
        var companiesName = arrayListOf<String>()
        if (companies != null) {
            companies.map {
                if (it.name != null) {
                    companiesName.add(it.name!!)
                }
            }
        } else {
            companiesName = arrayListOf()
        }
        return companiesName
    }

    private fun mapProductionCountries(countries: List<ProductionCountries>?): List<String> {
        val countriesName = arrayListOf<String>()
        if (countries != null) {
            countries.map {
                if (it.name != null) {
                    countriesName.add(it.name!!)
                }
            }
        } else {
            arrayListOf()
        }
        return countriesName
    }
}