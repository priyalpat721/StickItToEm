package edu.neu.madcourse.stickittoem.messages.notifications

data class NotificationData(
    val title: String,
    val message: String,
    val image: String
)

// for sending notification to a specific device
data class PushNotification(
    val data: NotificationData,
    val to : String
)