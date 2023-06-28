package store.devshcherbinavv.cinemasearch.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import store.devshcherbinavv.cinemasearch.R
import store.devshcherbinavv.cinemasearch.domain.Film
import store.devshcherbinavv.cinemasearch.view.rv_viewholders.FilmViewHolder


class FilmListRecyclerAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Film>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
                holder.filmBinding.itemContainer.setOnClickListener {
                    clickListener.click(items[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(list: List<Film>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun click(film: Film)
    }

}