package edu.neu.madcourse.stickittoem.models

class User {
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private var totalStickersSent: Int = 0
    private var totalStickersReceived: Int = 0

    constructor()

    constructor(
        name: String,
        email: String,
        password: String,
        totalStickersSent: Int,
        totalStickersReceived: Int
    ) {
        this.name = name
        this.email = email
        this.password = password
        this.totalStickersSent = totalStickersSent
        this.totalStickersReceived = totalStickersReceived
    }

    override fun toString(): String {
        return "User(name='$name', email='$email', password='$password', totalStickersSent=$totalStickersSent, totalStickersReceived=$totalStickersReceived)"
    }
}