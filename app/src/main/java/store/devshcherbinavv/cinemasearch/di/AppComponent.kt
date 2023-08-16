package store.devshcherbinavv.cinemasearch.di

import dagger.Component
import store.devshcherbinavv.cinemasearch.di.modules.DatabaseModule
import store.devshcherbinavv.cinemasearch.di.modules.DomainModule
import store.devshcherbinavv.cinemasearch.di.modules.RemoteModule
import store.devshcherbinavv.cinemasearch.viewmodel.FavoriteFragmentViewModel
import store.devshcherbinavv.cinemasearch.viewmodel.HomeFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    // заглушки
    fun inject(favoriteFragmentViewModel: FavoriteFragmentViewModel)

}