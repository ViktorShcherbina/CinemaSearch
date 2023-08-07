package store.devshcherbinavv.cinemasearch.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import store.devshcherbinavv.cinemasearch.data.MainRepository
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRepository() = MainRepository()
}