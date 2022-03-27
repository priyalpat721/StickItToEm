package edu.neu.madcourse.stickittoem.cards

class UserCard {
    lateinit var name: String
    lateinit var receiverId: String
    lateinit var senderId: String
    var totalStickersReceived : Int = 0
    private var totalStickersSent : Map<String, Int>? = null
    var userToken : String? = null

    constructor()

    constructor(
        name: String,
        receiverId: String,
        senderId: String,
        totalStickersReceived: Int,
        totalStickersSent: Map<String, Int>,
        user_token: String
    ) {
        this.name = name
        this.receiverId = receiverId
        this.senderId = senderId
        this.totalStickersReceived = totalStickersReceived
        this.totalStickersSent = totalStickersSent
        this.userToken = user_token
    }

    override fun toString(): String {
        return "UserCard(name=$name, senderId=$senderId, totalStickersReceived=$totalStickersReceived, totalStickersSent=$totalStickersSent)"

    }

}