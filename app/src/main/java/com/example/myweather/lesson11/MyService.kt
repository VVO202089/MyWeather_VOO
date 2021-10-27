package com.example.myweather.lesson11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myweather.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyService : FirebaseMessagingService() {

    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"
        private const val CHANNEL_ID_1 = "chanel_id_1"
        private const val CHANNEL_ID_2 = "chanel_id_2"
        private const val CHANNEL_ID_3 = "chanel_id_3"
        private const val NOTIFICATION_ID_1 = 1
        private const val NOTIFICATION_ID_2 = 2
        private const val NOTIFICATION_ID_3 = 3
    }


    //получение сообщения
    override fun onMessageReceived(p0: RemoteMessage) {

        val remoteMessageData = p0.data

        if (remoteMessageData.isNotEmpty()) {
            val title = remoteMessageData[PUSH_KEY_TITLE]
            val message = remoteMessageData[PUSH_KEY_MESSAGE]
            // если данные корректны
            if (!title.isNullOrBlank() && !message.isNullOrBlank()){
                pushNotification(title,message)
            }
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        // Отправили на сервер

    }

    private fun pushNotification(title: String, message: String) {
        // push - уведомления
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // строим 3 нотификации
        val notificationBuilder_1 = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
            setContentTitle(title)
            setContentText(message)
            setSmallIcon(R.drawable.ic_earth)
            priority = NotificationCompat.PRIORITY_MAX
        }

        // проверяем версию SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // используем каналы
            // создаем первый канал
            val nameChannel_1 = "Name ${CHANNEL_ID_1}"
            val descChannel_1 = "Description ${CHANNEL_ID_1}"
            val impotanceChannel_1 = NotificationManager.IMPORTANCE_MIN
            val channel_1 =
                NotificationChannel(CHANNEL_ID_1, nameChannel_1, impotanceChannel_1).apply {
                    description = descChannel_1
                }
            // само создание
            notificationManager.createNotificationChannel(channel_1)
        }
        notificationManager.notify(NOTIFICATION_ID_1, notificationBuilder_1.build())

    }
}