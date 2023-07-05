package store.devshcherbinavv.cinemasearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import store.devshcherbinavv.cinemasearch.MainActivity
import store.devshcherbinavv.cinemasearch.databinding.FragmentHomeBinding
import store.devshcherbinavv.cinemasearch.domain.Film
import store.devshcherbinavv.cinemasearch.utils.AnimationHelper
import store.devshcherbinavv.cinemasearch.view.rv_adapters.FilmListRecyclerAdapter
import store.devshcherbinavv.cinemasearch.view.rv_adapters.TopSpacingItemDecoration
import store.devshcherbinavv.cinemasearch.viewmodel.HomeFragmentViewModel
import java.util.*


class HomeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private lateinit var binding: FragmentHomeBinding
    private var filmsDataBase = listOf<Film>()
        //Используем backing field
        set(value) {
            //Если придет такое же значение то мы выходим из метода
            if (field == value) return
            //Если пришло другое значение, то кладем его в переменную
            field = value
            //Обновляем RV адаптер
            filmsAdapter.addItems(field)
        }
    private var filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
        override fun click(film: Film) {
            (requireActivity() as MainActivity).launchDetailsFragment(film)
        }
    })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                filmsAdapter.addItems(result)
                return true
            }
        })
        initRV()
        filmsAdapter.addItems(filmsDataBase)
        viewModel.filmsListLiveData.observe(viewLifecycleOwner,androidx.lifecycle.Observer<List<Film>>{
            filmsDataBase = it
        })

    }


    private fun initRV(){

        binding.rcView.apply {

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)

    }

}