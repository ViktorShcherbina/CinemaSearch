package store.devshcherbinavv.cinemasearch.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import store.devshcherbinavv.cinemasearch.data.entity.TmdbFilm
import store.devshcherbinavv.cinemasearch.data.entity.Film

object Converter {
    fun convertApiListToDtoList(list: List<TmdbFilm>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(
                Film(
                title = it.title,
                poster = it.posterPath,
                description = it.overview,
                rating = it.voteAverage,
                isInFavorites = false
            )
            )
        }
        return result
    }
//    private fun mapConvertApiListToDtoList(list: List<TmdbFilm>?): Flow<List<Film>>{
//        val result = flowOf(list).map{
//            list?.forEach {
//                result.add(
//                    Film(
//                        title = it.title,
//                        poster = it.posterPath,
//                        description = it.overview,
//                        rating = it.voteAverage,
//                        isInFavorites = false
//                    )
//                )
//            }
//
//        }
//        return result
//    }
}