package edu.neu.madcourse.stickittoem.cards

class HistoryCard {
     var sticker: String? = null
     var totalCount: Long? = null

    constructor()
    constructor(sticker: String?, totalCount: Long) {
        this.sticker = sticker
        this.totalCount = totalCount
    }
}