package edu.neu.madcourse.stickittoem.fragments.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.ContactAdapter
import edu.neu.madcourse.stickittoem.cards.UserCard

class FragmentContacts : Fragment(R.layout.fragment_contacts) {
    private val contactsList: MutableList<UserCard> = ArrayList<UserCard>()
    private var recyclerView: RecyclerView? = null
    var adapter: ContactAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.contact_recycler_view)

        setUpResources()
        getDummyData()
    }

    private fun getDummyData(){
        contactsList.add(UserCard("Priyal", 0, 1))
        contactsList.add(UserCard("Rach", 1, 2))
        contactsList.add(UserCard("Kash", 2, 3))
    }

    private fun setUpResources(){
        adapter = this.context?.let { ContactAdapter(contactsList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }

}