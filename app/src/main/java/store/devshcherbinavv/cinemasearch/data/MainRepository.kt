package store.devshcherbinavv.cinemasearch.data

import android.content.ContentValues
import android.database.Cursor
import store.devshcherbinavv.cinemasearch.R
import store.devshcherbinavv.cinemasearch.data.db.DatabaseHelper
import store.devshcherbinavv.cinemasearch.domain.Film

class MainRepository (databaseHelper: DatabaseHelper){
    //Инициализируем объект для взаимодействия с БД
    private val sqlDb = databaseHelper.readableDatabase
    //Создаем курсор для обработки запросов из БД
    private lateinit var cursor: Cursor

    fun putToDb(film: Film) {
        //Создаем объект, который будет хранить пары ключ-значение, для того
        //чтобы класть нужные данные в нужные столбцы
        val cv = ContentValues()
        cv.apply {
            put(DatabaseHelper.COLUMN_TITLE, film.title)
            put(DatabaseHelper.COLUMN_POSTER, film.poster)
            put(DatabaseHelper.COLUMN_DESCRIPTION, film.description)
            put(DatabaseHelper.COLUMN_RATING, film.rating)
        }
        //Кладем фильм в БД
        sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)
    }
//       Методы обновления и удаления в БД закоментировать!!!
    fun updateDb(film: Film) {
        val cv = ContentValues()
        cv.put(DatabaseHelper.COLUMN_TITLE, "Винни-Пух и все, все, все")
        //Обновляем БД
        sqlDb.update(DatabaseHelper.TABLE_NAME, cv,DatabaseHelper.COLUMN_ID + "=" + 1, null)

    }

    fun deleteDb(film: Film) {
        sqlDb.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.COLUMN_TITLE + "=" + "?",arrayOf(film.title))
    }


    fun getAllFromDB(): List<Film> {
        //Создаем курсор на основании запроса "Получить все из таблицы"
        cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        //Сюда будем сохранять результат получения данных
        val result = mutableListOf<Film>()
        //Проверяем, есть ли хоть одна строка в ответе на запрос
        if (cursor.moveToFirst()) {
            //Итерируемся по таблице, пока есть записи, и создаем на основании объект Film
            do {
                val title = cursor.getString(1)
                val poster = cursor.getString(2)
                val description = cursor.getString(3)
                val rating = cursor.getDouble(4)

                result.add(Film(title, poster, description, rating))
            } while (cursor.moveToNext())
        }
        //Возвращаем список фильмов
        return result
    }

}