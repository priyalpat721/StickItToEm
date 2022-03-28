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
import edu.neu.madcourse.stickittoem.R
import kotlin.random.Random

class PushNotificationService : FirebaseMessagingService() {

    companion object {
        var sharedPreferences : SharedPreferences? = null
        var token : String?
        get() {
            return sharedPreferences?.getString("token", "")
        }
        set(value) {
            sharedPreferences?.edit()?.putString("token", value)?.apply()
        }
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
            Intent(this, StickerMessagingActivity::class.java), FLAG_IMMUTABLE
        )
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE)
        var icon : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.exercisedino)
        println("Message message data: " + message.data["message"])
        println("Message title data: " + message.data["title"])
        println("Message image data: " + message.data["image"])
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.exercisedino)
            .setLargeIcon(icon)
            //high priority for messages
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.drawable.exercisedino, "Reply", replyIntent)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(notificationID,notification)



        val messageReceived : String = message.data["message"]!!
        Log.d("TAG", "Message received: $messageReceived")
        // passMessageToActivity(messageReceived)
        //passMessageToActivity(currentToken)
        createNotificationChannel()
    }

    private fun passMessageToActivity(messageReceived: String) {
        val intent : Intent = Intent().apply {
            action = "INTENT_ACTION_SEND_MESSAGE"
            putExtra("message", messageReceived)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }



    override fun onNewToken(token: String) {
        Log.d("TAG", "The token refreshed: $token")
        super.onNewToken(token)
        currentToken = token
        // need to save this token somewhere
        //passTokenToActivity(token)


    }
    private fun passTokenToActivity(token: String) {
        val intent : Intent = Intent().apply {
            action = "INTENT_ACTION_SEND_MESSAGE"
            putExtra("token", token)
        }
        //startActivity(intent)
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
        /*val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.exercisedino)
            /*.setLargeIcon(
                BitmapFactory.decodeResource(
                    context.getResources(),
                    R.drawable.exercisedino
                )
            )*/
            .setContentTitle("TEST")
            .setContentText("TEST NOTIFICATION")
            //high priority for messages
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        //.addAction(R.drawable.exercisedino, "Reply", replyIntent)
        //.setContentIntent(pIntent)
        with(NotificationManagerCompat.from(this)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }*/
    }


    /*override fun onCreate() {
        passTokenToActivity(currentToken)
        super.onCreate()
    }*/
}