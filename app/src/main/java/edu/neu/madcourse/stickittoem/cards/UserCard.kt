package edu.neu.madcourse.stickittoem.cards

class UserCard {
    lateinit var name: String
    var totalStickersSent: Int = 0
    var totalStickersReceived: Int = 0

    constructor()

    constructor(name: String, totalStickersSent: Int, totalStickersReceived: Int) {
        this.name = name
        this.totalStickersSent = totalStickersSent
        this.totalStickersReceived = totalStickersReceived
    }

    override fun toString(): String {
        return "User(name='$name', totalStickersSent=$totalStickersSent, totalStickersReceived=$totalStickersReceived)"

    }
}