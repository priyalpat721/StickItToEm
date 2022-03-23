package edu.neu.madcourse.stickittoem.fragments.chat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.ChatAdapter
import edu.neu.madcourse.stickittoem.cards.UserCard

class FragmentChat : Fragment(R.layout.fragment_chat) {
    private val userList: MutableList<UserCard> = ArrayList<UserCard>()
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
                    val chat = UserCard(
                        userData["name"].toString(),
                        userData["email"].toString(),
                        currentUser?.email,
                        userData["email"].toString(),
                        Integer.parseInt(userData["totalReceived"].toString()),
                        Integer.parseInt(userData["totalSent"].toString())
                    )
                    userList.add(chat)
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setUpResources() {
        adapter = this.context?.let { ChatAdapter(userList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}