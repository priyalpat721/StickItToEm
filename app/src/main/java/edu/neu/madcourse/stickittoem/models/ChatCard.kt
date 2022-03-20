package edu.neu.madcourse.stickittoem.models

class ChatCard {
    //TODO make chat card have sender id and receiver id?
    var name: String? = null
    var receiverId: String? = null;
    var senderId: String? = null;
    var totalStickers : Int? = 0

    constructor()
    constructor(name: String?, receiverId: String?, senderId: String?, totalStickers: Int?) {
        this.name = name
        this.receiverId = receiverId
        this.senderId = senderId
        this.totalStickers = totalStickers
    }

    override fun toString(): String {
        return "ChatCard(name=$name, receiverId=$receiverId, senderId=$senderId, totalStickers=$totalStickers)"
    }
}