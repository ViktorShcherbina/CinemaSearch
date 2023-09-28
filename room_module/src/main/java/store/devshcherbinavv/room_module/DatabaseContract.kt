package store.devshcherbinavv.room_module

import store.devshcherbinavv.cinemasearch.data.dao.FilmDao

interface DatabaseContract {
    fun itemsDao(): FilmDao
}