package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.cards.HistoryCard
import edu.neu.madcourse.stickittoem.cards.StickerCard
import edu.neu.madcourse.stickittoem.viewHolder.HistoryViewHolder

class HistoryAdapter(
    private var historyList: MutableList<HistoryCard>,
    private var context: Context
) : RecyclerView.Adapter<HistoryViewHolder>() {
    private var path: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.history_card_layout, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.totalSent.text = historyList[position].totalCount.toString()
        when (historyList[position].sticker) {
            "exercisedino" -> {
                path = "https://www.linkpicture.com/q/exercisedino.png"
            }
            "frustratedino" -> {
                path = "https://www.linkpicture.com/q/frustratedino.png"
            }
            "happydino" -> {
                path = "https://www.linkpicture.com/q/happydino.png"
            }
            "motivatedino" -> {
                path = "https://www.linkpicture.com/q/motivatedino.png"
            }
            "saddino" -> {
                path = "https://www.linkpicture.com/q/saddino.png"
            }
            "sleepdino2" -> {
                path = "https://www.linkpicture.com/q/sleepdino2.png"
            }
        }
//        holder as HistoryViewHolder
        Glide.with(context).load(path).into(holder.actualSticker)


    }

    override fun getItemCount(): Int {
        return historyList.size
    }

}