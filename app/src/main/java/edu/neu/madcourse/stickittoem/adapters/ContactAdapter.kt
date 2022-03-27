package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.content.Intent

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.cards.ChatCard
import edu.neu.madcourse.stickittoem.cards.UserCard
import edu.neu.madcourse.stickittoem.messages.StickerMessagingActivity

import edu.neu.madcourse.stickittoem.viewHolder.ContactViewHolder

class ContactAdapter(private var contactslist: MutableList<ChatCard>, private var context: Context) : RecyclerView.Adapter<ContactViewHolder>() {

    private val TAG = "ContactAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.contact_info_layout, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        holder.name.text = contactslist[position].name

        holder.contactInfoLayout.setOnClickListener{
            val intent = Intent(context, StickerMessagingActivity::class.java)
            Log.i(TAG, contactslist[position].toString())
            intent.putExtra("name", contactslist[position].name)
            intent.putExtra("senderId", contactslist[position].senderId)
            intent.putExtra("receiverId", contactslist[position].receiverId)
            Log.i(TAG, "Intent" + intent.getStringExtra("receiverId").toString())
            Log.i(TAG, "Intent" + intent.getStringExtra("senderId").toString())
            context.startActivity(intent)
        }

//        holder.contactInfoLayout.setOnClickListener{
//            val intent = Intent(context, StickerMessagingActivity::class.java)
//            Log.i(TAG, contactslist[position].toString())
//            intent.putExtra("name", contactslist[position].name)
//            intent.putExtra("senderId", contactslist[position].senderId)
//            intent.putExtra("receiverId", contactslist[position].receiverId)
//            Log.i(TAG, "Intent" + intent.getStringExtra("receiverId").toString())
//            Log.i(TAG, "Intent" + intent.getStringExtra("senderId").toString())
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return contactslist.size
    }

}