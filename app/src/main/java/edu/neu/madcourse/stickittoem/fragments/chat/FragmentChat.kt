package edu.neu.madcourse.stickittoem.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.ChatAdapter
import edu.neu.madcourse.stickittoem.models.ChatCard

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
        chatList.add(ChatCard("Priyal", "pri@gmail.com", "rachitmehta96@gmail.com",16))
        chatList.add(ChatCard("Rachit", "rachitmehta96@gmail.com", "pri@gmail.com",8))
    }

    private fun setUpResources() {
        adapter = this.context?.let { ChatAdapter(chatList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}