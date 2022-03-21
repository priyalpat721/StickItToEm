package edu.neu.madcourse.stickittoem.messages

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import edu.neu.madcourse.stickittoem.MainActivity
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.StickerMessagingAdapter
import edu.neu.madcourse.stickittoem.cards.StickerCard
import java.text.SimpleDateFormat
import java.util.*


class StickerMessagingActivity : AppCompatActivity() {
    private val TAG = "StickerAppMessage"
    private var stickerMessageList: MutableList<StickerCard> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: StickerMessagingAdapter? = null
    var context: Context = this
    private lateinit var stickerDisplayButton: Button
    private lateinit var sendButton: ImageButton
    private lateinit var nameBox : TextView

    private  var receiverId: String ? = null
    private  var senderId: String ? = null
    private  var stickerImage: String ?  = null
    private  var stickerDescription: String ?  = null
    private var fireStore = Firebase.firestore

    @ServerTimestamp
    var time: FieldValue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        setUpResources()
        getDummyData()



        val extras = intent.extras
        if (extras != null) {
            val receiverName = extras.getString("name")
            if (savedInstanceState != null) {
                receiverId = intent.getBundleExtra("receiverId").toString()
            }
            if (savedInstanceState != null) {
                senderId = intent.getBundleExtra("senderId").toString()
            }

            Log.i(TAG, extras.toString())
            nameBox = findViewById(R.id.receiver_name_box)
            nameBox.text = receiverName;
        }

        stickerDisplayButton = findViewById(R.id.sticker_btn)
        // TODO Jen: add the sticker popup functionality here :)
        val bottomStickerSheetDialog = BottomStickerSheetDialog()
        stickerDisplayButton.setOnClickListener {
            bottomStickerSheetDialog.show(supportFragmentManager,"sticker sheet")

//            val stickerIntent = intent.extras
//            if(stickerIntent != null) {
//                stickerImage = stickerIntent.getString("image")
//                stickerDescription = stickerIntent.getString("description")
////                Log.i(TAG, stickerIntent.toString())
////                addToDB()
//                println("This is from sticker intent: \n" + stickerIntent.toString())
//
//            }
        }
        sendButton = findViewById(R.id.send_btn)
        sendButton.setOnClickListener{
            // TODO logic is dependent on database
            if (savedInstanceState != null) {
                println("This is the receiverId in add to onClicklistener() : " + savedInstanceState.getString("image"))
            }
            if (savedInstanceState != null) {
                println(" This is the senderId in add to onClickListener() : " +savedInstanceState.getString("description"))
            }
            addToDB()
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDummyData() {
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            StickerCard("https://images.unsplash.com/photo-1647384630342-e2bffda01370?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=686&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
                Date()),
                it, "r7YKgBnaKcVydbZeeRXPiawP5NE2")
        }?.let { stickerMessageList.add(it) }

        FirebaseAuth.getInstance().currentUser?.uid?.let {
            StickerCard("https://images.unsplash.com/photo-1603826567438-7ccb36dff6e7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1842&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
                Date()
            ), "r7YKgBnaKcVydbZeeRXPiawP5NE2", it)
        }?.let { stickerMessageList.add(it) }
    }

    private fun setUpResources() {
        recyclerView = findViewById(R.id.message_recycler_view)

        adapter = StickerMessagingAdapter(stickerMessageList, context)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }

    private fun addToDB() {

        println("This is the receiverId in add to DB() : " + receiverId)
        println(" This is the senderId in add to DB() : " +senderId)

        time = FieldValue.serverTimestamp()
        val newSender = hashMapOf(
            "imgRef" to stickerImage,
            "timeStamp" to time,
        )
        fireStore.collection("senderChat").document(senderId + "-" + receiverId).set(newSender)
            .addOnSuccessListener {
                // this autogenerated the document id for the new user

                Log.d(TAG, "DocumentSnapshot added with ID: $senderId + $receiverId")
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(
                    baseContext, "successfully made.",
                    Toast.LENGTH_SHORT
                ).show()

                fireStore.collection("receiverChat").document(receiverId + "-" + senderId).set(newSender)

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