package store.devshcherbinavv.cinemasearch.di

import dagger.Component
import store.devshcherbinavv.cinemasearch.di.modules.DatabaseModule
import store.devshcherbinavv.cinemasearch.di.modules.DomainModule
import store.devshcherbinavv.cinemasearch.di.modules.RemoteModule
import store.devshcherbinavv.cinemasearch.viewmodel.FavoriteFragmentViewModel
import store.devshcherbinavv.cinemasearch.viewmodel.HomeFragmentViewModel
import store.devshcherbinavv.cinemasearch.viewmodel.SettingsFragmentViewModel
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
    //метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в FavoriteFragmentViewModel
    fun inject(favoriteFragmentViewModel: FavoriteFragmentViewModel)
}