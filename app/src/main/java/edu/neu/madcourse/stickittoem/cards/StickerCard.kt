package edu.neu.madcourse.stickittoem.cards

import com.google.firebase.firestore.FieldValue

class StickerCard{
    lateinit var sticker: String
    lateinit var timestamp: FieldValue
    lateinit var sender: String
    lateinit var receiver : String

    constructor()
    constructor(sticker: String?, timestamp: FieldValue?, sender: String, receiver: String) {
        if (sticker != null) {
            this.sticker = sticker
        }
        else {
            this.sticker = null.toString()
        }
        if (timestamp != null) {
            this.timestamp = timestamp
        }
        this.sender = sender
        this.receiver = receiver
    }

    override fun toString(): String {
        return "StickerCard(sticker='$sticker', timestamp='$timestamp', sender='$sender', receiver='$receiver')"
    }
}