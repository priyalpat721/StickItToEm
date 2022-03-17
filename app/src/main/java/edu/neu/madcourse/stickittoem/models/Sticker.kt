package edu.neu.madcourse.stickittoem.models

import com.google.api.client.util.DateTime

class Sticker {
    private lateinit var sticker: String
    private lateinit var senderName: String
    private lateinit var receiverName: String
    private lateinit var timestamp: DateTime

    constructor()

    constructor(
        sticker: String,
        senderName: String,
        receiverName: String,
        timestamp: DateTime
    ) {
        this.sticker = sticker
        this.senderName = senderName
        this.receiverName = receiverName
        this.timestamp = timestamp
    }

    override fun toString(): String {
        return "Message(messageImage='$sticker', senderName='$senderName', receiverName='$receiverName', timestamp=$timestamp)"
    }


}