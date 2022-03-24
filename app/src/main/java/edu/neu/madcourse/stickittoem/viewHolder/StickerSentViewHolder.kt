package edu.neu.madcourse.stickittoem.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class StickerSentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var sentSticker: ImageView = itemView.findViewById(R.id.sent)
    var senderTimeStamp: TextView = itemView.findViewById(R.id.sender_time_stamp)
}