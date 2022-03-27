package edu.neu.madcourse.stickittoem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.adapters.Adapter
import edu.neu.madcourse.stickittoem.userAuth.SignInActivity

class MainActivity : AppCompatActivity() {
    // server key from firebase https://console.firebase.google.com/project/stick-it-to-em-99f10/settings/cloudmessaging
    private val SERVER_KEY: String = "key = AAAAmT9eZxc:APA91bEUzh4cD0qqeNqzvMQv4EScFoTOcwBllfKVMjuPHWPkD5F8EVng6wE3UGxrpVAapsD336oGzp6dNUuK3rMYb1ZY7AQIjp0wo0cZEhAujwlnukmXTQQVQMoZ-vLaa6Zrq0GbY0xF"
    private val CLIENT_REGISTRATION_TOKEN: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //CLIENT_REGISTRATION_TOKEN = FirebaseInstanceId.getInstance().getInstanceId()


        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)

        val adapter = Adapter(supportFragmentManager, lifecycle)

        viewPager2.adapter = adapter

        val tabs = arrayListOf<String>()
        tabs.add("Contacts")
        tabs.add("History")
        tabs.add("Chats")

        TabLayoutMediator(tabLayout, viewPager2){ tab, position-> tab.text = tabs[position] }.attach()

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

//    private val db = Firebase.firestore
//    private val TAG = "FireStore"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Create a new user with a first and last name
//        val user1 = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815
//        )
//
//        // Add a new document with a generated ID
//        db.collection("users")
//            .add(user1)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//
//        // Create a new user with a first, middle, and last name
//        val user2 = hashMapOf(
//            "first" to "Alan",
//            "middle" to "Mathisson",
//            "last" to "Turing",
//            "born" to 1912
//        )
//
//        // Add a new document with a generated ID
//        db.collection("users")
//            .add(user2)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//
//        db.collection("users")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//    }
