package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.messages.StickerModel

class StickerGridAdapter(var context: Context) : RecyclerView.Adapter<StickerGridAdapter.ViewHolder>() {
    var dataList = emptyList<StickerModel>()
    internal fun setDataList(dataList:List<StickerModel>) {
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var description: TextView

        init {
            image = itemView.findViewById(R.id.sticker_image)
            description = itemView.findViewById(R.id.sticker_description)//set the content description
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StickerGridAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sticker_grid,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickerGridAdapter.ViewHolder, position: Int) {
        var data = dataList[position]

        holder.image.setImageResource(data.image)
        holder.description.text = data.description
    }

    override fun getItemCount() = dataList.size

}