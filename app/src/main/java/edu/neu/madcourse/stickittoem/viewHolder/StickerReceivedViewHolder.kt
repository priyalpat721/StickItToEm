package edu.neu.madcourse.stickittoem.viewHolder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class StickerReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var receivedSticker: ImageView = itemView.findViewById(R.id.received)
}