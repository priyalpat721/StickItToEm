package edu.neu.madcourse.stickittoem.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.neu.madcourse.stickittoem.R

class BottomStickerSheetDialog : BottomSheetDialogFragment(){
    private lateinit var button1 : ImageView
    private lateinit var button2 : ImageView
    private lateinit var button3 : ImageView
    private lateinit var button4 : ImageView
    private lateinit var button5 : ImageView
    private lateinit var button6 : ImageView

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
        button1 = view.findViewById(R.id.button1)
        button2 = view.findViewById(R.id.button2)
        button3 = view.findViewById(R.id.button3)
        button4 = view.findViewById(R.id.button4)
        button5 = view.findViewById(R.id.button5)
        button6 = view.findViewById(R.id.button6)
        // make click listeners for stickers
        button1.setOnClickListener {
            Toast.makeText(context,"pressed button 1", Toast.LENGTH_LONG).show()
        }
        button2.setOnClickListener {
            Toast.makeText(context,"pressed button 2", Toast.LENGTH_LONG).show()
        }
        button3.setOnClickListener {
            Toast.makeText(context,"pressed button 3", Toast.LENGTH_LONG).show()
        }
        button4.setOnClickListener {
            Toast.makeText(context,"pressed button 4", Toast.LENGTH_LONG).show()
        }
        button5.setOnClickListener {
            Toast.makeText(context,"pressed button 5", Toast.LENGTH_LONG).show()
        }
        button6.setOnClickListener {
            Toast.makeText(context,"pressed button 6", Toast.LENGTH_LONG).show()
        }
    }
}