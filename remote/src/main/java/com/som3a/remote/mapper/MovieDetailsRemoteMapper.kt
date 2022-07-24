package com.som3a.remote.mapper

import com.som3a.remote.model.GenreRemote
import com.som3a.remote.model.ProductionCompanyRemote
import com.som3a.remote.model.ProductionCountryRemote
import com.som3a.remote.model.SpokenLanguageRemote
import com.som3a.common.Mapper
import com.som3a.domain.entity.*
import com.som3a.remote.model.MovieDetailsRemote
import javax.inject.Inject

class MovieDetailsRemoteMapper @Inject constructor() : Mapper<MovieDetailsRemote, MovieDetails> {
    override fun map(input: MovieDetailsRemote): MovieDetails {
        return MovieDetails(
            adult = input.adult,
            backdropPath = input.backdropPath,
            budget = input.budget,
            genres = mapGenres(input.genreRemotes),
            homepage = input.homepage,
            id = input.id,
            imdbId = input.imdbId,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            overview = input.overview,
            popularity = input.popularity,
            posterPath = input.posterPath,
            productionCompanies = mapProductionCompanies(input.productionCompanies),
            productionCountries = mapProductionCountries(input.productionCountries),
            releaseDate = input.releaseDate,
            revenue = input.revenue,
            runtime = input.runtime,
            spokenLanguages = mapSpokenLanguages(input.spokenLanguageRemotes),
            status = input.status,
            tagline = input.tagline,
            title = input.title,
            video = input.video,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount
        )
    }

    private fun mapSpokenLanguages(spokenLanguageRemotes: java.util.ArrayList<SpokenLanguageRemote>): List<SpokenLanguages>? {
        return spokenLanguageRemotes.map {
            SpokenLanguages(
                iso6391 = it.iso_639_1,
                name = it.name
            )
        }
    }

    private fun mapProductionCountries(productionCountries: java.util.ArrayList<ProductionCountryRemote>): List<ProductionCountries>? {
        return productionCountries.map {
            ProductionCountries(
                iso31661 = it.iso_3166_1,
                name = it.name
            )
        }
    }

    private fun mapProductionCompanies(productionCompanies: ArrayList<ProductionCompanyRemote>): List<ProductionCompanies>? {
        return productionCompanies.map {
            ProductionCompanies(
                id = it.id,
                logoPath = it.logo_path,
                name = it.name,
                originCountry = it.origin_country
            )
        }
    }

    private fun mapGenres(genreRemotes: List<GenreRemote>?): List<Genres>? {
        return genreRemotes?.map {
            Genres(
                id = it.id,
                name = it.name
            )
        }
    }
}