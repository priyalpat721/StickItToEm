package edu.neu.madcourse.stickittoem.models

class User {
    private lateinit var name: String
    private lateinit var email: String

    constructor()
    constructor(name: String, email: String) {
        this.name = name
        this.email = email
    }

    override fun toString(): String {
        return "User(name='$name', email='$email')"
    }
}