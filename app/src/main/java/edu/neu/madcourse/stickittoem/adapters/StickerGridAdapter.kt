package edu.neu.madcourse.stickittoem.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class StickerGridAdapter(var context: Context) : RecyclerView.Adapter<StickerGridAdapter.ViewHolder>() {
    var dataList = emptyList<Int>()
    internal fun setDataList(dataList:List<Int>) {
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView

        init {
            image = itemView.findViewById(R.id.sticker_image)
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

        holder.image.setImageResource(data)
    }

    override fun getItemCount() = dataList.size

}