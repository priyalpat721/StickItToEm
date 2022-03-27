package edu.neu.madcourse.stickittoem.userAuth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.MainActivity
import edu.neu.madcourse.stickittoem.R

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var warning: TextView
    private lateinit var usernameOrEmail: EditText
    private lateinit var signInButton: Button
    private lateinit var progressBar: ProgressBar
    private var fireStore = Firebase.firestore
    private var db = Firebase.database.reference
    val TAG = "StickApp"
    private lateinit var signUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin_in)

        signUp = findViewById(R.id.to_sign_up_page)
        auth = Firebase.auth
        warning = findViewById(R.id.warning_sign)
        usernameOrEmail = findViewById(R.id.sign_in_email)
        signInButton = findViewById(R.id.sign_in_button)
        progressBar = findViewById(R.id.progress_bar_sign_in)
        progressBar.visibility = View.INVISIBLE

        signInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val emailOrUsername = usernameOrEmail.text.toString()
            signIn(emailOrUsername, "password")
        }

        signUp.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            finish()
            startActivity(intent)

        }

    }

    @SuppressLint("SetTextI18n")
    private fun signIn(userNameOrEmail: String, password: String) {
        warning.text = ""

        if(userNameOrEmail.equals("")){
            progressBar.visibility = View.VISIBLE
            Thread.sleep(1000)
            progressBar.visibility = View.GONE
            warning.text = "You don't have any text in the Email field. Please try again."
            return
        }

        auth.signInWithEmailAndPassword(userNameOrEmail, password)
            .addOnCompleteListener(this@SignInActivity) { task ->
                if (task.isSuccessful) {

                    val userRef = db.child("users")
                    userRef.get().addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d(TAG, "Successfully signed in with: $userNameOrEmail")

                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            finish()
                            progressBar.visibility = View.GONE
                            startActivity(intent)
                        }
                    }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error signing in", e)
                        }
                } else {
                    Thread.sleep(1000)
                    progressBar.visibility = View.GONE
                    warning.text = "Email is invalid! Check email or sign up by clicking here."
                    warning.setOnClickListener {
                        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                        finish()
                        progressBar.visibility = View.GONE
                        startActivity(intent)

                    }

                }
            }

    }
}