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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.ChatAdapter
import edu.neu.madcourse.stickittoem.cards.ChatCard

class FragmentChat : Fragment(R.layout.fragment_chat) {
    private val TAG: String = "TEST"
    private val chatList: ArrayList<ChatCard> = ArrayList()


    private var recyclerView: RecyclerView? = null
    var adapter: ChatAdapter? = null

    //private var db = Firebase.firestore
    private var db = Firebase.database.reference

    var cards = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.chat_recycler_view)

        setUpResources()
        getData()
        listenForChanges()
    }

    private fun getData() {

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun listenForChanges() {
        chatList.clear()
        db.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val name = snap.child("name").getValue(String::class.java)
                    val totalReceived = snap.child("totalReceived").getValue(Int::class.java)
                    val totalSent = snap.child("totalSent").value as Map<String, Int>
                    val currentUser = Firebase.auth.currentUser?.uid!!
                    val receiver = snap.key.toString()
                    val email = snap.child("email").getValue(String::class.java)

                    db.child("chatLog").child("$currentUser-$receiver").child("messages")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (message in snapshot.children) {
                                    val chat =
                                        ChatCard(
                                            name!!,
                                            receiver,
                                            currentUser,
                                            email!!,
                                            totalReceived!!,
                                            totalSent
                                        )
                                    Log.i(TAG, "CHATS$snap")
                                    if (Firebase.auth.currentUser?.uid != snap.key) {
                                        chatList.add(chat)
                                        adapter?.notifyDataSetChanged()
                                    }
                                    break
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // not implemented
                            }
                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // not implemented
            }
        })
    }


            private fun setUpResources() {
        adapter = this.context?.let { ChatAdapter(chatList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}
