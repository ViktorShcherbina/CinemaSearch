package store.devshcherbinavv.cinemasearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import store.devshcherbinavv.cinemasearch.data.entity.Film
@Dao
interface FilmDao {
    //Запрос на всю таблицу
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): List<Film>

    //Кладём списком в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)
}