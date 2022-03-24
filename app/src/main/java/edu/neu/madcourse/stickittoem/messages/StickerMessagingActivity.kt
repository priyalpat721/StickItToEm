package edu.neu.madcourse.stickittoem.messages

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
    private var senderId: String? = null
    private var stringStickerImg: String? = null
    private var stickerImage: Int? = null
    private var stickerDescription: String? = null
    private var fireStore = FirebaseFirestore.getInstance()
    private val sorter = ComparatorTime()

    @ServerTimestamp
    lateinit var time: Timestamp

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        getIds()
        setUpResources()

        getData()
        adapter?.notifyDataSetChanged()

        stickerDisplayButton = findViewById(R.id.sticker_btn)
        val bottomStickerSheetDialog = BottomStickerSheetDialog()
        stickerDisplayButton.setOnClickListener {
            bottomStickerSheetDialog.name = receiverName
            bottomStickerSheetDialog.receiver = receiver
            bottomStickerSheetDialog.sender = sender
            bottomStickerSheetDialog.show(supportFragmentManager, "sticker sheet")

            val stickerIntent = intent.extras
            if (stickerIntent != null) {
                stickerImage = stickerIntent.getInt("image")
                stickerDescription = stickerIntent.getString("description")
                receiver = stickerIntent.getString("receiver").toString()
                sender = stickerIntent.getString("sender").toString()
                receiverName = stickerIntent.getString("name").toString()

            }
        }

        sendButton = findViewById(R.id.send_btn)
        sendButton.setOnClickListener {
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
      
        stickerMessageList.clear()
        val tempList = ArrayList<StickerCard>()
        fireStore.collection("senderChat").document("$sender-$receiver")
            .collection("messages").orderBy("timestamp", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->
                for (user in result) {
                    Log.i(TAG, user.toString())
                    val userData = user.data
                    val currentUser = Firebase.auth.currentUser
                    if (userData["email"].toString() != currentUser?.email) {
                        val chat = StickerCard(
                            userData["sticker"].toString(),
                            userData["timestamp"] as Timestamp,
                            userData["sender"].toString(),
                            userData["receiver"].toString()
                        )
                        Log.i(TAG, chat.toString())
                        tempList.add(chat)
                        Log.i(TAG, "Stickerlist: $tempList")

                    }
                }
            }

        fireStore.collection("senderChat").document("$receiver-$sender")
            .collection("messages").orderBy("timestamp", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->
                for (user in result) {
                    val userData = user.data
                    val currentUser = Firebase.auth.currentUser
                    if (userData["email"].toString() != currentUser?.email) {
                        val chat = StickerCard(
                            userData["sticker"].toString(),
                            userData["timestamp"] as Timestamp,
                            userData["sender"].toString(),
                            userData["receiver"].toString()
                        )
                        tempList.add(chat)
                        Log.i(TAG, "Stickerlist: $tempList")

                    }
                }
                Log.i(TAG, "Stickerlist before: $tempList")
                Collections.sort(tempList, sorter)
                tempList.reverse()
                stickerMessageList.addAll(tempList)
                adapter?.notifyDataSetChanged()
            }


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

            senderId = intent.getStringExtra("senderId").toString()
            sender = senderId.toString()

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
        val newMessage = StickerCard(stringStickerImg, time, sender, receiver)
        fireStore.collection("senderChat").document("$sender-$receiver").collection("messages")
            .document().set(newMessage)
            .addOnSuccessListener {
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(
                    baseContext, "successfully made.",
                    Toast.LENGTH_SHORT
                ).show()
                stickerMessageList.add(newMessage)
                adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                Toast.makeText(
                    baseContext, "Could not send your sticker! you done goofed",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}