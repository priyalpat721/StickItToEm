package edu.neu.madcourse.stickittoem.fragments.history

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.HistoryAdapter
import edu.neu.madcourse.stickittoem.cards.HistoryCard



class FragmentHistory : Fragment(R.layout.fragment_history) {
    private val historyList: MutableList<HistoryCard> = ArrayList<HistoryCard>()
    private var recyclerView: RecyclerView? = null
    var adapter: HistoryAdapter? = null
    private var db = Firebase.firestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.history_recycler_view)

        setUpResources()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
//        db.collection("users").get().addOnSuccessListener { result ->
//            for (user in result) {
//                val userData = user.data
//                val currentUser = Firebase.auth.currentUser
//
//                val newhistoryItem = HistoryCard(userData["totalSent"].get(""))
//                if (userData["email"].toString() != currentUser?.email) {
//                    val chat = UserCard(
//                        userData["name"].toString(),
//                        userData["email"].toString(),
//                        currentUser?.email,
//
//                        userData["email"].toString(),
//                        Integer.parseInt(userData["totalReceived"].toString()),
//                        Integer.parseInt(userData["totalSent"].toString())
//                    )
//
////                    historyList.add(chat)
////
//                    adapter?.notifyDataSetChanged()
//                }
//            }
//        }

        val newHistoryFrustrated = HistoryCard("frustratedino", 7)
        val newHistoryHappy = HistoryCard("happydino", 13)
        val newHistorySad = HistoryCard("saddino", 15)
        val newHistoryMotivated = HistoryCard("motivatedino", 19)
        val newHistoryExercise = HistoryCard("exercisedino", 2)
        val newHistorySleepy = HistoryCard("sleepdino2", 90)

        historyList.add(newHistoryExercise)
        historyList.add(newHistoryFrustrated)
        historyList.add(newHistoryHappy)
        historyList.add(newHistorySad)
        historyList.add(newHistoryMotivated)
        historyList.add(newHistorySleepy)
        historyList.add(newHistoryExercise)
        historyList.add(newHistoryFrustrated)
        historyList.add(newHistoryHappy)
        historyList.add(newHistorySad)
        historyList.add(newHistoryMotivated)
        historyList.add(newHistorySleepy)
        adapter?.notifyDataSetChanged()
    }

    private fun setUpResources(){
        adapter = this.context?.let { HistoryAdapter(historyList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }

}