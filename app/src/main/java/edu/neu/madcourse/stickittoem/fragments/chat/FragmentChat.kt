package edu.neu.madcourse.stickittoem.fragments.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.messages.MessagingActivity

class FragmentChat : Fragment(R.layout.fragment_chat) {
    private val chatList: MutableList<ChatCard> = ArrayList<ChatCard>()
    private var recyclerView: RecyclerView? = null
    var adapter: ChatAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.chat_recycler_view)

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
        adapter = this.context?.let { ChatAdapter(chatList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}