package edu.neu.madcourse.stickittoem.messages

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class StickerSentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var sentSticker: ImageView = itemView.findViewById<ImageView>(R.id.sent)
    var messageSentCardLayout: ConstraintLayout? = itemView.findViewById<ConstraintLayout>(R.id.sentSticker)
}