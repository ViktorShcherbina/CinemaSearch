package store.devshcherbinavv.cinemasearch.viewmodel

import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import store.devshcherbinavv.cinemasearch.App
import store.devshcherbinavv.cinemasearch.SingleLiveEvent
import store.devshcherbinavv.cinemasearch.data.entity.Film
import store.devshcherbinavv.cinemasearch.domain.Interactor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListLiveData: LiveData<List<Film>>

    init {
        App.instance.dagger.inject(this)
        filmsListLiveData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        showProgressBar.postValue(true)
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess() {
                showProgressBar.postValue(false)
            }

            override fun onFailure() {
                Executors.newSingleThreadExecutor().execute {
                    showProgressBar.postValue(false)
                    postError()
                }
            }
        })
    }
    val  errorEvent = SingleLiveEvent<String>()
    fun postError(){
        errorEvent.postValue("Error happened")
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()

    }
}