package edu.neu.madcourse.stickittoem.messages

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class StickerReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var receivedSticker: ImageView = itemView.findViewById<ImageView>(R.id.received)
    var messageReceivedCardLayout: ConstraintLayout? = itemView.findViewById<ConstraintLayout>(R.id.receivedSticker)
}