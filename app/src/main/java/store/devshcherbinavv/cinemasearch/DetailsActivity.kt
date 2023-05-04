package store.devshcherbinavv.cinemasearch

import androidx.appcompat.app.AppCompatActivity
import store.devshcherbinavv.cinemasearch.databinding.ActivityDetailsBinding
import android.os.Bundle
import store.devshcherbinavv.cinemasearch.databinding.ActivityMainBinding


class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFilmsDetails()
    }

    private fun setFilmsDetails() {

          val film = intent.extras?.get("film") as Film
        binding.detailsToolbar.title = film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description
    }
}