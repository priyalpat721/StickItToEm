package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.content.Intent
import android.text.Layout

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.cards.UserCard
import edu.neu.madcourse.stickittoem.messages.StickerMessagingActivity

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
        var total = contactslist[position].totalStickersReceived


        //TODO this should open a messaging history associated with user
        //TODO DOES NOT WORK CORRECTLY, JUST DUMMY SET UP
        holder.contactInfoLayout.setOnClickListener{
            val intent = Intent(context, StickerMessagingActivity::class.java)
            Log.i(TAG, contactslist[position].toString())
            intent.putExtra("name", contactslist[position].name)
            intent.putExtra("receiverId", contactslist[position].receiverId)
            intent.putExtra("senderId", contactslist[position].senderId)
            Log.i(TAG, intent.getStringExtra("name").toString())
            context.startActivity(intent)
        }

        holder.contactInfoLayout.setOnClickListener{
            val intent = Intent(context, StickerMessagingActivity::class.java)
            Log.i(TAG, contactslist[position].toString())
            intent.putExtra("name", contactslist[position].name)
            intent.putExtra("receiverId", contactslist[position].receiverId)
            intent.putExtra("senderId", contactslist[position].senderId)
            Log.i(TAG, intent.getStringExtra("name").toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return contactslist.size
    }

}