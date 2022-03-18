package edu.neu.madcourse.stickittoem.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R

class StickerChatAdapter(
    private var stickerMessageList: MutableList<StickerCard>,
    private var context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // source: https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup,%20int)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // viewType comes from the function getItemViewType it seems default is 0

        // sender found
        return if (viewType == 0) {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.sticker_sent_layout, parent, false)
            StickerSentViewHolder(view)
        } else {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.sticker_received_layout, parent, false)
            StickerReceivedViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // holder can be of two types here: received and sent so this needs to be taken into account
        // source for :: meaning https://kotlinlang.org/docs/reflection.html
        if (holder.javaClass == StickerSentViewHolder::class.java) {
            holder as StickerSentViewHolder
            Glide.with(context).load(stickerMessageList[position].sticker).into(holder.sentSticker)

        } else {
            holder as StickerReceivedViewHolder
            Glide.with(context).load(stickerMessageList[position].sticker)
                .into(holder.receivedSticker)
        }
    }

    override fun getItemCount(): Int {
        return stickerMessageList.size
    }

    override fun getItemViewType(position: Int): Int {
        var message = stickerMessageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid == message.sender) {
            return 0
        }
        return 1
    }
}