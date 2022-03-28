package edu.neu.madcourse.stickittoem.messages

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.StickerMessagingAdapter
import edu.neu.madcourse.stickittoem.cards.StickerCard
import java.util.*

class StickerMessagingActivity : AppCompatActivity() {
    private lateinit var receiverName: String
    private lateinit var sender: String
    private lateinit var receiver: String
    private val TAG = "StickerAppMessage"
    private var stickerMessageList: ArrayList<StickerCard> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: StickerMessagingAdapter? = null
    var context: Context = this
    private lateinit var stickerDisplayButton: Button
    private lateinit var sendButton: ImageButton
    private lateinit var nameBox: TextView
    private var receiverId: String? = null
    private var senderId = Firebase.auth.currentUser?.uid!!
    private var stringStickerImg: String? = null
    private var stickerImage: Int? = null
    private var stickerDescription: String? = null
    private val sorter = ComparatorTime()
    private var db = Firebase.database.reference
    private var stickerIDMap = HashMap<Int, String>()

    private val SERVER_KEY: String = "key = AAAAmT9eZxc:APA91bEUzh4cD0qqeNqzvMQv4EScFoTOcwBllfKVMjuPHWPkD5F8EVng6wE3UGxrpVAapsD336oGzp6dNUuK3rMYb1ZY7AQIjp0wo0cZEhAujwlnukmXTQQVQMoZ-vLaa6Zrq0GbY0xF"
    private val CLIENT_REGISTRATION_TOKEN: String? = null

    @ServerTimestamp
    lateinit var time: Timestamp

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        stickerIDMap[R.drawable.exercisedino] = "exercisedino"
        stickerIDMap[R.drawable.frustratedino] = "frustratedino"
        stickerIDMap[R.drawable.happydino] = "happydino"
        stickerIDMap[R.drawable.motivatedino] = "motivatedino"
        stickerIDMap[R.drawable.saddino] = "saddino"
        stickerIDMap[R.drawable.sleepdino2] = "sleepdino2"

