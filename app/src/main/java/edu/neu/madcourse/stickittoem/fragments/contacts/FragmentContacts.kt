package edu.neu.madcourse.stickittoem.fragments.contacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.ContactAdapter
import edu.neu.madcourse.stickittoem.cards.ChatCard
import edu.neu.madcourse.stickittoem.cards.UserCard

class FragmentContacts : Fragment(R.layout.fragment_contacts) {
    private val TAG: String = "HASHMAP"
    private val contactsList: MutableList<ChatCard> = ArrayList()
    private var recyclerView: RecyclerView? = null
    var adapter: ContactAdapter? = null
    private var db = Firebase.database.reference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.contact_recycler_view)

        setUpResources()
        listenForChanges()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun listenForChanges() {
        db.child("users").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                contactsList.clear()
                for (snap in snapshot.children) {
                    val name = snap.child("name").getValue(String::class.java)
                    val totalReceived = snap.child("totalReceived").getValue(Int::class.java)
                    val totalSent = snap.child("totalSent").value as Map<String, Int>
                    val user =
                        ChatCard(name!!,
                            snap.key.toString(),
                            Firebase.auth.currentUser?.uid!!,
                            snap.key.toString(),
                            totalReceived!!,
                            totalSent)
                    if (Firebase.auth.currentUser?.uid != snap.key) {
                        contactsList.add(user)
                        adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // not implemented
            }

        })

    }

    private fun setUpResources(){
        adapter = this.context?.let { ContactAdapter(contactsList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }
}