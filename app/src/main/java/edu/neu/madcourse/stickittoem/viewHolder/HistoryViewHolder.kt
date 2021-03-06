package edu.neu.madcourse.stickittoem.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var totalSent : TextView = itemView.findViewById(R.id.total_sent_number)
    var actualSticker: ImageView = itemView.findViewById(R.id.sticker_image_history)
}