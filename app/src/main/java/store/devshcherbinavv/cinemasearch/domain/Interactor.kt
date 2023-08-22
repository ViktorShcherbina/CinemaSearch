package store.devshcherbinavv.cinemasearch.domain

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import store.devshcherbinavv.cinemasearch.data.API.API_KEY
import store.devshcherbinavv.cinemasearch.data.TmdbApi
import store.devshcherbinavv.cinemasearch.data.MainRepository
import store.devshcherbinavv.cinemasearch.data.PreferenceProvider
import store.devshcherbinavv.cinemasearch.data.entity.Film
import store.devshcherbinavv.cinemasearch.data.entity.TmdbResultsDto
import store.devshcherbinavv.cinemasearch.viewmodel.HomeFragmentViewModel

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        //Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API_KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                //При успехе мы вызываем метод, передаем onSuccess и в этот коллбэк список фильмов
                val list = store.devshcherbinavv.cinemasearch.utils.Converter.convertApiListToDtoList(response.body()?.tmdbFilms)
                //Кладем фильмы в бд
                list.forEach {
                    repo.putToDb(list)
                }
                callback.onSuccess()
            }

            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }
    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): LiveData<List<Film>> = repo.getAllFromDB()
}