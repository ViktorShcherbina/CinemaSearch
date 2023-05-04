package store.devshcherbinavv.cinemasearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import store.devshcherbinavv.cinemasearch.databinding.FilmItemBinding


class FilmListRecyclerAdapter(private val clickListener: OnItemClickListener) : ListAdapter<Film,RecyclerView.ViewHolder>(ItemComparator()) {

    class FilmViewHolder(private val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) = with(binding){
            title.text = film.title
            poster.setImageResource(film.poster)
            description.text = film.description
        }
        companion object {
            fun create(parent: ViewGroup) : FilmViewHolder {
                return FilmViewHolder(FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }

    }
    private val items = mutableListOf<Film>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])

                holder.itemView.setOnClickListener {
                    clickListener.click(items[position])
                }

            }
        }
    }

    fun addItems(list: List<Film>) {
//        items.clear()
        items.addAll(list)
//        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun click(film: Film)
    }
}