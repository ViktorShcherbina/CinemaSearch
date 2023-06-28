package store.devshcherbinavv.cinemasearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import store.devshcherbinavv.cinemasearch.App
import store.devshcherbinavv.cinemasearch.domain.Film
import store.devshcherbinavv.cinemasearch.domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData = MutableLiveData<List<Film>>()
    private var interactor: Interactor = App.instance.interactor
    init {
        //TODO init interactor
        val films = interactor.getFilmsDB()
        filmsListLiveData.postValue(films)
    }
}