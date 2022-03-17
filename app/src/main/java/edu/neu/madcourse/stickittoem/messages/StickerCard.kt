package edu.neu.madcourse.stickittoem.messages

import com.google.api.client.util.DateTime

class StickerCard{
    lateinit var sticker: String
    private lateinit var timestamp: String

    constructor()

    constructor(sticker: String, timestamp: String) {
        this.sticker = sticker
        this.timestamp = timestamp
    }


    override fun toString(): String {
        return "MessageCard(messageImage='$sticker', timestamp=$timestamp)"
    }
}