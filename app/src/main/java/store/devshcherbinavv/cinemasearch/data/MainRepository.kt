package store.devshcherbinavv.cinemasearch.data

import androidx.lifecycle.LiveData
import store.devshcherbinavv.cinemasearch.data.dao.FilmDao
import store.devshcherbinavv.cinemasearch.data.entity.Film
import java.util.concurrent.Executors

class MainRepository (private val filmDao: FilmDao){
    fun putToDb(films: List<Film>) {
        //Запросы в бд должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): LiveData<List<Film>> {
        return filmDao.getCachedFilms()
    }

}