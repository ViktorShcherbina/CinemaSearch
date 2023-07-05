package store.devshcherbinavv.cinemasearch.view.rv_viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import store.devshcherbinavv.cinemasearch.R
import store.devshcherbinavv.cinemasearch.databinding.FilmItemBinding
import store.devshcherbinavv.cinemasearch.domain.Film

class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val filmBinding = FilmItemBinding.bind(itemView)
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val poster = itemView.findViewById<ImageView>(R.id.poster)
    private val description = itemView.findViewById<TextView>(R.id.description)
    //Вот здесь мы находим в верстке наш прогресс бар для рейтинга
    private val ratingDonut = filmBinding.ratingDonut

    fun bind(film: Film){
        title.text = film.title
        Glide.with(itemView)
            .load(film.poster)
            .centerCrop()
            .into(poster)
        description.text = film.description
        //Устанавливаем рэйтинг
        ratingDonut.setProgress((film.rating * 10).toInt())

    }
}