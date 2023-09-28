package store.devshcherbinavv.room_module

import androidx.room.Database
import androidx.room.RoomDatabase
import store.devshcherbinavv.cinemasearch.data.dao.FilmDao
import store.devshcherbinavv.cinemasearch.data.entity.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase(), DatabaseContract {
    abstract fun filmDao(): FilmDao
}