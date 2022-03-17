package edu.neu.madcourse.stickittoem.messages

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class MessagingActivity : AppCompatActivity() {
    private var stickerMessageList: MutableList<StickerCard> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: StickerChatAdapter? = null
    var context: Context = this
    private lateinit var stickerDisplayButton : Button
    private lateinit var sendButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        stickerDisplayButton = findViewById(R.id.sticker_btn)
        sendButton = findViewById(R.id.send_btn)

        setUpResources();

    }

    private fun setUpResources() {
        recyclerView = findViewById(R.id.message_recycler_view)

        adapter = StickerChatAdapter(stickerMessageList, context)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}