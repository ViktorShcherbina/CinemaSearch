package store.devshcherbina.cinemasearch.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import store.devshcherbinavv.cinemasearch.R
import store.devshcherbinavv.cinemasearch.databinding.ActivityMainBinding
import store.devshcherbinavv.cinemasearch.data.entity.Film
import store.devshcherbinavv.cinemasearch.receivers.ConnectionChecker
import store.devshcherbinavv.cinemasearch.view.*
import store.devshcherbinavv.cinemasearch.view.fragments.*




@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: BroadcastReceiver


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
        receiver = ConnectionChecker()
        val filters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(receiver, filters)

        val mIntent = Intent(this, MainActivity::class.java)
        if (mIntent.hasExtra("fragment")){
            val fragmentTag = mIntent.getStringExtra("fragmentTag")
            if (fragmentTag != null){
                val fragment = DetailsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.details_fragment,fragment,fragmentTag).commit()
            }
        }




    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
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

    fun launchFavoritesFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = FavoritesFragment()
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
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: HomeFragment(), tag)
                    Toast.makeText(this, getString(R.string.menu_home_title_text), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.favorites -> {
                    Toast.makeText(this, "Доступно в Pro версии", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: WatchLaterFragment(), tag)
                    Toast.makeText(this, getString(R.string.later_text), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, "Доступно в Pro версии", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SettingsFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    private fun checkFragmentExistence(tag: String) : Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()

    }

}
