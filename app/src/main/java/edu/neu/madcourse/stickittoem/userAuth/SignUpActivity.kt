package edu.neu.madcourse.stickittoem.userAuth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import edu.neu.madcourse.stickittoem.MainActivity
import edu.neu.madcourse.stickittoem.R

class SignUpActivity : AppCompatActivity() {
    //lateinit var receiver: BroadcastReceiver
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var signUp: Button
    private lateinit var switchToLogIn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private var fireStore = Firebase.firestore
    private var db = Firebase.database.reference
    val TAG = "StickApp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        /*receiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val token : String? = intent.getStringExtra("token")

                Log.d("Sign Up", "token : $token")
            }

        }*/

        val token : String? = intent.getStringExtra("token")
        Log.d("Sign In", "token : $token")

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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token.toString()
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }



    /*override fun onResume() {
        super.onResume()
        val filter = IntentFilter(INTENT_ACTION_SEND_MESSAGE)
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }*/

    private fun signup(emailAddress: String, password: String, nameText: String) {
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
                        // get token for the user
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