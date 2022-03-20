package edu.neu.madcourse.stickittoem.cards

class StickerCard{
    lateinit var sticker: String
    lateinit var timestamp: String
    lateinit var sender: String
    lateinit var receiver : String

    constructor()
    constructor(sticker: String, timestamp: String, sender: String, receiver: String) {
        this.sticker = sticker
        this.timestamp = timestamp
        this.sender = sender
        this.receiver = receiver
    }

    override fun toString(): String {
        return "StickerCard(sticker='$sticker', timestamp='$timestamp', sender='$sender', receiver='$receiver')"
    }
}