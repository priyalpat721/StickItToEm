package edu.neu.madcourse.stickittoem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import edu.neu.madcourse.stickittoem.adapters.Adapter
import edu.neu.madcourse.stickittoem.messages.PushNotificationService
import edu.neu.madcourse.stickittoem.userAuth.SignInActivity

class MainActivity : AppCompatActivity() {
    lateinit var currentToken : String
    private var db = Firebase.database.reference
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)

        val adapter = Adapter(supportFragmentManager, lifecycle)

        viewPager2.adapter = adapter

        val tabs = arrayListOf<String>()
        tabs.add("Contacts")
        tabs.add("History")
        tabs.add("Chats")

        TabLayoutMediator(tabLayout, viewPager2){ tab, position-> tab.text = tabs[position] }.attach()

        PushNotificationService.sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token.toString()
            Log.i(TAG, msg)
            currentToken = msg
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            Log.i(TAG, "Currentuser ID: " + Firebase.auth.currentUser!!.uid)
            Firebase.auth.currentUser?.let { db.child("users").child(it.uid).child("token").setValue(currentToken) }
        })

        val signOut = findViewById<Button>(R.id.sign_out)
        signOut.setOnClickListener {
            Toast.makeText(
                baseContext, "Sign out was successfully.",
                Toast.LENGTH_SHORT
            ).show()
            Firebase.auth.signOut()
            finish()
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}

