package store.devshcherbinavv.cinemasearch.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import store.devshcherbinavv.cinemasearch.App
import store.devshcherbinavv.cinemasearch.domain.Film
import store.devshcherbinavv.cinemasearch.domain.Interactor
import javax.inject.Inject

class FavoriteFragmentViewModel: ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
//        interactor.getFavoritesFilmsFromApi(2, object : ApiCallback {
//            override fun onSuccess(films: List<Film>) {
//                filmsListLiveData.postValue(films)
//            }
//
//            override fun onFailure() {
//
//            }
//        })
    }
//
//    interface ApiCallback {
//        fun onSuccess(films: List<Film>)
//        fun onFailure()
//    }
}