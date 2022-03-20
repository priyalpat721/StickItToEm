package edu.neu.madcourse.stickittoem.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.neu.madcourse.stickittoem.R

class BottomStickerSheetDialog : BottomSheetDialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the bottom sticker sheet layout
        return inflater.inflate(R.layout.bottom_sticker_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // make click listeners for stickers
        /*button1.setOnClickListener {
            Toast.makeText(context,"pressed button 1", Toast.LENGTH_LONG).show()
        }
        button2.setOnClickListener {
            Toast.makeText(context,"pressed button 1", Toast.LENGTH_LONG).show()
        }
        button3.setOnClickListener {
            Toast.makeText(context,"pressed button 1", Toast.LENGTH_LONG).show()
        }*/
    }
}
