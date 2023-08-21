package store.devshcherbinavv.cinemasearch.data

import android.content.ContentValues
import android.database.Cursor
import store.devshcherbinavv.cinemasearch.data.dao.FilmDao
import store.devshcherbinavv.cinemasearch.data.db.DatabaseHelper
import store.devshcherbinavv.cinemasearch.data.entity.Film
import java.util.concurrent.Executors

class MainRepository (private val filmDao: FilmDao){
    fun putToDb(films: List<Film>) {
        //Запросы в бд должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): List<Film> {
        return filmDao.getCachedFilms()
    }

}