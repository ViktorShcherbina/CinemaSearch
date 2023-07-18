package store.devshcherbinavv.cinemasearch.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar.*
import store.devshcherbinavv.cinemasearch.data.ApiConstants
import store.devshcherbinavv.cinemasearch.R
import store.devshcherbinavv.cinemasearch.databinding.FragmentDetailsBinding
import store.devshcherbinavv.cinemasearch.domain.Film


@Suppress("DEPRECATION")
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var film: Film


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmsDetails()

        binding.detailsFabFavorites.setOnClickListener {
            val snackFavorites = make(
                binding.detailsFabFavorites,getString(R.string.snack_favorites),
                LENGTH_SHORT
            )
            val snackFavoritesDeleted = make(
                binding.detailsFabFavorites,
                getString(R.string.snack_favorites_deleted),
                LENGTH_SHORT
            )
            if (!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = true
                snackFavorites.show()
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = false
                snackFavoritesDeleted.show()
            }
        }
        binding.detailsFab.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share to:"))
        }
    }

    private fun setFilmsDetails() {

           film = arguments?.get("film") as Film
        binding.detailsToolbar.title = film.title
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)

        binding.detailsDescription.text = film.description

        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) {
                R.drawable.ic_baseline_favorite_border_24
            } else {
                R.drawable.ic_baseline_favorite_24
            }
        )
    }
}