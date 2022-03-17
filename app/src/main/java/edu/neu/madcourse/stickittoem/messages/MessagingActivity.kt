package edu.neu.madcourse.stickittoem.messages

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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