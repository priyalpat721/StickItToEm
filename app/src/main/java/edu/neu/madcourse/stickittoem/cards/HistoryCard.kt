package edu.neu.madcourse.stickittoem.cards

class HistoryCard {
     var sticker: String? = null
     var totalCount: Int? = null

    constructor()
    constructor(sticker: String?, totalCount: Int) {
        this.sticker = sticker
        this.totalCount = totalCount
    }
}