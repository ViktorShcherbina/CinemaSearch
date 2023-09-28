package store.devshcherbinavv.room_module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import store.devshcherbinavv.cinemasearch.data.dao.FilmDao
import javax.inject.Singleton

private const val DATABASE_NAME = "ITEMS_DB"

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase (databaseContract: DatabaseContract) : FilmDao {
        return databaseContract.itemsDao()
    }
    @Provides
    @Singleton
    fun provideItemsDatabase (context: Context): DatabaseContract{
        return Room.databaseBuilder(
            context,
            ItemsDatabase::class.java, DATABASE_NAME
        ).build()
    }
}