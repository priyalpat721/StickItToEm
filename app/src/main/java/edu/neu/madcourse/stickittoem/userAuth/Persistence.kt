package edu.neu.madcourse.stickittoem.userAuth

import android.app.Application
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import edu.neu.madcourse.stickittoem.MainActivity

class Persistence: Application() {

    override fun onCreate() {
        super.onCreate()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}