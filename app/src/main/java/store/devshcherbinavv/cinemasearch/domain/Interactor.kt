package store.devshcherbinavv.cinemasearch.domain

import store.devshcherbinavv.cinemasearch.data.MainRepository

class Interactor(private val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase

}