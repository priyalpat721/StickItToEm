package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.cards.ChatCard
import edu.neu.madcourse.stickittoem.viewHolder.ChatViewHolder
import edu.neu.madcourse.stickittoem.messages.StickerMessagingActivity

class ChatAdapter(
    private var chatList: ArrayList<ChatCard>,
    private var context: Context
) : RecyclerView.Adapter<ChatViewHolder>() {

    private var db = Firebase.database.reference
    private val TAG = "ChatAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.chat_info_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.name.text = chatList[position].name
        val sender = chatList[position].senderId
        val receiver = chatList[position].receiverId
        var count = 0
        Log.i(TAG, "Current sender: $sender")
        Log.i(TAG, "Current receiver: $receiver")
        db.child("chatLog").child("$sender-$receiver").child("messages")
            .runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {

                    currentData.children.forEach { result ->
                        Log.i(TAG, "Current data: ${result.value}")
                        count++
                    }
                    Log.i(TAG, "Current data: $count")
                    holder.totalSent.text = count.toString()

                    return Transaction.success(currentData)
                }

                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    //Not implemented
                }
            })

        holder.chatInfoLayout.setOnClickListener {
            val intent = Intent(context, StickerMessagingActivity::class.java)
            Log.i(TAG, chatList[position].toString())
            intent.putExtra("name", chatList[position].name)
            intent.putExtra("senderId", chatList[position].senderId)
            intent.putExtra("receiverId", chatList[position].receiverId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}