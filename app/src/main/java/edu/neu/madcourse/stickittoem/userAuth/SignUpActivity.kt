package edu.neu.madcourse.stickittoem.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import edu.neu.madcourse.stickittoem.MainActivity
import edu.neu.madcourse.stickittoem.R

// reference used: https://firebase.google.com/docs/auth/android/custom-auth?utm_source=studio
class SignUpActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var signUp: Button
    private lateinit var switchToLogIn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        signUp = findViewById(R.id.sign_up)
        switchToLogIn = findViewById(R.id.to_login_page)
        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility = View.GONE
        switchToLogIn.setOnClickListener {
            // TODO Change this to login activity
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            finish()
            startActivity(intent)
        }

        signUp.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val emailAddress = email.text.toString()
            val nameText = name.text.toString()
            val password = "password"
            signup(emailAddress, password)
        }
        auth = FirebaseAuth.getInstance()

    }

    // resource: https://firebase.google.com/docs/auth/android/start
    private fun signup(emailAddress: String, password: String) {
        auth.createUserWithEmailAndPassword(emailAddress, password)
            .addOnCompleteListener(this@SignUpActivity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "Account successfully made.",
                        Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    finish()
                    progressBar.visibility = View.GONE
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Could not create an account.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}