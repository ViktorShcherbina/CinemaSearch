package store.devshcherbinavv.cinemasearch.di.modules


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import store.devshcherbinavv.cinemasearch.data.MainRepository
import store.devshcherbinavv.cinemasearch.data.dao.FilmDao
import javax.inject.Singleton

@Module
class DatabaseModule {
//    @Singleton
//    @Provides
//    fun provideFilmDao(context: Context) =
//        Room.databaseBuilder(
//            context,
//            ItemsDatabase::class.java,
//            "film_db"
//        ).build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}