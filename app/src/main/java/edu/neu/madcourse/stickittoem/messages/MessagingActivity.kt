package edu.neu.madcourse.stickittoem.messages

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import edu.neu.madcourse.stickittoem.R
import java.text.SimpleDateFormat
import java.util.*

class MessagingActivity : AppCompatActivity() {
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
        val bottomStickerSheetDialog = BottomStickerSheetDialog()
        stickerDisplayButton.setOnClickListener {
            bottomStickerSheetDialog.show(supportFragmentManager,"sticker sheet")
        }
        sendButton = findViewById(R.id.send_btn)
        sendButton.setOnClickListener{
            // TODO logic is dependent on database
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
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            StickerCard("https://images.unsplash.com/photo-1647396603763-1167b14aa7ea?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=671&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
                Date()
            ),
                it, "")
        }?.let { stickerMessageList.add(it) }

        FirebaseAuth.getInstance().currentUser?.uid?.let {
            StickerCard("https://images.unsplash.com/photo-1647377106050-9dfad031a8e7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=764&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
                Date()
            ),
                "r7YKgBnaKcVydbZeeRXPiawP5NE2", it)
        }?.let { stickerMessageList.add(it) }
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            StickerCard("https://images.unsplash.com/photo-1647391004047-92f60efd8a99?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
                Date()
            ),
                "r7YKgBnaKcVydbZeeRXPiawP5NE2", it)
        }?.let { stickerMessageList.add(it) }

        FirebaseAuth.getInstance().currentUser?.uid?.let {
            StickerCard("https://images.unsplash.com/photo-1598362314166-a81c4a4ffa83?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=714&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
                Date()
            ),
                it, "r7YKgBnaKcVydbZeeRXPiawP5NE2")
        }?.let { stickerMessageList.add(it) }

        FirebaseAuth.getInstance().currentUser?.uid?.let {
            StickerCard("https://images.unsplash.com/photo-1610140755238-fdce8f3be7a3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
                Date()
            ),
                "r7YKgBnaKcVydbZeeRXPiawP5NE2", it)
        }?.let { stickerMessageList.add(it) }
    }

    private fun setUpResources() {
        recyclerView = findViewById(R.id.message_recycler_view)

        adapter = StickerChatAdapter(stickerMessageList, context)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}