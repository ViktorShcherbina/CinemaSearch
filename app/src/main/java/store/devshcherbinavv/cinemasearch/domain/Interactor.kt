package store.devshcherbinavv.cinemasearch.domain

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import store.devshcherbinavv.cinemasearch.data.API
import store.devshcherbinavv.cinemasearch.data.TmdbApi
import store.devshcherbinavv.cinemasearch.data.MainRepository
import store.devshcherbinavv.cinemasearch.data.entity.TmdbResultsDto
import store.devshcherbinavv.cinemasearch.viewmodel.FavoriteFragmentViewModel
import store.devshcherbinavv.cinemasearch.viewmodel.HomeFragmentViewModel

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {
    //В конструктор мы будем передавать коллбэк из вью модели, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, которую нужно загрузить (это для пагинации)

    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.API_KEY, "ru-RU", page).enqueue(object :
            Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                callback.onSuccess(store.devshcherbinavv.cinemasearch.utils.Converter.convertApiListToDtoList(response.body()?.tmdbFilms))
            }

            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }
    fun getFavoritesFilmsFromApi(page: Int, callback: FavoriteFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.API_KEY, "ru-RU", page).enqueue(object :
            Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                callback.onSuccess(store.devshcherbinavv.cinemasearch.utils.Converter.convertApiListToDtoList(response.body()?.tmdbFilms))
            }

            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }
}