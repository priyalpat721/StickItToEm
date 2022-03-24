package edu.neu.madcourse.stickittoem.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.neu.madcourse.stickittoem.fragments.contacts.FragmentContacts
import edu.neu.madcourse.stickittoem.fragments.history.FragmentHistory

class Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return (when(position){
            0-> {
                FragmentContacts()
            }
            1->{
                FragmentHistory()
            }
            else -> {
                FragmentHistory()
            }
        })
    }


}