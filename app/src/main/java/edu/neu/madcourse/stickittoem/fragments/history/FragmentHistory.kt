package edu.neu.madcourse.stickittoem.fragments.history

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.neu.madcourse.stickittoem.R
import edu.neu.madcourse.stickittoem.adapters.HistoryAdapter
import edu.neu.madcourse.stickittoem.cards.HistoryCard



class FragmentHistory : Fragment(R.layout.fragment_history) {
    private val historyList: MutableList<HistoryCard> = ArrayList<HistoryCard>()
    private var recyclerView: RecyclerView? = null
    var adapter: HistoryAdapter? = null
    private var db = Firebase.database.reference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.history_recycler_view)

        setUpResources()
        listenForChanges()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun listenForChanges() {
        historyList.clear()
        db.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                historyList.clear()
                for (snap in snapshot.children) {
                    val totalSent = snap.child("totalSent").value as Map<String, Long>

                    if (Firebase.auth.currentUser?.uid == snap.key) {
                        val newHistoryFrustrated =
                            totalSent["frustratedino"]?.let { HistoryCard("frustratedino", it) }
                        val newHistoryHappy =
                            totalSent["happydino"]?.let { HistoryCard("happydino", it) }
                        val newHistorySad = totalSent["saddino"]?.let { HistoryCard("saddino", it) }
                        val newHistoryMotivated =
                            totalSent["motivatedino"]?.let { HistoryCard("motivatedino", it) }
                        val newHistoryExercise =
                            totalSent["exercisedino"]?.let { HistoryCard("exercisedino", it) }
                        val newHistorySleepy =
                            totalSent["sleepdino2"]?.let { HistoryCard("sleepdino2", it) }

                        if (newHistoryExercise != null) {
                            historyList.add(newHistoryExercise)
                        }
                        if (newHistoryFrustrated != null) {
                            historyList.add(newHistoryFrustrated)
                        }
                        if (newHistoryHappy != null) {
                            historyList.add(newHistoryHappy)
                        }
                        if (newHistorySad != null) {
                            historyList.add(newHistorySad)
                        }
                        if (newHistoryMotivated != null) {
                            historyList.add(newHistoryMotivated)
                        }
                        if (newHistorySleepy != null) {
                            historyList.add(newHistorySleepy)
                        }

                        adapter?.notifyDataSetChanged()

                        // adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // not implemented
            }
        })
    }

    private fun setUpResources(){
        adapter = this.context?.let { HistoryAdapter(historyList, it) }
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }

}