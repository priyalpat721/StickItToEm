package edu.neu.madcourse.stickittoem.messages

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import edu.neu.madcourse.stickittoem.MainActivity
import edu.neu.madcourse.stickittoem.R
import kotlin.random.Random

class PushNotificationService : FirebaseMessagingService() {

    companion object {
        var sharedPreferences : SharedPreferences? = null
    }

    var context: Context = this
    lateinit var currentToken : String
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val intent = Intent(this, StickerMessagingActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // show multiple notifications
        val notificationID = Random.nextInt()
        // makes activity on top of the stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // pending intent can only be used once
        val replyIntent = PendingIntent.getActivity(
            this, System.currentTimeMillis().toInt(),
            Intent(this, MainActivity::class.java), FLAG_IMMUTABLE
        )
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE)

        var path = R.drawable.exercisedino
        when (message.data["image"]) {
            "exercisedino" -> {
                path = R.drawable.exercisedino
            }
            "frustratedino" -> {
                path = R.drawable.frustratedino
            }
            "happydino" -> {
                path = R.drawable.happydino
            }
            "motivatedino" -> {
                path = R.drawable.motivatedino
            }
            "saddino" -> {
                path = R.drawable.saddino
            }
            "sleepdino2" -> {
                path = R.drawable.sleepdino2
            }

        }

        val icon : Bitmap = BitmapFactory.decodeResource(context.resources, path)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(path)
            .setLargeIcon(icon)
            //high priority for messages
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(path, "OPEN APP", replyIntent)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(notificationID,notification)



        val messageReceived : String = message.data["message"]!!
        Log.d("TAG", "Message received: $messageReceived")
        createNotificationChannel()
    }

    override fun onNewToken(token: String) {
        Log.d("TAG", "The token refreshed: $token")
        super.onNewToken(token)
        currentToken = token
        // need to save this token somewhere
    }

    private val channelId = "channel ID"
    private fun createNotificationChannel() {
        val name = "channel name"
        val descriptionText = "notification channel description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        // register channel
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}