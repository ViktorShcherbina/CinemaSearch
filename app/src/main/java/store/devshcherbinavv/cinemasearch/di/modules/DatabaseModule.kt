package store.devshcherbinavv.cinemasearch.di.modules


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import store.devshcherbinavv.cinemasearch.data.MainRepository
import store.devshcherbinavv.cinemasearch.data.dao.FilmDao
import store.devshcherbinavv.cinemasearch.data.db.AppDatabase
import store.devshcherbinavv.cinemasearch.data.db.DatabaseHelper
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "film_db"
        ).build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}