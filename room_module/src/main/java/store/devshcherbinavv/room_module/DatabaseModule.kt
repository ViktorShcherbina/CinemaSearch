package store.devshcherbinavv.room_module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import store.devshcherbinavv.cinemasearch.data.MainRepository
import store.devshcherbinavv.cinemasearch.data.dao.FilmDao
import javax.inject.Singleton



@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideFilmDao(databaseContract: DatabaseContract) : FilmDao {
        return databaseContract.itemsDao()
    }
    @Provides
    @Singleton
    fun provideItemsDatabase (context: Context): DatabaseContract{
        return Room.databaseBuilder(
            context,
            ItemsDatabase::class.java, "film_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}