package store.devshcherbinavv.cinemasearch.view.notifications

import android.Manifest
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import store.devshcherbinavv.cinemasearch.R
import store.devshcherbinavv.cinemasearch.data.ApiConstants
import store.devshcherbinavv.cinemasearch.data.entity.Film
import store.devshcherbinavv.cinemasearch.receivers.ReminderBroadcast
import store.devshcherbinavv.cinemasearch.view.MainActivity
import java.util.*


object NotificationHelper {
    fun createNotification(context: Context, film: Film){

        val mIntent = Intent(context, MainActivity::class.java)
        mIntent.putExtra("fragment","fragmentTag")

//        val taskStackBuilder = TaskStackBuilder.create(context)
//        taskStackBuilder.addParentStack(DetailsFragment::class.java)
//        taskStackBuilder.addNextIntent(mIntent)


        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                    context,
                0,
                mIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or  PendingIntent.FLAG_IMMUTABLE
                )




        }else {
            PendingIntent.getActivity(
                context,
                0,
                mIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(context!!, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_outline_watch_later_24)
            setContentTitle("Не забудьте посмотреть!")
            setContentText(film.title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)

        Glide.with(context)
            //говорим что нужен битмап
            .asBitmap()
            //указываем откуда загружать, это ссылка как на загрузку с API
            .load(ApiConstants.IMAGES_URL + "w500" + film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                //Этот коллбэк отрабатоет когда мы успешно получим битмап
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    //Создаем нотификации в стиле big picture
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    //Обновляем нотификацю
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling

                        return
                    }
                    notificationManager.notify(film.id, builder.build())
                }
            })
        //Отправляем изначальную нотификацю в стандартном исполнении
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling

            return
        }
        notificationManager.notify(film.id, builder.build())
    }

    fun notificationSet(context: Context, film: Film) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(
            context,
            { _, dpdYear, dpdMonth, dayOfMonth ->
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, pickerMinute ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(
                            dpdYear,
                            dpdMonth,
                            dayOfMonth,
                            hourOfDay,
                            pickerMinute,
                            0
                        )
                        val dateTimeInMillis = pickedDateTime.timeInMillis
                        //После того, как получим время, вызываем метод, который создаст Alarm
                        createWatchLaterEvent(context, dateTimeInMillis, film)
                    }

                TimePickerDialog(
                    context,
                    timeSetListener,
                    currentHour,
                    currentMinute,
                    true
                ).show()

            },
            currentYear,
            currentMonth,
            currentDay
        ).show()
    }

    private fun createWatchLaterEvent(context: Context, dateTimeInMillis: Long, film: Film) {
        //Получаем доступ к AlarmManager
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //Создаем интент для запуска ресивера
        val intent = Intent(film.title, null, context, ReminderBroadcast()::class.java)
        //Кладем в него фильм
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.FILM_KEY, film)
        intent.putExtra(NotificationConstants.FILM_BUNDLE_KEY, bundle)
        //Создаем пендинг интент для запуска извне приложения
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        //Устанавливаем Alarm
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            dateTimeInMillis,
            pendingIntent
        )
    }

}

