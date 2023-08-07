package store.devshcherbinavv.cinemasearch.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import store.devshcherbinavv.cinemasearch.data.MainRepository
import store.devshcherbinavv.cinemasearch.data.TmdbApi
import store.devshcherbinavv.cinemasearch.domain.Interactor
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}