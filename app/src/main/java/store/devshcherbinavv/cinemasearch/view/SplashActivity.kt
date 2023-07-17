package store.devshcherbinavv.cinemasearch.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import store.devshcherbinavv.cinemasearch.databinding.ActivitySplashBinding


class SplashScreenActivity : AppCompatActivity() {
private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottieAnim.alpha = 0f
        binding.lottieAnim.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}