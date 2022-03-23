package edu.neu.madcourse.stickittoem.cards

class ChatCard {
    lateinit var name: String
    lateinit var receiverId: String
    lateinit var senderId: String
    lateinit var email: String
    var totalStickersReceived : Int = 0
    var totalStickersSent : Int = 0

    constructor()

    constructor(
        name: String,
        receiverId: String,
        senderId: String,
        email: String,
        totalStickersReceived: Int,
        totalStickersSent: Int
    ) {
        this.name = name
        this.receiverId = receiverId
        this.senderId = senderId
        this.email = email
        this.totalStickersReceived = totalStickersReceived
        this.totalStickersSent = totalStickersSent
    }

    override fun toString(): String {
        return "ChatCard(name=$name, receiverId=$receiverId, senderId=$senderId, email=$email, totalStickersReceived=$totalStickersReceived, totalStickersSent=$totalStickersSent)"
    }

}