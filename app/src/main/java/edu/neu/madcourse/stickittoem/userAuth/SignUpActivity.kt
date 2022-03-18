package edu.neu.madcourse.stickittoem.userAuth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.neu.madcourse.stickittoem.R

// reference used: https://firebase.google.com/docs/auth/android/custom-auth?utm_source=studio
class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }
}