package edu.neu.madcourse.stickittoem.messages

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.StickerMessagingAdapter
import edu.neu.madcourse.stickittoem.cards.StickerCard
import java.text.SimpleDateFormat
import java.util.*


class StickerMessagingActivity : AppCompatActivity() {
    private lateinit var sender: String
    private lateinit var receiver: String
    private val TAG = "StickerAppMessage"
    private var stickerMessageList: MutableList<StickerCard> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: StickerMessagingAdapter? = null
    var context: Context = this
    private lateinit var stickerDisplayButton: Button
    private lateinit var sendButton: ImageButton
    private lateinit var nameBox: TextView

    private var receiverId: String? = null
    private var senderId: String? = null
    private var stickerImage: String? = null
    private var stickerDescription: String? = null
    private var fireStore = Firebase.firestore

    @ServerTimestamp
    var time: FieldValue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        setUpResources()
        //getDummyData()

        getIds()

        stickerDisplayButton = findViewById(R.id.sticker_btn)
        // TODO Jen: add the sticker popup functionality here :)
        val bottomStickerSheetDialog = BottomStickerSheetDialog()
        stickerDisplayButton.setOnClickListener {
            bottomStickerSheetDialog.show(supportFragmentManager, "sticker sheet")

            val stickerIntent = intent.extras
            if (stickerIntent != null) {
                stickerImage = stickerIntent.getString("image")
                stickerDescription = stickerIntent.getString("description")
                Log.i(TAG, stickerIntent.toString())
                println("This is from sticker intent: \n$stickerIntent")
            }
        }
        sendButton = findViewById(R.id.send_btn)
        sendButton.setOnClickListener {
            addToDB()
        }
    }

    private fun getIds() {
        val extras = intent.extras
        if (extras != null) {
            val receiverName = extras.getString("name")

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

    private fun addToDB() {

        println("This is the receiverId in add to DB() : $receiver")
        println(" This is the senderId  bin add to DB() : $sender")

        time = FieldValue.serverTimestamp()

        val newSender = stickerImage?.let { StickerCard (it, time, sender,receiver) }

        if (newSender != null) {
            fireStore.collection("senderChat").document("$sender-$receiver").set(newSender)
                .addOnSuccessListener {
                    // this autogenerated the document id for the new user

                    Log.d(TAG, "DocumentSnapshot added with ID: $sender + $receiver")
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext, "successfully made.",
                        Toast.LENGTH_SHORT
                    ).show()

                    fireStore.collection("receiverChat").document("$receiver-$senderId")
                        .set(newSender)

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
}