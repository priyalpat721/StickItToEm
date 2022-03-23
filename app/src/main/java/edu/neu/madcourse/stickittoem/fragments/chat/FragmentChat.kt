package edu.neu.madcourse.stickittoem.fragments.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.ChatAdapter
import edu.neu.madcourse.stickittoem.cards.ChatCard

class FragmentChat : Fragment(R.layout.fragment_chat) {
    private val TAG: String = "TEST"
    private val chatList: MutableList<ChatCard> = ArrayList<ChatCard>()
    private var recyclerView: RecyclerView? = null
    var adapter: ChatAdapter? = null
    private var db = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.chat_recycler_view)

        setUpResources()

        getData()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        db.collection("users").get().addOnSuccessListener { result ->
            for (user in result) {
                val userData = user.data
                val currentUser = Firebase.auth.currentUser
                if (userData["email"].toString() != currentUser?.email) {
                    val sender = currentUser?.email
                    val receiver = userData["email"].toString()
                    Log.i(TAG, "Sender: $sender")
                    Log.i(TAG, "Receiver: $receiver")
                    db.collection("senderChat").document("$sender-$receiver")
                        .collection("messages").get().addOnSuccessListener {
                            response ->
                            for(message in response) {
                                if (message != null) {
                                    Log.i(TAG, message.data.toString())
                                    val chat = ChatCard(
                                        userData["name"].toString(),
                                        userData["email"].toString(),
                                        currentUser?.email,
                                        userData["email"].toString(),
                                        Integer.parseInt(userData["totalReceived"].toString()),
                                        Integer.parseInt(userData["totalSent"].toString())
                                    )
                                    chatList.add(chat)
                                    adapter?.notifyDataSetChanged()
                                    break
                                }
                            }

                        }

                    // THIS IS THE BUG
                    db.collection("senderChat").document("$receiver-$sender")
                        .collection("messages").get().addOnSuccessListener {
                                response ->
                            for(message in response) {
                                if (message != null) {
                                    Log.i(TAG, message.data.toString())
                                    val chat = ChatCard(
                                        userData["name"].toString(),
                                        userData["email"].toString(),
                                        currentUser?.email,
                                        userData["email"].toString(),
                                        Integer.parseInt(userData["totalReceived"].toString()),
                                        Integer.parseInt(userData["totalSent"].toString())
                                    )
                                    if(!chatList.contains(chat)) {
                                        chatList.add(chat)
                                    }
                                    adapter?.notifyDataSetChanged()
                                    break
                                }
                            }

                        }
                }
            }
        }
    }

    private fun setUpResources() {
        adapter = this.context?.let { ChatAdapter(chatList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}
