package edu.neu.madcourse.stickittoem.viewHolder

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R


class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name : TextView = itemView.findViewById(R.id.receiver_name)
    var totalStickers : TextView = itemView.findViewById(R.id.total_stickers_sent)
    var chatInfoLayout : ConstraintLayout = itemView.findViewById(R.id.chat_info_layout)
}