package store.devshcherbinavv.cinemasearch

import android.app.Application
import store.devshcherbinavv.cinemasearch.di.AppComponent
import store.devshcherbinavv.cinemasearch.di.DaggerAppComponent
import store.devshcherbinavv.cinemasearch.di.modules.DatabaseModule
import store.devshcherbinavv.cinemasearch.di.modules.DomainModule
import store.devshcherbinavv.remote_module.DaggerRemoteComponent



class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}

