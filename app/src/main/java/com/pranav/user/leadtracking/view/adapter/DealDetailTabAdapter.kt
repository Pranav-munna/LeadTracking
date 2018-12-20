package com.pranav.user.leadtracking.view.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.pranav.user.leadtracking.view.fragment.tab.DealDetailTabDeal
import com.pranav.user.leadtracking.view.fragment.tab.MeetingTabDeal
import com.pranav.user.leadtracking.view.fragment.tab.NotesTab
import com.pranav.user.leadtracking.view.fragment.tab.TimeLineTabDeal

class DealDetailTabAdapter(fm: FragmentManager, internal var a: Int) : FragmentStatePagerAdapter(fm) {

    var LEVEL_VALUE = ""
    var ID = ""
    var GROUP_ID = ""
    var LEVEL_ID = ""
    var MEMBERS = ""
    var CONTACT_ID = ""
    var NAME = ""
    var STAGE = ""

    override fun getItem(p0: Int): Fragment? {
        val bundle = Bundle()
        bundle.putString("LEVEL_VALUE", LEVEL_VALUE)
        bundle.putString("ID", ID)
        bundle.putString("GROUP_ID", GROUP_ID)
        bundle.putString("LEVEL_ID", LEVEL_ID)
        bundle.putString("MEMBERS", MEMBERS)
        bundle.putString("CONTACT_ID", CONTACT_ID)
        bundle.putString("NAME", NAME)
        bundle.putString("FLAG", "DEAL")
        bundle.putString("STAGE", STAGE)

        when (p0) {
            0 -> {
                val dealDetailTabDeal = DealDetailTabDeal()
                dealDetailTabDeal.arguments = bundle
                return dealDetailTabDeal
            }
            1 -> {
//                val bundle = Bundle()
//                bundle.putString("ID", ID)
//                bundle.putString("CONTACT_ID", CONTACT_ID)
                val meetingTabDeal = MeetingTabDeal()
                meetingTabDeal.arguments = bundle
                return meetingTabDeal
            }
            2 -> {
                val timeLineTabDeal = TimeLineTabDeal()
                timeLineTabDeal.arguments = bundle
                return timeLineTabDeal
            }
            3 -> {
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

    fun set(leveL_VALUE: String?, id: String?, grouP_ID: String?, leveL_ID: String?,
            members: String?, contacT_ID: String?, name: String?, stage: String?) {
        LEVEL_VALUE = leveL_VALUE!!
        ID = id!!
        GROUP_ID = grouP_ID!!
        LEVEL_ID = leveL_ID!!
        MEMBERS = members!!
        CONTACT_ID = contacT_ID!!
        NAME = name!!
        STAGE=stage!!
    }
}