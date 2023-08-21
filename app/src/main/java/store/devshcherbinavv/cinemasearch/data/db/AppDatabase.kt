package store.devshcherbinavv.cinemasearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import store.devshcherbinavv.cinemasearch.data.dao.FilmDao
import store.devshcherbinavv.cinemasearch.data.entity.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}