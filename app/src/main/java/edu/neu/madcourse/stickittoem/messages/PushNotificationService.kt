package edu.neu.madcourse.stickittoem.messages

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import edu.neu.madcourse.stickittoem.R

class PushNotificationService : FirebaseMessagingService() {
    /*lateinit var currentToken : String
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val messageReceived : String = message.data["message"]!!
        Log.d("TAG", "Message received: $messageReceived")
        passMessageToActivity(messageReceived)
        createNotificationChannel()
    }

    private fun passMessageToActivity(messageReceived: String) {
        val intent : Intent = Intent().apply {
            action = INTENT_ACTION_SEND_MESSAGE
            putExtra("message", messageReceived)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }*/

    companion object {
        const val INTENT_ACTION_SEND_MESSAGE = "INTENT_ACTION_SEND_MESSAGE"
    }

    override fun onNewToken(token: String) {
        Log.d("TAG", "The token refreshed: $token")
        //super.onNewToken(token)
        // need to save this token somewhere
        passTokenToActivity(token)
    }
    private fun passTokenToActivity(token: String) {
        val intent : Intent = Intent().apply {
            action = INTENT_ACTION_SEND_MESSAGE
            putExtra("token", token)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
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
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.exercisedino)
            /*.setLargeIcon(
                BitmapFactory.decodeResource(
                    context.getResources(),
                    R.drawable.exercisedino
                )
            )*/
            .setContentTitle("Sender name")
            .setContentText("New sticker Received!")
            //high priority for messages
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        //.addAction(R.drawable.exercisedino, "Reply", replyIntent)
        //.setContentIntent(pIntent)
        with(NotificationManagerCompat.from(this)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}