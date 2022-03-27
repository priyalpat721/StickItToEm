package edu.neu.madcourse.stickittoem.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.MainActivity
import edu.neu.madcourse.stickittoem.R
import java.util.HashMap

class SignUpActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var warning_sign_up: TextView
    private lateinit var signUp: Button
    private lateinit var switchToLogIn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var warning_sign_up: TextView
    private var fireStore = Firebase.firestore
    private var db = Firebase.database.reference
    val TAG = "StickApp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        warning_sign_up = findViewById(R.id.warning_sign_up)
        warning_sign_up.text = ""

        warning_sign_up = findViewById(R.id.warning_sign_up)
        warning_sign_up.text = ""
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        signUp = findViewById(R.id.sign_up)
        switchToLogIn = findViewById(R.id.to_login_page)
        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility = View.GONE
        switchToLogIn.setOnClickListener {
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            finish()
            startActivity(intent)
        }

        signUp.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val emailAddress = email.text.toString()
            val nameText = name.text.toString()
            signup(emailAddress, "password", nameText)
        }
        auth = Firebase.auth

    }

    private fun signup(emailAddress: String, password: String, nameText: String) {
        warning_sign_up.text = ""

        if(emailAddress.equals("") || nameText.equals("")){
            Thread.sleep(1000)
            progressBar.visibility = View.GONE
            if(nameText.equals("") && emailAddress.equals("")){
                warning_sign_up.text = "Name and email fields empty. Please provide above information"
            } else if (emailAddress.equals("")){
                warning_sign_up.text = "Email Field empty. Please enter your Email."
            } else {
                warning_sign_up.text = "Name Field empty. Please enter your Name."
            }

            return
        }

        auth.createUserWithEmailAndPassword(emailAddress, password)
            .addOnCompleteListener(this@SignUpActivity) { task ->
                if (task.isSuccessful) {
                    val newUser = hashMapOf(
                        "name" to nameText,
                        "password" to password,
                        "email" to emailAddress,
                        "totalReceived" to 0,
                        "totalSent" to hashMapOf(
                            "exercisedino" to 0,
                            "frustratedino" to 0,
                            "happydino" to 0,
                            "motivatedino" to 0,
                            "saddino" to 0,
                            "sleepdino2" to 0
                        )
                    )

                    progressBar.visibility = View.VISIBLE

                    Firebase.auth.currentUser?.let { db.child("users").child(it.uid).setValue(newUser) }

                    Log.d(TAG, "DocumentSnapshot added with ID: $emailAddress")
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext, "Account successfully made.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    finish()
                    progressBar.visibility = View.GONE
                    startActivity(intent)


                } else {
                    Thread.sleep(1000)
                    progressBar.visibility = View.GONE

                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Could not create an account. Please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}