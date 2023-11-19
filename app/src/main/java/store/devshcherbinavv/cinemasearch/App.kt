package store.devshcherbinavv.cinemasearch

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import store.devshcherbinavv.cinemasearch.data.ApiConstants
import store.devshcherbinavv.cinemasearch.data.MainRepository
import store.devshcherbinavv.cinemasearch.data.TmdbApi
import store.devshcherbinavv.cinemasearch.di.AppComponent
import store.devshcherbinavv.cinemasearch.di.DaggerAppComponent
import store.devshcherbinavv.cinemasearch.di.modules.DatabaseModule
import store.devshcherbinavv.cinemasearch.di.modules.DomainModule
import store.devshcherbinavv.cinemasearch.di.modules.RemoteModule
import store.devshcherbinavv.cinemasearch.domain.Interactor
import store.devshcherbinavv.cinemasearch.view.notifications.NotificationConstants.CHANNEL_ID
import java.util.concurrent.TimeUnit

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Задаем имя, описание и важность канала
            val name = "WatchLaterChannel"
            val descriptionText = "FilmsSearch notification Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            //Создаем канал, передав в параметры его ID(строка), имя(строка), важность(константа)
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            //Отдельно задаем описание
            mChannel.description = descriptionText
            //Получаем доступ к менеджеру нотификаций
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            //Регистрируем канал
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}

