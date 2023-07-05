package store.devshcherbinavv.cinemasearch.data

import store.devshcherbinavv.cinemasearch.R
import store.devshcherbinavv.cinemasearch.domain.Film

class MainRepository {
     val filmsDataBase = listOf(
        Film("John Wick: Chapter 4", R.drawable.john,"John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",7.5f),
        Film("Avatar", R.drawable.avatar,"JA paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.", 3.0f),
        Film("Indiana Jones And The Dial of Destiny", R.drawable.indiana,"The plot is unknown at this time.", 9.0f),
        Film("Batman", R.drawable.batman,"When a sadistic serial killer begins murdering key political figures in Gotham, Batman is forced to investigate the city's hidden corruption and question his family's involvement.", 8.2f),
        Film("Flash", R.drawable.flash,"Barry Allen uses his super speed to change the past, but his attempt to save his family creates a world without super heroes, forcing him to race for his life in order to save the future.", 5.3f),
        Film("Sonic the Hedgehog 2", R.drawable.sonic,"When the manic Dr Robotnik returns to Earth with a new ally, Knuckles the Echidna, Sonic and his new friend Tails is all that stands in their way.", 7.5f),
        Film("Shazam! Fury of the Gods", R.drawable.shazam,"The film continues the story of teenage Billy Batson who, upon reciting the magic word \"SHAZAM!\" is transformed into his adult Super Hero alter ego, Shazam.",4.3f)
    )
}