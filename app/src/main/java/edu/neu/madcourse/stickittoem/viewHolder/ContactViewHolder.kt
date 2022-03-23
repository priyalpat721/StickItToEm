package edu.neu.madcourse.stickittoem.viewHolder

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import edu.neu.madcourse.stickittoem.R

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var name : TextView = itemView.findViewById(R.id.contact_name)
    var contactInfoLayout : ConstraintLayout = itemView.findViewById(R.id.contact_info_layout)

}