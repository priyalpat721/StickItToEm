package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.messages.StickerMessagingActivity
import edu.neu.madcourse.stickittoem.messages.StickerModel

class StickerGridAdapter(var context: Context, var receiver : String, var sender : String) : RecyclerView.Adapter<StickerGridAdapter.ViewHolder>() {
    private var dataList = emptyList<StickerModel>()
    internal fun setDataList(dataList:List<StickerModel>) {
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        lateinit var description: String

        init {
            image = itemView.findViewById(R.id.sticker_image)
            // scales stickers to fir in grid
            image.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sticker_grid,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.image.setImageResource(data.image)
        holder.description = data.description

        holder.image.setOnClickListener(){
            Toast.makeText(context, holder.description +" pressed", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, StickerMessagingActivity::class.java)
            intent.putExtra("image", data.image)
            intent.putExtra("description", data.description)
            intent.putExtra("receiver", receiver)
            intent.putExtra("sender", sender)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = dataList.size
}