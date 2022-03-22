package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.common.io.Resources.getResource
import com.google.common.reflect.Reflection.getPackageName
import com.google.firebase.auth.FirebaseAuth
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.cards.StickerCard
import edu.neu.madcourse.stickittoem.viewHolder.StickerReceivedViewHolder
import edu.neu.madcourse.stickittoem.viewHolder.StickerSentViewHolder
import kotlin.properties.Delegates

class StickerMessagingAdapter(
    private var stickerMessageList: MutableList<StickerCard>,
    private var context: Context

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG: String = "Message"
    private var path : String = ""
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
        when(stickerMessageList[position].sticker){
            "exercisedino" ->{
                path = "https://www.linkpicture.com/q/exercisedino.png"
            }
            "frustratedino" ->{
                path = "https://www.linkpicture.com/q/frustratedino.png"
            }
            "happydino"->{
                path = "https://www.linkpicture.com/q/happydino.png"
            }
            "motivatedino"->{
                path = "https://www.linkpicture.com/q/motivatedino.png"
            }
            "saddino"->{
                path = "https://www.linkpicture.com/q/saddino.png"
            }
            "sleepdino2"->{
                path = "https://www.linkpicture.com/q/sleepdino2.png"
            }

        }
        Log.i(TAG, "Path$path")
        println("This is from sticker path: $path")
        // holder can be of two types here: received and sent so this needs to be taken into account
        if (holder.javaClass == StickerSentViewHolder::class.java) {
            holder as StickerSentViewHolder

            Glide.with(context).load(path).into(holder.sentSticker)

        } else {
            holder as StickerReceivedViewHolder
            Glide.with(context).load(path).into(holder.receivedSticker)
        }
    }

    override fun getItemCount(): Int {
        return stickerMessageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = stickerMessageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid == message.sender) {
            return 0
        }
        return 1
    }
}