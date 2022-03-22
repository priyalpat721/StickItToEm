package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.cards.ChatCard
import edu.neu.madcourse.stickittoem.viewHolder.ChatViewHolder
import edu.neu.madcourse.stickittoem.messages.StickerMessagingActivity

class ChatAdapter(
    private var chatList: MutableList<ChatCard>,
    private var context: Context
) : RecyclerView.Adapter<ChatViewHolder>() {

    private val TAG = "ChatAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.chat_info_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.name.text = chatList[position].name
        var total = chatList[position].totalStickersReceived
        total = total?.plus(chatList[position].totalStickersSent!!)
        holder.totalStickers.text = (total.toString())

        //TODO this should open a messaging history associated with user
        //TODO DOES NOT WORK CORRECTLY, JUST DUMMY SET UP
        holder.chatInfoLayout.setOnClickListener{
            val intent = Intent(context, StickerMessagingActivity::class.java)
            Log.i(TAG, chatList[position].toString())
            intent.putExtra("name", chatList[position].name)
            intent.putExtra("receiverId", chatList[position].receiverId)
            intent.putExtra("senderId", chatList[position].senderId)
            Log.i(TAG, intent.getStringExtra("name").toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return chatList.size
    }
}