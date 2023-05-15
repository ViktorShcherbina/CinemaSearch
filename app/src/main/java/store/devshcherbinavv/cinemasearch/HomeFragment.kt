package store.devshcherbinavv.cinemasearch

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Scene
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import store.devshcherbinavv.cinemasearch.databinding.FragmentHomeBinding
import store.devshcherbinavv.cinemasearch.databinding.MergeHomeScreenContentBinding
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment(private val filmsDataBase : List <Film>) : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var binding2: MergeHomeScreenContentBinding
    private var filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
        override fun click(film: Film) {
            (requireActivity() as MainActivity).launchDetailsFragment(film)
        }
    })

    init {
        exitTransition = Slide(Gravity.START).apply { duration = 800;mode = Slide.MODE_OUT }
        reenterTransition = Slide(Gravity.START).apply { duration = 800; }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        view?.setOnClickListener {
            val a = activity as FragmentActivity
            a.supportFragmentManager.beginTransaction().replace(R.id.main, HomeFragment(filmsDataBase)).addToBackStack("HomeFragment").commit()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding2 = MergeHomeScreenContentBinding.inflate(layoutInflater,binding.homeFragmentRoot,false)

        val scene = Scene(
            binding.homeFragmentRoot,
            binding2.root)

        val searchSlide = Slide(Gravity.TOP).addTarget(R.id.search_view)
        val recyclerSlide = Slide(Gravity.BOTTOM).addTarget(R.id.rcView)
        val customTransition = TransitionSet().apply {
            duration = 400
            addTransition(searchSlide)
            addTransition(recyclerSlide)
        }
        TransitionManager.go(scene, customTransition)

        binding2.searchView.setOnClickListener {
            binding2.searchView.isIconified = false
        }

        binding2.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    }


    private fun initRV(){

        binding2.rcView.apply {

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)

    }

}