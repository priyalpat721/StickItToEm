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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.MainActivity
import edu.neu.madcourse.stickittoem.R

class SignInActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usernameOrEmail : EditText
    private lateinit var signInButton : Button
    private lateinit var progressBar: ProgressBar
    private var fireStore = Firebase.firestore
    val TAG = "StickApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin_in)

        auth = Firebase.auth
        usernameOrEmail = findViewById(R.id.sign_in_email)
        signInButton = findViewById(R.id.sign_in_button)
        progressBar = findViewById(R.id.progress_bar_sign_in)
        progressBar.visibility = View.INVISIBLE

        signInButton.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            var emailOrUsername = usernameOrEmail.text.toString()
            signIn(emailOrUsername, "password");
        }

    }

    private fun signIn(userNameOrEmail: String, password: String) {
        auth.signInWithEmailAndPassword(userNameOrEmail, password)
            .addOnCompleteListener(this@SignInActivity) { task ->
                if(task.isSuccessful) {

                    val userRef = fireStore.collection("users").document(userNameOrEmail)
                    userRef.get().addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d(TAG, "Successfully signed in with: ${userNameOrEmail}")

                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            finish()
                            //hello
                            progressBar.visibility = View.GONE
                            startActivity(intent)
                        }
                    }
                        .addOnFailureListener{e->
                            Log.w(TAG, "Error signing in", e)
                            progressBar.visibility = View.GONE
                        }


                }
        }
    }


}