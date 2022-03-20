package edu.neu.madcourse.stickittoem.fragments.chat

class ChatCard {
    //TODO make chat card have sender id and receiver id?
    var name: String? = null
    var totalStickers : Int? = 0

    constructor()

    constructor(name: String?, totalStickers: Int?) {
        this.name = name
        this.totalStickers = totalStickers
    }

    override fun toString(): String {
        return "ChatCard(name=$name, totalStickers=$totalStickers)"
    }
}