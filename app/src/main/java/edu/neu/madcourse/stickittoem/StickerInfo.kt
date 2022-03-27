package edu.neu.madcourse.stickittoem

/*
stickerImage = stickerIntent?.getInt("image")
            stringStickerImg = stickerIDMap[stickerImage]
            stickerDescription = stickerIntent?.getString("description")
            receiver = stickerIntent?.getString("receiver").toString()
            sender = stickerIntent?.getString("sender").toString()
            receiverName = stickerIntent?.getString("name").toString()
 */
class StickerInfo {

    private lateinit var receiverName: String
    private lateinit var sender: String
    private lateinit var receiver: String
    private var stickerImage: Int? = null
    private var stickerDescription: String? = null


    constructor(image: Int, description: String, receiver: String, sender: String, name: String) {

    }
}