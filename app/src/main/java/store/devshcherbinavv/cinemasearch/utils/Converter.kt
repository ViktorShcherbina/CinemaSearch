package store.devshcherbinavv.cinemasearch.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import store.devshcherbinavv.cinemasearch.data.entity.Film

object Converter {
    fun convertApiListToDtoList(list: List<store.devshcherbinavv.remote_module.entity.TmdbFilm>?): List<Film> {
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

        fun mapConvertApiListToDtoList(list: List<store.devshcherbinavv.remote_module.entity.TmdbFilm>): Disposable {
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