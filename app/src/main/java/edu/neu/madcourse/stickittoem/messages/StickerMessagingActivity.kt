package edu.neu.madcourse.stickittoem.messages

import android.annotation.SuppressLint
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.StickerMessagingAdapter
import edu.neu.madcourse.stickittoem.cards.StickerCard
import edu.neu.madcourse.stickittoem.cards.UserCard
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
    private val stickerImgHashMap:HashMap<String, Uri> = HashMap()

    @ServerTimestamp
    var time: FieldValue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        setUpResources()
        stickerDisplayButton = findViewById(R.id.sticker_btn)
        getIds()
        var chatDataExists = checkChatData()
        val bottomStickerSheetDialog = BottomStickerSheetDialog()
        if (chatDataExists) {
            getData()
        }
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
                Log.i(TAG, "Receiver: "+receiver.toString())
                Log.i(TAG, "Sender: $sender")
                Log.i(TAG, "Sticker Image: $stickerImage")
                Log.i(TAG, stickerDescription.toString())
                getData()
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
                    url = Uri.parse("android.resource://edu.neu.madcourse.stickittoem.messages/drawable/R.drawable.exercisedino")
                }
                2131165377->{
                    stringStickerImg = "frustratedino"
                    url = Uri.parse("android.resource://edu.neu.madcourse.stickittoem.messages/drawable/R.drawable.frustratedino")
                }
                2131165378->{
                    stringStickerImg = "happydino"
                    url = Uri.parse("android.resource://edu.neu.madcourse.stickittoem.messages/drawable/R.drawable.happydino")
                }
                2131165379->{
                    stringStickerImg = "motivatedino"
                    url = Uri.parse("android.resource://edu.neu.madcourse.stickittoem.messages/drawable/R.drawable.motivatedino")
                }
                2131165380->{
                    stringStickerImg = "saddino"
                    url = Uri.parse("android.resource://edu.neu.madcourse.stickittoem.messages/drawable/R.drawable.saddino")
                }
                2131165381->{
                    stringStickerImg = "sleepdino2"
                    url = Uri.parse("android.resource://edu.neu.madcourse.stickittoem.messages/drawable/R.drawable.sleepdino2")
                }
            }
            Log.i(TAG, "Receiver: "+receiver.toString())
            Log.i(TAG, "Sender: $sender")
            Log.i(TAG, "Sticker Image: $stickerImage")
            Log.i(TAG, stickerDescription.toString())
            stickerImgHashMap.put(stringStickerImg!!,url!!)
            stickerDescription = stickerIntent?.getString("description")
            receiver = stickerIntent?.getString("receiver").toString()
            sender = stickerIntent?.getString("sender").toString()
            receiverName = stickerIntent?.getString("name").toString()

            println("This is from sticker intent: \n${stringStickerImg}")
            addToDB()

        }
    }

    private fun checkChatData(): Boolean {
        var senderReceiver ="$sender-$receiver"
        var receiverSender = "$receiver-$sender"
        var senderVal = false
        var receiverVal = false
        fireStore.collection("senderChat").document(senderReceiver).get().addOnSuccessListener {
            senderVal = true
        }
        fireStore.collection("senderChat").document(receiverSender).get().addOnSuccessListener {
            receiverVal = true
        }
        return senderVal or receiverVal
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        fireStore.collection("senderChat").document("$sender-$receiver").collection("messages").get().addOnSuccessListener { result ->
            for (user in result) {
                Log.i(TAG, user.toString())
                val userData = user.data
                val currentUser = Firebase.auth.currentUser
                if (userData["email"].toString() != currentUser?.email) {
                    val chat = StickerCard(
                        userData["sticker"].toString(),
                        userData["timestamp"] as FieldValue?,
                        userData["sender"].toString(),
                        userData["receiver"].toString()
                    )
                    Log.i(TAG, chat.toString())
                    stickerMessageList.add(chat)
                    adapter?.notifyDataSetChanged()
                }
            }
        }

        fireStore.collection("senderChat").document("$receiver-$sender").collection("messages").get().addOnSuccessListener { result ->
            for (user in result) {
                val userData = user.data
                val currentUser = Firebase.auth.currentUser
                if (userData["email"].toString() != currentUser?.email) {
                    val chat = StickerCard(
                        userData["sticker"].toString(),
                        userData["timestamp"] as FieldValue?,
                        userData["sender"].toString(),
                        userData["receiver"].toString()
                    )
                    stickerMessageList.add(chat)
                    adapter?.notifyDataSetChanged()
                }
            }
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
        time = FieldValue.serverTimestamp()
        val newMessage = StickerCard(stringStickerImg, time, sender, receiver)
        fireStore.collection("senderChat").document("$sender-$receiver").collection("messages").document().set(newMessage)
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