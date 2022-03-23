package edu.neu.madcourse.stickittoem.cards

class UserCard {
    var name: String? = null
    var receiverId: String? = null
    var senderId: String? = null
    var email: String? = null
    var totalStickersReceived : Int? = 0
    var totalStickersSent : Int? = 0

    constructor()

    constructor(
        name: String?,
        receiverId: String?,
        senderId: String?,
        email: String?,
        totalStickersReceived: Int?,
        totalStickersSent: Int?
    ) {
        this.name = name
        this.receiverId = receiverId
        this.senderId = senderId
        this.email = email
        this.totalStickersReceived = totalStickersReceived
        this.totalStickersSent = totalStickersSent
    }

    override fun toString(): String {
        return "UserCard(name=$name, receiverId=$receiverId, senderId=$senderId, email=$email, totalStickersReceived=$totalStickersReceived, totalStickersSent=$totalStickersSent)"

    }

}