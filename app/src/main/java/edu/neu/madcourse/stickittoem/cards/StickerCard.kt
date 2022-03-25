package edu.neu.madcourse.stickittoem.cards

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import java.time.LocalDateTime
import java.util.*

class StickerCard{
    lateinit var sticker: String
    lateinit var timestamp: String
    lateinit var sender: String
    lateinit var receiver : String

    constructor()
    constructor(sticker: String?, timestamp: String, sender: String, receiver: String) {
        if (sticker != null) {
            this.sticker = sticker
        }
        else {
            this.sticker = null.toString()
        }
        this.timestamp = timestamp
        this.sender = sender
        this.receiver = receiver
    }

    override fun toString(): String {
        return "StickerCard(sticker='$sticker', timestamp='$timestamp', sender='$sender', receiver='$receiver')"
    }
}