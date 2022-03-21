package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.cards.UserCard
import edu.neu.madcourse.stickittoem.viewHolder.ChatViewHolder
import edu.neu.madcourse.stickittoem.viewHolder.ContactViewHolder

class ContactAdapter(private var contactslist: MutableList<UserCard>, private var context: Context) : RecyclerView.Adapter<ContactViewHolder>() {

    private val TAG = "ContactAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.contact_info_layout, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.name.text = contactslist[position].name

    }

    override fun getItemCount(): Int {
        return contactslist.size
    }


}