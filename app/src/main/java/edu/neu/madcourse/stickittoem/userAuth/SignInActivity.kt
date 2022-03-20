package edu.neu.madcourse.stickittoem.userAuth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import edu.neu.madcourse.stickittoem.MainActivity
import edu.neu.madcourse.stickittoem.R

class SignInActivity: AppCompatActivity() {

    private lateinit var usernameOrEmail : EditText
    private lateinit var signInButton : Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin_in)

        usernameOrEmail = findViewById(R.id.sign_in_email)
        signInButton = findViewById(R.id.sign_in_button)
        progressBar = findViewById(R.id.progress_bar_sign_in)
        progressBar.visibility = View.INVISIBLE

        }
}