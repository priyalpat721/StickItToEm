package edu.neu.madcourse.stickittoem.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.StickerGridAdapter

// sources:
// https://pasindulaksara.medium.com/recyclerview-with-grid-layout-in-kotlin-414d780c47ae
class BottomStickerSheetDialog : BottomSheetDialogFragment(){
    private val imageList: MutableList<StickerModel> = ArrayList<StickerModel>()
    private var recyclerView: RecyclerView? = null
    private var adapter: StickerGridAdapter? = null
    private lateinit var stickerButton: ImageButton
    //private lateinit var stickerGridAdapter: StickerGridAdapter
    //private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.bottom_sticker_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.sticker_recycler)

        adapter = this.context?.let { StickerGridAdapter(view.context) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = GridLayoutManager(context,4,
            LinearLayoutManager.VERTICAL,false)
        imageList.clear()
        imageList.add(StickerModel(com.google.firebase.messaging.R.drawable.adaptive_icon, "sticker1"))
        imageList.add(StickerModel(com.google.firebase.messaging.R.drawable.adaptive_icon, "sticker2"))
        imageList.add(StickerModel(com.google.firebase.messaging.R.drawable.adaptive_icon, "sticker3"))
        imageList.add(StickerModel(com.google.firebase.messaging.R.drawable.adaptive_icon, "sticker4"))
        imageList.add(StickerModel(com.google.firebase.messaging.R.drawable.adaptive_icon, "sticker5"))
        imageList.add(StickerModel(com.google.firebase.messaging.R.drawable.adaptive_icon, "sticker6"))
        adapter?.setDataList(imageList)

        /*stickerButton = view.findViewById(R.id.sticker_grid)
        stickerButton.setOnClickListener{
            // TODO get the sticker name
            Toast.makeText(context,"pressed", Toast.LENGTH_LONG).show()
        }*/
        /*adapter = view.findViewById(R.id.sticker_grid).also {
            it.layoutManager = gridLayoutManager
            it.setHasFixedSize(true)
            it.adapter = alphaAdapters
        }*/

        //super.onViewCreated(view, savedInstanceState)
        //recycler = view.findViewById(R.id.sticker_recycler)
        /*button1 = view.findViewById(R.id.button1)
        button2 = view.findViewById(R.id.button2)
        button3 = view.findViewById(R.id.button3)*/
        // make click listeners for stickers
        /*button1.setOnClickListener {
            Toast.makeText(context,"pressed button 1", Toast.LENGTH_LONG).show()
        }
        button2.setOnClickListener {
            Toast.makeText(context,"pressed button 2", Toast.LENGTH_LONG).show()
        }
        button3.setOnClickListener {
            Toast.makeText(context,"pressed button 3", Toast.LENGTH_LONG).show()
        }*/
    }
}

data class StickerModel(
    var image : Int,
    var description : String
)
