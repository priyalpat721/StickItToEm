package edu.neu.madcourse.stickittoem.messages

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.StickerMessagingAdapter
import edu.neu.madcourse.stickittoem.cards.StickerCard
import java.util.*
import kotlin.collections.HashMap


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
    private var url: Uri? = null
    private var stickerImage: Int? = null
    private var stickerDescription: String? = null
    private var fireStore = FirebaseFirestore.getInstance()

    @ServerTimestamp
    var time: FieldValue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        setUpResources()

        stickerDisplayButton = findViewById(R.id.sticker_btn)
        // TODO Jen: add the sticker popup functionality here :)
        getIds()
        val bottomStickerSheetDialog = BottomStickerSheetDialog()
        getDummyData()

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
                Log.i(TAG, receiver.toString())
                Log.i(TAG, sender.toString())
                Log.i(TAG, stickerImage.toString())
                Log.i(TAG, stickerDescription.toString())
                getDummyData()
//                println("This is from sticker intent: \n${stickerIntent.getString("image")}")
            }
        }
        sendButton = findViewById(R.id.send_btn)
        sendButton.setOnClickListener {
            val stickerIntent = intent.extras
            stickerImage = stickerIntent?.getInt("image")
            when(stickerImage){
                2131165376->{
                    stringStickerImg = "exercisedino"
                }
                2131165377->{
                    stringStickerImg = "frustratedino"
                }
                2131165378->{
                    stringStickerImg = "happydino"
                }
                2131165379->{
                    stringStickerImg = "motivatedino"
                }
                2131165380->{
                    stringStickerImg = "saddino"
                }
                2131165381->{
                    stringStickerImg = "sleepdino2"
                }
            }

            stickerDescription = stickerIntent?.getString("description")
            receiver = stickerIntent?.getString("receiver").toString()
            sender = stickerIntent?.getString("sender").toString()
            receiverName = stickerIntent?.getString("name").toString()

            println("This is from sticker intent: \n${stringStickerImg}")
            addToDB()
        }
    }

    private fun getDummyData() {
        stickerMessageList.add(StickerCard("motivatedino", FieldValue.serverTimestamp(), "pri@gmail.com", "rachitmehta96@gmail.com"))
        stickerMessageList.add(StickerCard("sleepdino2", FieldValue.serverTimestamp(), "rachitmehta96@gmail.com", "pri@gmail.com"))

        Log.i(TAG, stickerMessageList.toString())
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

    private fun addToDB() {
        time = FieldValue.serverTimestamp()
        val newMessage = StickerCard(stringStickerImg, time, sender, receiver)
        fireStore.collection("senderChat").document("$sender-$receiver").collection("messages").document().set(newMessage)
            .addOnSuccessListener {
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(
                    baseContext, "successfully made.",
                    Toast.LENGTH_SHORT
                ).show()
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