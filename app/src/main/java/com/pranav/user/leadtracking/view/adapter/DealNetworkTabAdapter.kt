package com.pranav.user.leadtracking.view.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.pranav.user.leadtracking.view.fragment.tab.DealDetailTab
import com.pranav.user.leadtracking.view.fragment.tab.MeetingTabDeal
import com.pranav.user.leadtracking.view.fragment.tab.NotesTab


class DealNetworkTabAdapter(fm: FragmentManager, internal var a: Int) : FragmentStatePagerAdapter(fm) {


    var DESIGNATION = ""
    var LAST_CONNECTED_AT = ""
    var GROUP_ID = ""
    var ID = ""
    var NAME = ""

    override fun getItem(p0: Int): Fragment? {


        when (p0) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("DESIGNATION", DESIGNATION)
                bundle.putString("LAST_CONNECTED_AT", LAST_CONNECTED_AT)
                bundle.putString("GROUP_ID", GROUP_ID)
                bundle.putString("ID", ID)
                val dealDetailTab = DealDetailTab()
                dealDetailTab.arguments = bundle
                return dealDetailTab
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("DESIGNATION", DESIGNATION)
                bundle.putString("ID", ID)
                bundle.putString("CONTACT_ID", ID)
                bundle.putString("FLAG", "NETWORK")
                val meetingTab = MeetingTabDeal()
                meetingTab.arguments = bundle

//                val meetingTab = MeetingTab()
                return meetingTab
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("NAME", NAME)
                bundle.putString("ID", ID)
                val notesTab = NotesTab()
                notesTab.arguments = bundle
                return notesTab
            }
        }
        return null

    }

    override fun getCount(): Int {
        return a
    }

    fun set(designation: String?, lasT_CONNECTED_AT: String?, grouP_ID: String?, id: String?, name: String?) {
        DESIGNATION = designation!!
        LAST_CONNECTED_AT = lasT_CONNECTED_AT!!
        GROUP_ID = grouP_ID!!
        ID = id!!
        NAME = name!!
    }

}