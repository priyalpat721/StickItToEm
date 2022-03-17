package edu.neu.madcourse.stickittoem.chat

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class ChatActivity : AppCompatActivity() {
    private val chatList: MutableList<ChatCard> = ArrayList<ChatCard>()
    private var recyclerView: RecyclerView? = null
    var adapter: ChatAdapter? = null
    var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setUpResources()

        getDummyData()
    }

    private fun getDummyData() {
        chatList.add(ChatCard("Priyal", 16))
        chatList.add(ChatCard("Rachit", 8))
        chatList.add(ChatCard("Jen", 100))
        chatList.add(ChatCard("Kash", 2))
    }

    private fun setUpResources() {
        recyclerView = findViewById(R.id.chat_recycler_view)

        adapter = ChatAdapter(chatList, context)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}