        stickerDisplayButton = findViewById(R.id.sticker_btn)
        val bottomStickerSheetDialog = BottomStickerSheetDialog()
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                //do something based on the intent's action
                stickerImage = intent.getIntExtra("image", 0)
                stringStickerImg = stickerIDMap[stickerImage]
                stickerDescription = intent?.getStringExtra("description")
                receiver = intent?.getStringExtra("receiver").toString()
                sender = intent?.getStringExtra("sender").toString()
                receiverName = intent?.getStringExtra("name").toString()
            }
        }

        val filter = IntentFilter("com.stickerclicked.stickerInformation")
        registerReceiver(broadcast, filter)
        getIds()
        setUpResources()

        listenForChanges()
        adapter?.notifyDataSetChanged()

        stickerDisplayButton.setOnClickListener {
            bottomStickerSheetDialog.name = receiverName
            bottomStickerSheetDialog.receiver = receiver
            bottomStickerSheetDialog.sender = senderId
            bottomStickerSheetDialog.show(supportFragmentManager, "sticker sheet")

        }

        sendButton = findViewById(R.id.send_btn)
        sendButton.setOnClickListener {

            addToDB()
            adapter?.notifyDataSetChanged()

            stringStickerImg?.let { it1 ->

                db.child("users").child(senderId).child("totalSent").child(it1)
                    .runTransaction(object : Transaction.Handler {
                        override fun doTransaction(currentData: MutableData): Transaction.Result {
                            val currentValue = currentData.value
                            val newValue = currentValue.toString()
                            val longVal = newValue.toLong() + 1

                            currentData.value = longVal
                            return Transaction.success(currentData)

                        }

                        override fun onComplete(
                            error: DatabaseError?,
                            committed: Boolean,
                            currentData: DataSnapshot?
                        ) {

                        }

                    })
            }
            Toast.makeText(context, "$stickerDescription sticker sent", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun listenForChanges() {
        stickerMessageList.clear()
        db.child("chatLog").child("$senderId-$receiver").child("messages")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    stickerMessageList.clear()
                    for (snap in snapshot.children) {
                        Log.i(TAG, "Stickerlist before: $snap")
                        val sticker = snap.child("sticker").getValue(String::class.java)
                        val timestamp = snap.child("timestamp").getValue(String::class.java)
                        val sender = snap.child("sender").getValue(String::class.java)
                        val receiver = snap.child("receiver").getValue(String::class.java)
                        val message =
                            StickerCard(sticker, timestamp!!, sender!!, receiver!!)
                        stickerMessageList.add(message)
                        adapter?.notifyDataSetChanged()
                        recyclerView?.smoothScrollToPosition(stickerMessageList.size)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // not implemented
                }
            })
        Collections.sort(stickerMessageList, sorter)
        stickerMessageList.reverse()
        adapter?.notifyDataSetChanged()
    }


    class ComparatorTime : Comparator<StickerCard> {
        override fun compare(a: StickerCard, b: StickerCard): Int {
            return a.timestamp.compareTo(b.timestamp)
        }
    }


    private fun getIds() {
        val extras = intent.extras
        if (extras != null) {
            receiverName = intent.getStringExtra("name").toString()
            receiverId = intent.getStringExtra("receiverId").toString()
            receiver = receiverId.toString()

            Log.i(TAG, extras.toString())
            nameBox = findViewById(R.id.receiver_name_box)
            nameBox.text = receiverName
        }
    }

    private fun setUpResources() {
        recyclerView = findViewById(R.id.message_recycler_view)

        adapter = StickerMessagingAdapter(stickerMessageList, context)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addToDB() {
        time = Timestamp.now()
        val newMessage = StickerCard(stringStickerImg, time.toDate().toString(), sender, receiver)

        db.child("chatLog").child("$sender-$receiver").child("messages")
            .push().setValue(newMessage)

        db.child("chatLog").child("$receiver-$sender").child("messages")
            .push().setValue(newMessage)
        adapter?.notifyDataSetChanged()
    }

    // TODO pull the token from the message receiver from the database

//    public fun sendNotificationToDevice(view : View) {
//        val t = Thread()
//        Thread {
//            if (CLIENT_REGISTRATION_TOKEN != null) {
//                sendMessageToDevice(CLIENT_REGISTRATION_TOKEN)
//            }
//        }
//        t.start()
//    }

//    public fun sendMessageToDevice(token : String) {
//        // Prepare data
//
//
//        // Prepare data
//        val jPayload = JSONObject()
//        val jNotification = JSONObject()
//        val jdata = JSONObject()
//        try {
//            jNotification.put("title", "Message Title from 'SEND MESSAGE TO CLIENT BUTTON'")
//            jNotification.put("body", "Message body from 'SEND MESSAGE TO CLIENT BUTTON'")
//            jNotification.put("sound", "default")
//            jNotification.put("badge", "1")
//            /*
//            // We can add more details into the notification if we want.
//            // We happen to be ignoring them for this demo.
//            jNotification.put("click_action", "OPEN_ACTIVITY_1");
//            */jdata.put("title", "data title from 'SEND MESSAGE TO CLIENT BUTTON'")
//            jdata.put("content", "data content from 'SEND MESSAGE TO CLIENT BUTTON'")
//            /***
//             * The Notification object is now populated.
//             * Next, build the Payload that we send to the server.
//             */
//
//            // If sending to a single client
//            jPayload.put("to", token) // CLIENT_REGISTRATION_TOKEN);
//
//            jPayload.put("priority", "high")
//            jPayload.put("notification", jNotification)
//            jPayload.put("data", jdata)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//
//        try {
//
//            // Open the HTTP connection and send the payload
//            val url = URL("https://fcm.googleapis.com/fcm/send")
//            val conn = url.openConnection() as HttpURLConnection
//            conn.requestMethod = "POST"
//            conn.setRequestProperty("Content-Type", "application/json")
//            conn.setRequestProperty("Authorization", token)
//            conn.doOutput = true
//
//            // Send FCM message content.
//            val outputStream = conn.outputStream
//            outputStream.write(jsonObject.toString().toByteArray())
//            outputStream.close()
//
//            // Read FCM response.
//            val inputStream = conn.inputStream
//            edu.neu.madcourse.firebasedemo.utils.Utils.convertStreamToString(inputStream)
//        } catch (e: IOException) {
//            "NULL"
//        }
//        val resp: String = Utils.fcmHttpConnection(
//            edu.neu.madcourse.firebasedemo.fcm.FCMActivity.SERVER_KEY,
//            jPayload
//        )
//        Utils.postToastMessage("Status from Server: $resp", applicationContext)
//
//    }

}
