package store.devshcherbinavv.cinemasearch.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
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

        fun mapConvertApiListToDtoList(list: List<TmdbFilm>): Disposable {
        val result = Observable.just(list).map{
            it.forEach {
                Film(
                        title = it.title,
                        poster = it.posterPath,
                        description = it.overview,
                        rating = it.voteAverage,
                        isInFavorites = false
                    )
            }
        }.subscribe{}
        return result
    }
}