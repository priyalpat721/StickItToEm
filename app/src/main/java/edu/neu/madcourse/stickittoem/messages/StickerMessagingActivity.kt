package edu.neu.madcourse.stickittoem.messages

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.MainActivity
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
    private var fireStore = FirebaseFirestore.getInstance()
    private val sorter = ComparatorTime()
    private var db = Firebase.database.reference

    @ServerTimestamp
    lateinit var time: Timestamp

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        createNotificationChannel()
        getIds()
        setUpResources()

        getData()
        listenForChanges()
        adapter?.notifyDataSetChanged()

        stickerDisplayButton = findViewById(R.id.sticker_btn)
        val bottomStickerSheetDialog = BottomStickerSheetDialog()
        stickerDisplayButton.setOnClickListener {
            bottomStickerSheetDialog.name = receiverName
            bottomStickerSheetDialog.receiver = receiver
            bottomStickerSheetDialog.sender = senderId
            bottomStickerSheetDialog.show(supportFragmentManager, "sticker sheet")

            val stickerIntent = intent.extras
            if (stickerIntent != null) {
                stickerImage = stickerIntent.getInt("image")
                stickerDescription = stickerIntent.getString("description")
                receiver = stickerIntent.getString("receiver").toString()
                sender = stickerIntent.getString("sender").toString()
                receiverName = stickerIntent.getString("name").toString()

                Log.i(TAG, "UPDATED: $stickerIntent")
            }
        }

        sendButton = findViewById(R.id.send_btn)
        sendButton.setOnClickListener {
            sendNotification()
            val stickerIntent = intent.extras

            stickerImage = stickerIntent?.getInt("image")
            when (stickerImage) {
                2131165311 -> {
                    stringStickerImg = "exercisedino"
                }
                2131165312 -> {
                    stringStickerImg = "frustratedino"
                }
                2131165317 -> {
                    stringStickerImg = "happydino"
                }
                2131165351 -> {
                    stringStickerImg = "motivatedino"
                }
                2131165375 -> {
                    stringStickerImg = "saddino"
                }
                2131165377 -> {
                    stringStickerImg = "sleepdino2"
                }
            }


            stickerDescription = stickerIntent?.getString("description")
            receiver = stickerIntent?.getString("receiver").toString()
            sender = stickerIntent?.getString("sender").toString()
            receiverName = stickerIntent?.getString("name").toString()
            addToDB()

            getData()
            adapter?.notifyDataSetChanged()

            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)


        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        db.child("chatLog").child("$senderId-$receiver").child("messages")
            .get().addOnSuccessListener { result ->
                Log.i(TAG, "Stickerlist before: $result")
//                val sticker = result.child("sticker").getValue(String::class.java)
//                val timestamp = result.child("timestamp").getValue(String::class.java)
//                val sender = result.child("sender").getValue(String::class.java)
//                val receiver = result.child("receiver").getValue(String::class.java)
//                val message =
//                    StickerCard(sticker, timestamp!!, sender!!, receiver!!)
//                stickerMessageList.add(message)
            }


        Collections.sort(stickerMessageList, sorter)
        stickerMessageList.reverse()
        adapter?.notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun listenForChanges() {
        stickerMessageList.clear()
        db.child("chatLog").child("$senderId-$receiver").child("messages")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {
                        Log.i(TAG, "Stickerlist before: $snap")
                        val sticker = snap.child("sticker").getValue(String::class.java)
                        val timestamp = snap.child("timestamp").getValue(String::class.java)
                        val sender = snap.child("sender").getValue(String::class.java)
                        val receiver = snap.child("receiver").getValue(String::class.java)
                        val message =
                            StickerCard(sticker, timestamp!!, sender!!, receiver!!)
                        stickerMessageList.add(message)
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
    }

    private val channelId = "channel ID"
    private fun createNotificationChannel() {
        val name = "channel name"
        val descriptionText = "notification channel description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText}
        // register channel
        val notificationManager : NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    private fun sendNotification() {
        // Prepare intent which is triggered if the
        // notification is selected
        val intent = Intent(this, StickerMessagingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pIntent : PendingIntent = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
        val replyIntent = PendingIntent.getActivity(
            this, System.currentTimeMillis().toInt(),
            Intent(this, StickerMessagingActivity::class.java), PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.exercisedino)
            .setContentTitle("Sender name" + "New Sticker!")
            .setContentText("New sticker Received!")
                //high priority for messages
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.drawable.exercisedino, "Reply", replyIntent)
            .setContentIntent(pIntent)
        //val notificationManager = NotificationManagerCompat.from(this)
        // define ID
        //notificationManager.notify(System.currentTimeMillis().toInt(), notifyBuild.build())
        with(NotificationManagerCompat.from(this)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}