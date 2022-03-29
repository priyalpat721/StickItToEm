package edu.neu.madcourse.stickittoem.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.StickerGridAdapter

class BottomStickerSheetDialog : BottomSheetDialogFragment(){
    lateinit var name: String
    private val imageList: MutableList<StickerModel> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: StickerGridAdapter? = null
    var receiver : String? = null
    var sender : String? = null
    private var receiverId: String? = null
    private var senderId = Firebase.auth.currentUser?.uid!!
    private var stringStickerImg: String? = null
    private var stickerImage: Int? = null
    private var stickerDescription: String? = null
    private lateinit var doneButton: Button
    private lateinit var receiverName: String

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
        recyclerView = view.findViewById(R.id.sticker_recycler_view)

        adapter = this.context?.let { sender?.let { it1 ->
            receiver?.let { it2 ->
                StickerGridAdapter(view.context, it2,
                    it1, name)
            }
        } }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = GridLayoutManager(context,4,
            LinearLayoutManager.VERTICAL,false)
        imageList.clear()
        imageList.add(StickerModel(R.drawable.motivatedino, "motivated"))
        imageList.add(StickerModel(R.drawable.happydino, "happy"))
        imageList.add(StickerModel(R.drawable.frustratedino, "frustrated"))
        imageList.add(StickerModel(R.drawable.exercisedino, "exercise"))
        imageList.add(StickerModel(R.drawable.saddino, "sad"))
        imageList.add(StickerModel(R.drawable.sleepdino2, "sleepy"))
        adapter?.setDataList(imageList)

        // dismisses the dialog
        doneButton = view.findViewById(R.id.button)
        doneButton.setOnClickListener {
            dismiss()
        }
    }



    override fun toString(): String {
        return "BottomStickerSheetDialog(name='$name', imageList=$imageList, recyclerView=$recyclerView, adapter=$adapter, receiver='$receiver', sender='$sender')"
    }

}

data class StickerModel(
    var image : Int,
    var description : String
)

