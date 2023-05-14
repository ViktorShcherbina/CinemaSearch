package store.devshcherbinavv.cinemasearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import store.devshcherbinavv.cinemasearch.databinding.FragmentHomeBinding
import kotlin.collections.ArrayList

class HomeFragment(private val filmsDataBase : List <Film>) : Fragment() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()

    }

    private fun initRV(){

        binding.rcView.apply {

            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)
        updateData(newList = ArrayList(filmsDataBase))
    }

    private fun updateData(newList: ArrayList<Film>) {
        val filmDiff = FilmDiff(oldList = ArrayList(filmsDataBase), newList)
        val diffResult = DiffUtil.calculateDiff(filmDiff)
        filmsAdapter.addItems(newList)
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }

}