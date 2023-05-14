package store.devshcherbinavv.cinemasearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import store.devshcherbinavv.cinemasearch.databinding.ActivityMainBinding



@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val filmsDataBase = listOf(
        Film("John Wick: Chapter 4", R.drawable.john,"John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes."),
        Film("Avatar", R.drawable.avatar,"JA paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home."),
        Film("Indiana Jones And The Dial of Destiny", R.drawable.indiana,"The plot is unknown at this time."),
        Film("Batman", R.drawable.batman,"When a sadistic serial killer begins murdering key political figures in Gotham, Batman is forced to investigate the city's hidden corruption and question his family's involvement."),
        Film("Flash", R.drawable.flash,"Barry Allen uses his super speed to change the past, but his attempt to save his family creates a world without super heroes, forcing him to race for his life in order to save the future."),
        Film("Sonic the Hedgehog 2", R.drawable.sonic,"When the manic Dr Robotnik returns to Earth with a new ally, Knuckles the Echidna, Sonic and his new friend Tails is all that stands in their way."),
        Film("Shazam! Fury of the Gods", R.drawable.shazam,"The film continues the story of teenage Billy Batson who, upon reciting the magic word \"SHAZAM!\" is transformed into his adult Super Hero alter ego, Shazam.")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment(filmsDataBase))
            .addToBackStack(null)
            .commit()

   }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.alertdialog_title))
                .setIcon(android.R.drawable.presence_video_away)
                .setPositiveButton(getString(R.string.alertdialog_positive)) { _, _ -> finish() }
                .setNegativeButton(getString(R.string.alertdialog_negative)) { _, _ -> }
                .show()
        } else {
            super.onBackPressed()

        }
    }
    private fun initNavigation(){

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, getString(R.string.settings_text), Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigation.setOnItemSelectedListener(){

            when (it.itemId) {
                 R.id.favorites-> {
                     supportFragmentManager
                         .beginTransaction()
                         .replace(R.id.fragment_placeholder, FavoritesFragment(filmsDataBase))
                         .addToBackStack(null)
                         .commit()
                    Toast.makeText(this, getString(R.string.favorite_text), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, getString(R.string.later_text), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, getString(R.string.selections_text), Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

}




