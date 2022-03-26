package edu.neu.madcourse.stickittoem.cards

class UserCard {
    lateinit var name: String
    lateinit var receiverId: String
    lateinit var senderId: String
    var totalStickersReceived : Int = 0
    private var totalStickersSent : Map<String, Long>? = null

    constructor()

    constructor(
        name: String,
        receiverId: String,
        senderId: String,
        totalStickersReceived: Int,
        totalStickersSent: Map<String, Long>
    ) {
        this.name = name
        this.receiverId = receiverId
        this.senderId = senderId
        this.totalStickersReceived = totalStickersReceived
        this.totalStickersSent = totalStickersSent
    }

    override fun toString(): String {
        return "UserCard(name=$name, senderId=$senderId, totalStickersReceived=$totalStickersReceived, totalStickersSent=$totalStickersSent)"

    }

}