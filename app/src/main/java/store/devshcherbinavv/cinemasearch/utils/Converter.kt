package store.devshcherbinavv.cinemasearch.utils

import store.devshcherbinavv.cinemasearch.data.entity.TmdbFilm
import store.devshcherbinavv.cinemasearch.domain.Film

object Converter {
    fun convertApiListToDtoList(list: List<TmdbFilm>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(Film(
                title = it.title,
                poster = it.posterPath,
                description = it.overview,
                rating = it.voteAverage,
                isInFavorites = false
            ))
        }
        return result
    }
}