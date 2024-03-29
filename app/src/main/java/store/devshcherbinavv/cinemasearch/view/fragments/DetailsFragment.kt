package store.devshcherbinavv.cinemasearch.view.fragments

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar.*
import kotlinx.coroutines.*
import store.devshcherbinavv.cinemasearch.R
import store.devshcherbinavv.cinemasearch.data.ApiConstants
import store.devshcherbinavv.cinemasearch.data.entity.Film
import store.devshcherbinavv.cinemasearch.databinding.FragmentDetailsBinding
import store.devshcherbinavv.cinemasearch.view.notifications.NotificationHelper
import store.devshcherbinavv.cinemasearch.viewmodel.DetailsFragmentViewModel


@Suppress("DEPRECATION")
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var film: Film
    private val viewModel: DetailsFragmentViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.IO)


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
        binding.detailsFabDownloadWp.setOnClickListener {
            performAsyncLoadOfPoster()
        }
        binding.detailsFabWatchLater.setOnClickListener {
            NotificationHelper.notificationSet(requireContext(), film)

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

    private fun performAsyncLoadOfPoster() {
        //Проверяем есть ли разрешение
        if (!checkPermission()) {
            //Если нет, то запрашиваем и выходим из метода
            requestPermission()
            return
        }
        //Создаем родительский скоуп с диспатчером Main потока, так как будет взаимодействовать с UI
        MainScope().launch {
            //Включаем прогресс бар
            binding.progressBar.isVisible = true
            //Создаем через async так как нам нужен результат от работы, то есть Bitmap
            val job = scope.async {
                viewModel.loadWallpaper(ApiConstants.IMAGES_URL + "original" + film.poster)
            }
            //Сохраняем в галерею, как только файл загрузится
            saveToGallery(job.await())
            //Выводим снекбар с кнопкой перейти в галерею
            make(
                binding.root,
                R.string.downloaded_to_gallery,
                LENGTH_LONG
            )
                .setAction(R.string.open) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.type = "image/*"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    try {
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {

                    }

                }
                .show()

            //Отключаем прогресс бар
            binding.progressBar.isVisible = false
        }
    }

    //Узнаем, было ли получено разрешение ранее
    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }
    //Запрашиваем разрешение
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    private fun saveToGallery(bitmap: Bitmap) {
        //Проверяем версию системы
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Создаем объект для передачи данных
            val contentValues = ContentValues().apply {
                //Составляем информацию для файла(имя, тип, дата создания, куда сохранять и т.д.)
                put(MediaStore.Images.Media.TITLE, film.title.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    film.title.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CinemaSearch")
            }
            //Получаем ссылку на объект Content resolver, которые помогает передвать информацию из приложения во вне
            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            //Открываем канал для записи на диск
            val outputStream = contentResolver.openOutputStream(uri!!)
            //Передаем нашу картинку, может сделать компрессию
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!)
            //Закрываем поток
            outputStream?.close()
        } else {
            //Тоже, но для более старых версий ОС
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                film.title.handleSingleQuote(),
                film.description.handleSingleQuote()
            )
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

}