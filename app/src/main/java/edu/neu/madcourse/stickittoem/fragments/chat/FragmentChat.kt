package edu.neu.madcourse.stickittoem.fragments.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.ChatAdapter
import edu.neu.madcourse.stickittoem.cards.ChatCard

class FragmentChat : Fragment(R.layout.fragment_chat) {
    private val TAG: String = "TEST"
    private val chatList: ArrayList<ChatCard> = ArrayList()
    private var recyclerView: RecyclerView? = null
    var adapter: ChatAdapter? = null
    private var db = Firebase.firestore

    var cards = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.history_recycler_view)

        setUpResources()

        getData()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        chatList.clear()
        db.collection("users").get().addOnSuccessListener { result ->
            for (user in result) {
                val userData = user.data
                val currentUser = Firebase.auth.currentUser
                if (userData["email"].toString() != currentUser?.email) {
                    val sender = currentUser?.email
                    val receiver = userData["email"].toString()
                    val senderReceiver = "$sender-$receiver"
                    val receiverSender = "$receiver-$sender"
                    db.collection("senderChat").document(senderReceiver).get()
                        .addOnSuccessListener {
                            getSenderMessages(userData, sender, receiver, currentUser)

                        }.addOnFailureListener {

                        }

                    db.collection("senderChat").document(receiverSender).get()
                        .addOnSuccessListener {
                            getReceiverMessages(userData, sender, receiver, currentUser)
                        }.addOnFailureListener {

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

    @SuppressLint("NotifyDataSetChanged")
    private fun getSenderMessages(
        userData: Map<String, Any>,
        sender: String?,
        receiver: String,
        currentUser: FirebaseUser?
    ) {
        db.collection("senderChat").document("$sender-$receiver")
            .collection("messages").get().addOnSuccessListener { response ->
                for (message in response) {
                    if (message != null) {
                        Log.i(TAG, message.data.toString())
                        val chat = currentUser?.email?.let {
                            ChatCard(
                                userData["name"].toString(),
                                userData["email"].toString(),
                                it,
                                userData["email"].toString(),
                                Integer.parseInt(userData["totalReceived"].toString()),
                                Integer.parseInt(userData["totalSent"].toString())
                            )
                        }
                        if (chat != null) {
                            if (!cards.contains(chat.senderId)) {
                                chat.receiverId.let { cards.add(it) }
                                chatList.add(chat)
                                adapter?.notifyDataSetChanged()
                                Log.i(TAG, "IN sender message: $chatList")
                                break
                            }
                        }
                    }
                    break
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getReceiverMessages(
        userData: Map<String, Any>,
        sender: String?,
        receiver: String,
        currentUser: FirebaseUser?
    ) {
        db.collection("senderChat").document("$receiver-$sender")
            .collection("messages").get().addOnSuccessListener { response ->
                for (message in response) {
                    if (message != null) {
                        Log.i(TAG, message.data.toString())
                        val chat = currentUser?.email?.let {
                            ChatCard(
                                userData["name"].toString(),
                                userData["email"].toString(),
                                it,
                                userData["email"].toString(),
                                Integer.parseInt(userData["totalReceived"].toString()),
                                Integer.parseInt(userData["totalSent"].toString())
                            )
                        }

                        if (chat != null) {
                            if (!cards.contains(chat.receiverId)) {
                                chat.senderId.let { cards.add(it) }
                                chatList.add(chat)
                                adapter?.notifyDataSetChanged()
                                Log.i(TAG, "IN receiver message: $chatList")
                                break
                            }
                        }
                    }
                    break
                }
            }
    }
}
