package edu.neu.madcourse.stickittoem

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.messages.StickerCard
import edu.neu.madcourse.stickittoem.messages.StickerChatAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var stickerMessageList: MutableList<StickerCard> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: StickerChatAdapter? = null
    var context: Context = this
    private lateinit var stickerDisplayButton: Button
    private lateinit var sendButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        setUpResources()
        getDummyData()

        stickerDisplayButton = findViewById(R.id.sticker_btn)
        // TODO Jen: add the sticker popup functionality here :)
        stickerDisplayButton.setOnClickListener {

        }
        sendButton = findViewById(R.id.send_btn)
        sendButton.setOnClickListener{
            // TODO logic is dependent on database
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDummyData() {
        stickerMessageList.add(StickerCard("https://images.unsplash.com/photo-1647384630342-e2bffda01370?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=686&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
            Date()
        )))

        stickerMessageList.add(StickerCard("https://images.unsplash.com/photo-1603826567438-7ccb36dff6e7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1842&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
            Date()
        )))
    }

    private fun setUpResources() {
        recyclerView = findViewById(R.id.message_recycler_view)

        adapter = StickerChatAdapter(stickerMessageList, context)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }

}

//    private val db = Firebase.firestore
//    private val TAG = "FireStore"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Create a new user with a first and last name
//        val user1 = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815
//        )
//
//        // Add a new document with a generated ID
//        db.collection("users")
//            .add(user1)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//
//        // Create a new user with a first, middle, and last name
//        val user2 = hashMapOf(
//            "first" to "Alan",
//            "middle" to "Mathison",
//            "last" to "Turing",
//            "born" to 1912
//        )
//
//        // Add a new document with a generated ID
//        db.collection("users")
//            .add(user2)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//
//        db.collection("users")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//    }
