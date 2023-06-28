package store.devshcherbinavv.cinemasearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import store.devshcherbinavv.cinemasearch.MainActivity
import store.devshcherbinavv.cinemasearch.databinding.FragmentFavoritesBinding
import store.devshcherbinavv.cinemasearch.domain.Film
import store.devshcherbinavv.cinemasearch.utils.AnimationHelper
import store.devshcherbinavv.cinemasearch.view.rv_adapters.FilmListRecyclerAdapter
import store.devshcherbinavv.cinemasearch.view.rv_adapters.TopSpacingItemDecoration
import store.devshcherbinavv.cinemasearch.viewmodel.FavoriteFragmentViewModel

class FavoritesFragment: Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private var filmsAdapter =
        FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
            override fun click(film: Film) {
                (requireActivity() as MainActivity).launchDetailsFragment(film)
            }
        })
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(FavoriteFragmentViewModel::class.java)
    }

    private var filmsDataBase = listOf<Film>()
        //Используем backing field
        set(value) {
            if (field == value) return
            field = value.filter { it.isInFavorites }
            //Обновляем RV адаптер
            filmsAdapter.addItems(field)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.favoritesFragmentRoot,
            requireActivity(),
            2)

        initRecycler()
        filmsAdapter.addItems(filmsDataBase)
        viewModel.filmsListLiveData.observe(
            viewLifecycleOwner
        ) {
            filmsDataBase = it
        }

    }
    private fun initRecycler() {
        binding.rcFavorites.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(7)
            addItemDecoration(decorator)
        }
    }


}